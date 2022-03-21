package nsu.titov.myconverter.data.models

import com.squareup.moshi.Json

data class CBRResponse(
    @Json(name = "Valute")
    val currencyItems: Map<String, CurrencyEntity>
)