package nsu.titov.myconverter.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInstance {
	val api: CBRApiService by lazy {
		val moshi = Moshi.Builder()
			.add(KotlinJsonAdapterFactory())
			.build()

		Retrofit.Builder()
			.baseUrl("https://www.cbr-xml-daily.ru")
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.build()
			.create(CBRApiService::class.java)
	}
}