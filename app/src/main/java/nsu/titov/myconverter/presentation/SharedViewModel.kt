package nsu.titov.myconverter.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.myconverter.domain.models.ErrorType
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.domain.models.SimpleCurrency

class SharedViewModel(private val repository: Repository) : ViewModel() {
    val currencyData: MutableLiveData<List<SimpleCurrency>?> = MutableLiveData()
    val errorType: MutableLiveData<ErrorType> = MutableLiveData()


    fun getCurrencyData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCurrencyList()
            if (null == response) {
                withContext(Dispatchers.Main) {
                    errorType.value = repository.getErrorType()
                }
            } else {
                withContext(Dispatchers.Main) {
                    currencyData.value = response
                }
            }
        }
    }
}