package nsu.titov.myconverter.domain.models

interface Repository {
    fun getLastError(): ErrorType
    suspend fun forceRefreshData(): ErrorType
    suspend fun getConverterCurrencyList(): List<ConverterCurrency>?
    suspend fun getSimpleCurrencyList(): List<SimpleCurrency>?
}