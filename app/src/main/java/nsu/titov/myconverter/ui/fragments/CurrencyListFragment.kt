package nsu.titov.myconverter.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import nsu.titov.myconverter.R
import nsu.titov.myconverter.databinding.FragmentCurrencyListBinding
import nsu.titov.myconverter.domain.models.ErrorType
import nsu.titov.myconverter.presentation.CurrencyListViewModel
import nsu.titov.myconverter.ui.adapters.CurrencyListRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : Fragment(), CurrencyListRecyclerAdapter.OnRecyclerItemClickedListener {

    private lateinit var binding: FragmentCurrencyListBinding
    private val currencyListViewModel by viewModel<CurrencyListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = binding.currencyListRecyclerView

        val adapter = CurrencyListRecyclerAdapter(this)

        // setting up observers
        currencyListViewModel.errorType.observe(viewLifecycleOwner) { error ->
            onErrorOccurred(error)
        }

        currencyListViewModel.currencyData.observe(viewLifecycleOwner) { newData ->
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            newData?.let { data -> adapter.updateCurrencyData(data) }
        }

        // starting update
        currencyListViewModel.requestDataFromRepo()
        recycler.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener { onRefreshRequested() }
    }

    private fun onRefreshRequested() {
        currencyListViewModel.requestDataFromRepo()
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

    override fun onRecyclerItemClicked(code: String?) {
        Log.d("Bebra", code ?: "")
        if (null == code) return
        val bundle = bundleOf(ConverterFragment.PRESELECTED_CURR_CODE to code)
        findNavController().navigate(R.id.fromListToConverter, bundle)
    }
}