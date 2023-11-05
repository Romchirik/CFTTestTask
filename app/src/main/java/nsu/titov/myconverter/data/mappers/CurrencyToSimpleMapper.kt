package nsu.titov.myconverter.data.mappers

import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.CurrencyTrend
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyToSimpleMapper : CurrencyMapper<Currency, SimpleCurrency> {

	override fun fromData(value: Currency): SimpleCurrency = SimpleCurrency(
		nominal = value.nominal,
		charCode = value.charCode,
		name = value.name,
		value = value.value,
		trend = if (value.previousValue > value.value) {
			CurrencyTrend.FALLING
		} else {
			CurrencyTrend.RISING
		}
	)
}