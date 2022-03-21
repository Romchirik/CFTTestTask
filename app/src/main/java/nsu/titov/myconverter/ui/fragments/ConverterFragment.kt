package nsu.titov.myconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nsu.titov.myconverter.databinding.FragmentConverterBinding
import nsu.titov.myconverter.presentation.CurrencyListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private val currencyListViewModel by viewModel<CurrencyListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }
}