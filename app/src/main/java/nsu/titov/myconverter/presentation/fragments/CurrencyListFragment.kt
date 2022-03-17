package nsu.titov.myconverter.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nsu.titov.myconverter.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

}