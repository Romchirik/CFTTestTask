package nsu.titov.myconverter.domain

data class SimpleCurrency(
    val nominal: Int,
    val charCode: String,
    val name: String,
    val currentValue: Double,
    val currencyTrend: CurrencyTrend
)