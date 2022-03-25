package nsu.titov.myconverter.domain.models

import androidx.lifecycle.LiveData

interface Repository {
    fun getLastError(): LiveData<ErrorType>
    suspend fun forceRefreshData()
    suspend fun getConverterCurrencyList(): List<ConverterCurrency>
    suspend fun getSimpleCurrencyList(): List<SimpleCurrency>
}