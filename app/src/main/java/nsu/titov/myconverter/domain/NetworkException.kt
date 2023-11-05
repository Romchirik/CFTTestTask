package nsu.titov.myconverter.domain

//это если что не лучший пример, но пойдет
sealed class ConverterException : Throwable()

class NetworkException(
	val info: String = "Unknown exception"
) : ConverterException()