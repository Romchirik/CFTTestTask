package nsu.titov.myconverter.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import nsu.titov.myconverter.data.models.CBRResponse
import nsu.titov.myconverter.data.models.ResponseDtoMapper
import nsu.titov.myconverter.data.network.RetrofitInstance
import nsu.titov.myconverter.domain.mappers.CurrencySimplifier
import nsu.titov.myconverter.domain.models.ErrorType
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.domain.models.SimpleCurrency
import retrofit2.Response

class RepositoryImpl(private val outerWrapper: CurrencySimplifier<CBRResponse>) : Repository {
    private var lastError = ErrorType.NONE


    override suspend fun getCurrencyList(): List<SimpleCurrency>? {
        val response = requestData()
        if (null == response) {
            lastError = ErrorType.NETWORK_ERROR
            return null
        } else {
            if (response.isSuccessful) {
                return outerWrapper.fromResponse(response.body()!!)
            }
        }

        Log.e("RepositoryNetwork", "It not supposed to reach this place...")
        lastError = ErrorType.UNKNOWN
        return null
    }

    override fun getErrorType(): ErrorType {
        return lastError
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