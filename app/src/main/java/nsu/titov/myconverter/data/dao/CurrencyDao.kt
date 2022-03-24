package nsu.titov.myconverter.data.dao

import androidx.room.*
import nsu.titov.myconverter.data.models.CurrencyDto

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currency: CurrencyDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyAll(currencyList: List<CurrencyDto>)

    @Query("SELECT * FROM currencies_cached ORDER BY id")
    suspend fun getAllCurrencyData(): List<CurrencyDto>
}