package nsu.titov.myconverter.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.titov.myconverter.R
import nsu.titov.myconverter.databinding.CurrencyRateItemBinding
import nsu.titov.myconverter.domain.CurrencyTrend
import nsu.titov.myconverter.domain.SimpleCurrency


class CurrencyListRecyclerAdapter :
    RecyclerView.Adapter<CurrencyListRecyclerAdapter.CurrencyRateViewHolder>() {

    private var currencyList: List<SimpleCurrency> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val viewHolderBinding =
            CurrencyRateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyRateViewHolder(viewHolderBinding)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        val item = currencyList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrencyList(list: List<SimpleCurrency>) {
        currencyList = list
        notifyDataSetChanged()
    }

    class CurrencyRateViewHolder(private val binding: CurrencyRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyData: SimpleCurrency) {
            binding.charCodeView.text = currencyData.charCode
            binding.nominalNameView.text = "%s %s".format(currencyData.nominal, currencyData.name)
            binding.valueView.text = "%.2lf".format(currencyData.currentValue)
            binding.currencyTrendView.setImageResource(currencyTrendToPic(currencyData.currencyTrend))
        }

        private fun currencyTrendToPic(currencyTrend: CurrencyTrend): Int {
            return when (currencyTrend) {
                CurrencyTrend.RISING -> R.drawable.ic_baseline_keyboard_arrow_up_24
                CurrencyTrend.FALLING -> R.drawable.ic_baseline_keyboard_arrow_down_24
            }
        }
    }
}