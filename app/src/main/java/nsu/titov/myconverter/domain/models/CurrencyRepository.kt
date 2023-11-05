package nsu.titov.myconverter.domain.models

interface CurrencyRepository {

	suspend fun fetchData()
	suspend fun getSimpleCached(): List<SimpleCurrency>?
	suspend fun getConverterCached(): List<ConverterCurrency>?
}