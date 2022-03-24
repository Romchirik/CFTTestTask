package nsu.titov.myconverter.domain.mappers

interface CurrencyMapper<DataLayerType, ToType> {
    fun fromDataLayerType(values: List<DataLayerType>): List<ToType>
}