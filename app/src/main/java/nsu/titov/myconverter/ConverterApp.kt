package nsu.titov.myconverter

import android.app.Application
import android.content.Context
import androidx.room.Room
import nsu.titov.myconverter.data.CurrencyDatabase

class ConverterApp : Application() {

	override fun onCreate() {
		super.onCreate()

		//TODO DI
		databaseInstance = Room.databaseBuilder(
			this,
			CurrencyDatabase::class.java,
			"CurrencyDatabase"
		).build()
		context = this
	}

	companion object {
		//TODO DI
		lateinit var context: Context
		//TODO DI
		lateinit var databaseInstance: CurrencyDatabase
	}
}