package nsu.titov.myconverter.domain.mappers

interface CurrencyMapper<FromType, ToType> {

	fun fromData(value: FromType): ToType

	fun List<FromType>.fromData(): List<ToType> {
		return this.map { fromData(it) }
	}
}