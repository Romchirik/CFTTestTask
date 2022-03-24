package nsu.titov.myconverter.data.mappers

import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.CurrencyTrend
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyToSimpleMapper : CurrencyMapper<Currency, SimpleCurrency> {
    override fun fromDataLayerType(values: List<Currency>): List<SimpleCurrency> {
        return values.map { currency ->
            SimpleCurrency(
                nominal = currency.nominal,
                charCode = currency.charCode,
                name = currency.name,
                value = currency.value,
                trend = if (currency.previousValue > currency.value) {
                    CurrencyTrend.FALLING
                } else {
                    CurrencyTrend.RISING
                }
            )
        }
    }

}