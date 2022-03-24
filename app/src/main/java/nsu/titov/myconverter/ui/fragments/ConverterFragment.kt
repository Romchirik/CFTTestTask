package nsu.titov.myconverter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import nsu.titov.myconverter.R
import nsu.titov.myconverter.databinding.FragmentConverterBinding
import nsu.titov.myconverter.presentation.ConverterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val converterViewModel by viewModel<ConverterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        converterViewModel.getCurrencyData().observe(viewLifecycleOwner) { newData ->
            val items = newData.map { it.charCode }
            adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            binding.currencySelectorView.setAdapter(adapter)
        }
        converterViewModel.requestDataFromRepo()

        val preselectedCurrencyCode = arguments?.get(PRESELECTED_CURR_CODE) as String?

        binding.convertButton.setOnClickListener { onConvertRequested() }
        binding.convertResultView.text = converterViewModel.lastConvertedValue
        binding.currencySelectorView.setText(preselectedCurrencyCode ?: "")

    }

    private fun onConvertRequested(showHints: Boolean = true) {
        val code = binding.currencySelectorView.text?.toString() ?: run {
            if (showHints) showInputHint(getString(R.string.currency_not_selected_hint))
            return
        }
        if (code.isBlank() || code.isEmpty()) {
            if (showHints) showInputHint(getString(R.string.currency_not_selected_hint))
            return
        }
        val value = binding.valueInput.text?.toString()?.toDoubleOrNull() ?: run {
            if (showHints) showInputHint(getString(R.string.no_input_value_hint))
            return
        }
        val result = converterViewModel.anyFromRub(value, code) ?: run {
            showInputHint(getString(R.string.internal_error_hint))
            0.0
        }

        val text = getString(R.string.converter_result_format).format(result, code)
        binding.convertResultView.text = text
        converterViewModel.lastConvertedValue = text
    }

    private fun showInputHint(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val PRESELECTED_CURR_CODE = "preselectedCurrency"
    }
}