package nsu.titov.myconverter.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.myconverter.data.dao.CurrencyDao
import nsu.titov.myconverter.data.mappers.RepositoryInternalMapper
import nsu.titov.myconverter.data.models.CBRResponse
import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.data.network.CBRApiService
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
    private val localRepo: CurrencyDao,
    private val remoteRepo: CBRApiService
) : Repository {

    private var lastError = MutableLiveData(ErrorType.NONE)
    private var buffer: List<Currency> = listOf()

    override fun getLastError(): LiveData<ErrorType> {
        return lastError
    }

    override suspend fun forceRefreshData() {
        Log.i("ConverterRepository", "Force refresh requested")
        val data = requestDataRemote()
        if (null == data) {
            withContext(Dispatchers.Main) {
                lastError.value = ErrorType.NETWORK_ERROR
            }
        } else {
            buffer = internalMapper.currencyFromResponse(data.body() ?: CBRResponse(mapOf()))
            GlobalScope.launch(Dispatchers.IO) {
                storeData(buffer)
            }
        }
        setError(ErrorType.NONE)

    }

    override suspend fun getConverterCurrencyList(): List<ConverterCurrency> {
        refreshBuffer()
        return converterMapper.fromDataLayerType(buffer)
    }

    override suspend fun getSimpleCurrencyList(): List<SimpleCurrency> {
        refreshBuffer()
        return currencyListMapper.fromDataLayerType(buffer)
    }

    private suspend fun setError(error: ErrorType) {
        if (error != lastError.value) {
            withContext(Dispatchers.Main) {
                lastError.value = error
            }
        }
    }

    private suspend fun refreshBuffer() {

        if (buffer.isNotEmpty()) {
            return
        }

        val response = requestDataRemote()
        if (null == response) {
            Log.w("ConverterRepository", "Unable to retrieve data from remote server")
            setError(ErrorType.NETWORK_ERROR)
            val saved = localRepo.getAllCurrencyData()
            if (saved.isEmpty()) {
                Log.w("ConverterRepository", "Unable to retrieve cached data")
                setError(ErrorType.DATABASE_ERROR)
            } else {
                buffer = saved.map { internalMapper.dtoToCurrency(it) }
            }
        } else {
            if (response.isSuccessful) {
                Log.i("ConverterRepository", "Successfully retrieved data from remote server")
                buffer = internalMapper.currencyFromResponse(response.body()!!)
                localRepo.addCurrencyAll(buffer.map { internalMapper.currencyToDto(it) })
            }
        }
    }

    private suspend fun storeData(currencyData: List<Currency>) {
        localRepo.addCurrencyAll(currencyData.map { internalMapper.currencyToDto(it) })
        Log.i("ConverterRepository", "Data cached successfully")
    }

    private suspend fun requestDataRemote(): Response<CBRResponse>? {
        var response: Response<CBRResponse>? = null
        try {
            response = remoteRepo.getCurrencyList()
        } catch (e: Exception) {
            Log.e("ConverterRepository", e.localizedMessage ?: "Unable to retrieve data from server")
        }
        return response
    }
}