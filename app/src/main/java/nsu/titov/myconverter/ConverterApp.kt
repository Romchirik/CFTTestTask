package nsu.titov.myconverter

import android.app.Application
import nsu.titov.myconverter.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConverterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConverterApp)
            modules(listOf(appModule))
        }
    }
}