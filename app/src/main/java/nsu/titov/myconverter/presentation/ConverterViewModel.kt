package nsu.titov.myconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.myconverter.ConverterApp
import nsu.titov.myconverter.data.mappers.CurrencyToConverterMapper
import nsu.titov.myconverter.data.mappers.CurrencyToSimpleMapper
import nsu.titov.myconverter.data.mappers.RepositoryInternalMapper
import nsu.titov.myconverter.data.network.RetrofitInstance
import nsu.titov.myconverter.data.repository.CurrencyRepositoryImpl
import nsu.titov.myconverter.domain.models.ConverterCurrency
import nsu.titov.myconverter.domain.models.CurrencyRepository

//TODO DI
class ConverterViewModel : ViewModel() {

	private val repository: CurrencyRepository = CurrencyRepositoryImpl(
		currencyListMapper = CurrencyToSimpleMapper(),
		converterMapper = CurrencyToConverterMapper(),
		internalMapper = RepositoryInternalMapper(),
		localStorage = ConverterApp.databaseInstance.currencyDao(),
		remoteService = RetrofitInstance().api,
	)

	private val currencyData: MutableLiveData<List<ConverterCurrency>> = MutableLiveData(listOf())
	var lastConvertedValue = "0.0"

	fun getCurrencyData(): LiveData<List<ConverterCurrency>> {
		return currencyData
	}

	/** Convert rubble value to selected currency value,
	 * return null if unable to find char code in currencyData
	 * @param value rubble value
	 * @param charCode target currency char code
	 */
	fun anyFromRub(value: Double, charCode: String): Double? {
		val converterItem = currencyData.value?.find { charCode == it.charCode }
		converterItem?.let {
			return it.nominal * value / it.value
		}
		return null
	}

	fun update() {
		viewModelScope.launch(Dispatchers.IO) {
			val data = repository.getConverterCached() ?: return@launch
			withContext(Dispatchers.Main) {
				currencyData.value = data
			}

		}
	}
}