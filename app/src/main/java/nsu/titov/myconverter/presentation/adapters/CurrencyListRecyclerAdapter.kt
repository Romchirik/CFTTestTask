package nsu.titov.myconverter.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.titov.myconverter.databinding.CurrencyRateItemBinding
import nsu.titov.myconverter.domain.CurrencyTrend
import nsu.titov.myconverter.domain.SimpleCurrency


class CurrencyListRecyclerAdapter : RecyclerView.Adapter<CurrencyListRecyclerAdapter.CurrencyRateViewHolder>() {

    private val currencyList: List<SimpleCurrency> = listOf(
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING),
        SimpleCurrency(1, "", 2.0, CurrencyTrend.RISING)
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val viewHolderBinding =
            CurrencyRateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyRateViewHolder(viewHolderBinding)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        val item = currencyList[position]

    }

    override fun getItemCount(): Int {
        return currencyList.size
    }


    class CurrencyRateViewHolder(private val binding: CurrencyRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyData: SimpleCurrency) {
            //TODO("Fill this with code")
        }
    }
}