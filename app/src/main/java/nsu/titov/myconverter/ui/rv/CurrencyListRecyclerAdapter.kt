package nsu.titov.myconverter.ui.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.titov.myconverter.databinding.CurrencyRateItemBinding
import nsu.titov.myconverter.domain.models.SimpleCurrency

class CurrencyListRecyclerAdapter : RecyclerView.Adapter<CurrencyRateViewHolder>() {

	private var currencyList: List<SimpleCurrency> = emptyList()

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
}