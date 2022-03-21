package nsu.titov.myconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nsu.titov.myconverter.domain.models.Repository

class SharedViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedViewModel(repository) as T
    }
}