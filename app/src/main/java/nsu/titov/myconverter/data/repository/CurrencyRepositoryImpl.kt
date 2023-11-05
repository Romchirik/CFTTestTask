package nsu.titov.myconverter.data.repository

import androidx.annotation.WorkerThread
import nsu.titov.myconverter.data.dao.CurrencyDao
import nsu.titov.myconverter.data.mappers.RepositoryInternalMapper
import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.data.network.CBRApiService
import nsu.titov.myconverter.domain.NetworkException
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.ConverterCurrency
import nsu.titov.myconverter.domain.models.CurrencyRepository
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyRepositoryImpl(
	private val currencyListMapper: CurrencyMapper<Currency, SimpleCurrency>,
	private val converterMapper: CurrencyMapper<Currency, ConverterCurrency>,
	private val internalMapper: RepositoryInternalMapper,
	private val localStorage: CurrencyDao,
	private val remoteService: CBRApiService,
) : CurrencyRepository {

	private var cache: List<Currency>? = null

	override suspend fun fetchData() {
		try {
			val data = remoteService.getCurrencyList()
			val mapped = internalMapper.currencyFromResponse(data.body()!!)
			cache = mapped
			localStorage.addCurrencyAll(
				cache?.map { internalMapper.currencyToDto(it) }.orEmpty()
			)
		} catch (e: Throwable) {
			throw NetworkException("Failed to fetch currencies")
		}
	}

	override suspend fun getSimpleCached(): List<SimpleCurrency>? = with(currencyListMapper) {
		if (cache == null) {
			refreshCache()
		}
		cache?.fromData()
	}

	override suspend fun getConverterCached(): List<ConverterCurrency>? = with(converterMapper) {
		if (cache == null) {
			refreshCache()
		}
		cache?.fromData()
	}

	private suspend fun refreshCache() {
		cache = getFromDatabase()
	}

	@WorkerThread
	private suspend fun getFromDatabase(): List<Currency> = localStorage.getAllCurrencyData().map {
		internalMapper.dtoToCurrency(it)
	}
}
