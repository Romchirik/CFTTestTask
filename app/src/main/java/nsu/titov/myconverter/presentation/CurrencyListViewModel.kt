package nsu.titov.myconverter.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyListViewModel(private val repository: Repository) : ViewModel() {
    val currencyData: MutableLiveData<List<SimpleCurrency>?> = MutableLiveData()
    val errorType = repository.getLastError()

    fun requestDataFromRepo() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSimpleCurrencyList()
            if (response.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    currencyData.value = response
                }
            }
        }
    }

    fun forceRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.forceRefreshData()
        }
        requestDataFromRepo()
    }
}