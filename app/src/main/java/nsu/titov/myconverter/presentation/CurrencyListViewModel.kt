package nsu.titov.myconverter.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.myconverter.domain.models.ErrorType
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyListViewModel(private val repository: Repository) : ViewModel() {
    val currencyData: MutableLiveData<List<SimpleCurrency>?> = MutableLiveData()
    val errorType: MutableLiveData<ErrorType> = MutableLiveData()

    fun requestDataFromRepo() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSimpleCurrencyList()
            if (null == response) {
                withContext(Dispatchers.Main) {
                    errorType.value = repository.getLastError()
                }
            } else {
                withContext(Dispatchers.Main) {
                    currencyData.value = response
                }
            }
        }
    }
}