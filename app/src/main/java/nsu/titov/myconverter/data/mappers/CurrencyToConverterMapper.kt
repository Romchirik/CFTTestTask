package nsu.titov.myconverter.data.mappers

import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.ConverterCurrency

class CurrencyToConverterMapper : CurrencyMapper<Currency, ConverterCurrency> {
	override fun fromData(value: Currency): ConverterCurrency = ConverterCurrency(
		nominal = value.nominal,
		charCode = value.charCode,
		value = value.value,
	)
}