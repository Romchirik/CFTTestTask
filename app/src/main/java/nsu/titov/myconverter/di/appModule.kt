package nsu.titov.myconverter.di

import nsu.titov.myconverter.data.models.CBRResponse
import nsu.titov.myconverter.data.models.ResponseDtoMapper
import nsu.titov.myconverter.data.repository.RepositoryImpl
import nsu.titov.myconverter.domain.mappers.CurrencySimplifier
import nsu.titov.myconverter.domain.models.Repository
import nsu.titov.myconverter.presentation.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<CurrencySimplifier<CBRResponse>> { ResponseDtoMapper() }

    single<Repository> { RepositoryImpl(get()) }

    viewModel { CurrencyListViewModel(get()) }
    viewModel { CurrencyListViewModel(get()) }
}