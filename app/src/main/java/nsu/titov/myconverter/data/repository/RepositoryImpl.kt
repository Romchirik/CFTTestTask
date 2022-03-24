package nsu.titov.myconverter.data.repository

import android.util.Log
import nsu.titov.myconverter.data.dao.CurrencyDao
import nsu.titov.myconverter.data.mappers.RepositoryInternalMapper
import nsu.titov.myconverter.data.models.CBRResponse
import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.data.network.RetrofitInstance
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.ConverterCurrency
import nsu.titov.myconverter.domain.models.ErrorType
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.domain.models.SimpleCurrency
import retrofit2.Response

class RepositoryImpl(
    private val currencyListMapper: CurrencyMapper<Currency, SimpleCurrency>,
    private val converterMapper: CurrencyMapper<Currency, ConverterCurrency>,
    private val internalMapper: RepositoryInternalMapper,
    private val localRepo: CurrencyDao
) : Repository {


    private var lastError = ErrorType.NONE
    private var buffer: List<Currency> = listOf()

    override fun getLastError(): ErrorType {
        return lastError.also {
            lastError = ErrorType.NONE
        }
    }

    override suspend fun forceRefreshData(): ErrorType {
        val data = requestData()
        if (null != data && data.isSuccessful) {
            buffer = internalMapper.currencyFromResponse(data.body()!!)
        } else {
            return ErrorType.NETWORK_ERROR
        }
        return ErrorType.NONE
    }

    override suspend fun getConverterCurrencyList(): List<ConverterCurrency> {
        checkBuffer()
        return converterMapper.fromDataLayerType(buffer)
    }

    override suspend fun getSimpleCurrencyList(): List<SimpleCurrency> {
        checkBuffer()
        return currencyListMapper.fromDataLayerType(buffer)
    }

    private suspend fun checkBuffer() {
        if (buffer.isNotEmpty()) {
            return
        }

        val response = requestData()
        if (null == response) {
            lastError = ErrorType.NETWORK_ERROR
            buffer = localRepo.getAllCurrencyData().map { internalMapper.dtoToCurrency(it) }
            lastError = ErrorType.DATABASE_ERROR
            return
        } else {
            if (response.isSuccessful) {
                buffer = internalMapper.currencyFromResponse(response.body()!!)
                localRepo.addCurrencyAll(buffer.map { internalMapper.currencyToDto(it) })
            }
            return
        }
    }

    private suspend fun requestData(): Response<CBRResponse>? {
        var response: Response<CBRResponse>? = null
        try {
            response = RetrofitInstance.api.getCurrencyList()
        } catch (e: Exception) {
            Log.e("RepositoryNetwork", e.localizedMessage ?: "Unable to retrieve data from server")
        }
        return response
    }
}