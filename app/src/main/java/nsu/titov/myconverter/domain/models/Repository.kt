package nsu.titov.myconverter.domain.models

import nsu.titov.myconverter.data.models.CBRResponse
import retrofit2.Response

interface Repository {
    suspend fun getCurrencyList(): List<SimpleCurrency>?
    fun getErrorType(): ErrorType
}