package nsu.titov.myconverter.ui.rv

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import nsu.titov.myconverter.R
import nsu.titov.myconverter.databinding.CurrencyRateItemBinding
import nsu.titov.myconverter.domain.models.CurrencyTrend
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyRateViewHolder(
	private val binding: CurrencyRateItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
	private var currencyCode: String? = null

	@SuppressLint("SetTextI18n")
	fun bind(currencyData: SimpleCurrency) {
		binding.charCodeView.text = currencyData.charCode
		binding.nominalNameView.text = "%s %s".format(currencyData.nominal, currencyData.name)
		binding.valueView.text = "%.2f".format(currencyData.value)
		binding.currencyTrendView.setImageResource(currencyTrendToPic(currencyData.trend))
		currencyCode = currencyData.charCode
	}

	private fun currencyTrendToPic(currencyTrend: CurrencyTrend): Int {
		return when (currencyTrend) {
			CurrencyTrend.RISING -> R.drawable.ic_baseline_keyboard_arrow_up_24
			CurrencyTrend.FALLING -> R.drawable.ic_baseline_keyboard_arrow_down_24
		}
	}
}