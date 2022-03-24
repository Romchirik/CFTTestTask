package nsu.titov.myconverter.data.mappers

import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.ConverterCurrency

class CurrencyToConverterMapper : CurrencyMapper<Currency, ConverterCurrency> {
    override fun fromDataLayerType(values: List<Currency>): List<ConverterCurrency> {
        return values.map { currency ->
            ConverterCurrency(
                nominal = currency.nominal,
                charCode = currency.charCode,
                value = currency.value,
            )
        }
    }
}