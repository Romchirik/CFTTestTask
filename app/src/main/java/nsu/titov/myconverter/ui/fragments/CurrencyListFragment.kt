package nsu.titov.myconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import nsu.titov.myconverter.data.models.ResponseDtoMapper
import nsu.titov.myconverter.data.repository.RepositoryImpl
import nsu.titov.myconverter.databinding.FragmentCurrencyListBinding
import nsu.titov.myconverter.domain.models.ErrorType
import nsu.titov.myconverter.presentation.SharedViewModel
import nsu.titov.myconverter.presentation.SharedViewModelFactory
import nsu.titov.myconverter.ui.adapters.CurrencyListRecyclerAdapter

class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)

        val repository = RepositoryImpl(ResponseDtoMapper())
        val viewModelFactory = SharedViewModelFactory(repository)

        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[SharedViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = binding.currencyListRecyclerView
        val adapter = CurrencyListRecyclerAdapter()

        //setting up observers
        viewModel.errorType.observe(viewLifecycleOwner, Observer { error ->
            onErrorOccurred(error)
        })

        viewModel.currencyData.observe(viewLifecycleOwner, Observer { newData ->
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            newData?.let { data -> adapter.updateCurrencyData(data) }
        })

        //starting update
        viewModel.getCurrencyData()
        recycler.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener { onRefreshRequested() }
    }

    private fun onRefreshRequested() {
        viewModel.getCurrencyData()
    }

    private fun onErrorOccurred(type: ErrorType) {
        val message = when (type) {
            ErrorType.UNKNOWN -> "Unknown error occurred"
            ErrorType.NETWORK_ERROR -> "Network error, check network connection"
            //just do nothing
            ErrorType.NONE, ErrorType.DATABASE_ERROR -> return
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}