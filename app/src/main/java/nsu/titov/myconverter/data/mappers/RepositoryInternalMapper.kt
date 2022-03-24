package nsu.titov.myconverter.data.mappers

import nsu.titov.myconverter.data.models.CBRResponse
import nsu.titov.myconverter.data.models.Currency

class RepositoryInternalMapper {
    fun currencyFromResponse(response: CBRResponse): List<Currency> {
        return response.currencyItems.values.toList()
    }
}