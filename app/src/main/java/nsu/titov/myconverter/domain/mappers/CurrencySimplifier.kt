package nsu.titov.myconverter.domain.mappers

import nsu.titov.myconverter.domain.models.SimpleCurrency

interface CurrencySimplifier<NetworkType> {
    fun fromResponse(entity: NetworkType): List<SimpleCurrency>
}