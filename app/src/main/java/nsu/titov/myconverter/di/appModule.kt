package nsu.titov.myconverter.di

import nsu.titov.myconverter.data.mappers.CurrencyToConverterMapper
import nsu.titov.myconverter.data.mappers.CurrencyToSimpleMapper
import nsu.titov.myconverter.data.mappers.RepositoryInternalMapper
import nsu.titov.myconverter.data.models.Currency
import nsu.titov.myconverter.data.repository.RepositoryImpl
import nsu.titov.myconverter.domain.mappers.CurrencyMapper
import nsu.titov.myconverter.domain.models.ConverterCurrency
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.domain.models.SimpleCurrency
import nsu.titov.myconverter.presentation.ConverterViewModel
import nsu.titov.myconverter.presentation.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {


    single<Repository> {
        RepositoryImpl(
            get(named("CurrencyToSimple")),
            get(named("CurrencyToConverter")),
            get()
        )
    }

    factory<CurrencyMapper<Currency, SimpleCurrency>>(named("CurrencyToSimple")) { CurrencyToSimpleMapper() }
    factory<CurrencyMapper<Currency, ConverterCurrency>>(named("CurrencyToConverter")) { CurrencyToConverterMapper() }
    factory { RepositoryInternalMapper() }


    viewModel { ConverterViewModel(get()) }
    viewModel { CurrencyListViewModel(get()) }
}