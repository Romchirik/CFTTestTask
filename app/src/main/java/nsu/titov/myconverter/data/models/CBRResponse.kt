package nsu.titov.myconverter.data.models

import com.squareup.moshi.Json

data class CBRResponse(
	@Json(name = "Valute")
	val currencyItems: Map<String, Currency>
)

data class Currency(
	@Json(name = "ID")
	val id: String,
	@Json(name = "NumCode")
	val numCode: Int,
	@Json(name = "CharCode")
	val charCode: String,
	@Json(name = "Nominal")
	val nominal: Int,
	@Json(name = "Name")
	val name: String,
	@Json(name = "Value")
	val value: Double,
	@Json(name = "Previous")
	val previousValue: Double
)