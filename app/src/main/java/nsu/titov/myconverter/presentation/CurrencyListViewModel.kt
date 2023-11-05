package nsu.titov.myconverter.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.myconverter.ConverterApp
import nsu.titov.myconverter.data.mappers.CurrencyToConverterMapper
import nsu.titov.myconverter.data.mappers.CurrencyToSimpleMapper
import nsu.titov.myconverter.data.mappers.RepositoryInternalMapper
import nsu.titov.myconverter.data.network.RetrofitInstance
import nsu.titov.myconverter.data.repository.CurrencyRepositoryImpl
import nsu.titov.myconverter.domain.models.CurrencyRepository
import nsu.titov.myconverter.domain.models.SimpleCurrency
import nsu.titov.myconverter.ui.AndroidToaster

//TODO DI
class CurrencyListViewModel : ViewModel() {

	private val repository: CurrencyRepository = CurrencyRepositoryImpl(
		currencyListMapper = CurrencyToSimpleMapper(),
		converterMapper = CurrencyToConverterMapper(),
		internalMapper = RepositoryInternalMapper(),
		localStorage = ConverterApp.databaseInstance.currencyDao(),
		remoteService = RetrofitInstance().api,
	)
	private val toaster: Toaster = AndroidToaster(ConverterApp.context)

	val currencyData: MutableLiveData<List<SimpleCurrency>> = MutableLiveData()
	private val toasterHandler = CoroutineExceptionHandler { _, exception ->
		toaster.showToast(exception.message)
	}

	fun getCurrencyData() {
		viewModelScope.launch(Dispatchers.IO + toasterHandler) {
			var data = repository.getSimpleCached()
			if (data == null) {
				repository.fetchData()
			}
			withContext(Dispatchers.Main) {
				currencyData.value = repository.getSimpleCached()
			}
		}
	}

	fun forceRefresh() {
		viewModelScope.launch(Dispatchers.IO + toasterHandler) {
			repository.fetchData()
			withContext(Dispatchers.Main) {
				currencyData.value = repository.getSimpleCached()
			}
		}
	}
}