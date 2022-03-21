package nsu.titov.myconverter.data.models

import nsu.titov.myconverter.domain.mappers.CurrencySimplifier
import nsu.titov.myconverter.domain.models.CurrencyTrend
import nsu.titov.myconverter.domain.models.SimpleCurrency

class ResponseDtoMapper : CurrencySimplifier<CBRResponse> {
    override fun fromResponse(entity: CBRResponse): List<SimpleCurrency> {
        return entity.currencyItems.values.map { item ->
            SimpleCurrency(
                nominal = item.nominal,
                charCode = item.charCode,
                name = item.name,
                value = item.value,
                trend = if (item.previousValue > item.value) {
                    CurrencyTrend.FALLING
                } else {
                    CurrencyTrend.RISING
                }
            )
        }.toList()
    }

}