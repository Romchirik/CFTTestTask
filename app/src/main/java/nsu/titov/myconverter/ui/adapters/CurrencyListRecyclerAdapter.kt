package nsu.titov.myconverter.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.titov.myconverter.R
import nsu.titov.myconverter.databinding.CurrencyRateItemBinding
import nsu.titov.myconverter.domain.models.CurrencyTrend
import nsu.titov.myconverter.domain.models.SimpleCurrency


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
    fun updateCurrencyData(list: List<SimpleCurrency>) {
        currencyList = list
        notifyDataSetChanged()
    }

    class CurrencyRateViewHolder(private val binding: CurrencyRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(currencyData: SimpleCurrency) {
            binding.charCodeView.text = currencyData.charCode
            binding.nominalNameView.text = "%s %s".format(currencyData.nominal, currencyData.name)
            binding.valueView.text = "%.2f".format(currencyData.value)
            binding.currencyTrendView.setImageResource(currencyTrendToPic(currencyData.trend))
        }

        private fun currencyTrendToPic(currencyTrend: CurrencyTrend): Int {
            return when (currencyTrend) {
                CurrencyTrend.RISING -> R.drawable.ic_baseline_keyboard_arrow_up_24
                CurrencyTrend.FALLING -> R.drawable.ic_baseline_keyboard_arrow_down_24
            }
        }
    }
}