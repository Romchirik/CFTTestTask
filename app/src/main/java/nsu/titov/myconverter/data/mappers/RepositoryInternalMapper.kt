package nsu.titov.myconverter.data.mappers

import nsu.titov.myconverter.data.models.CBRResponse
import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.data.models.CurrencyDto

class RepositoryInternalMapper {
    fun currencyFromResponse(response: CBRResponse): List<Currency> {
        return response.currencyItems.values.toList().sortedBy { it.id }
    }

    fun dtoToCurrency(currencyDto: CurrencyDto): Currency {
        return Currency(
            id = currencyDto.id,
            numCode = currencyDto.numCode,
            charCode = currencyDto.charCode,
            nominal = currencyDto.nominal,
            name = currencyDto.name,
            value = currencyDto.value,
            previousValue = currencyDto.previousValue
        )
    }

    fun currencyToDto(currency: Currency): CurrencyDto {
        return CurrencyDto(
            id = currency.id,
            numCode = currency.numCode,
            charCode = currency.charCode,
            nominal = currency.nominal,
            name = currency.name,
            value = currency.value,
            previousValue = currency.previousValue
        )
    }
}