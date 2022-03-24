package nsu.titov.myconverter.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nsu.titov.myconverter.data.dao.CurrencyDao
import nsu.titov.myconverter.data.models.CurrencyDto

@Database(entities = [CurrencyDto::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}