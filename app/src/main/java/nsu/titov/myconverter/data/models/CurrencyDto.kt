package nsu.titov.myconverter.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies_cached")
data class CurrencyDto(
	@PrimaryKey
	val id: String,
	@ColumnInfo(name = "num_code")
	val numCode: Int,
	@ColumnInfo(name = "char_code")
	val charCode: String,
	val nominal: Int,
	val name: String,
	val value: Double,
	@ColumnInfo(name = "previous_value")
	val previousValue: Double
)