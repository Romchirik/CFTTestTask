package nsu.titov.myconverter.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyEntity(
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