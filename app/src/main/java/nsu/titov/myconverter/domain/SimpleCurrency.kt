package nsu.titov.myconverter.domain

data class SimpleCurrency(
    val nominal: Int,
    val name: String,
    val currentValue: Double,
    val currencyTrend: CurrencyTrend
)