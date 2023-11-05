package nsu.titov.myconverter.domain.models

data class SimpleCurrency(
	val nominal: Int,
	val charCode: String,
	val name: String,
	val value: Double,
	val trend: CurrencyTrend
)