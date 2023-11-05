package nsu.titov.myconverter.data.network

import nsu.titov.myconverter.data.models.CBRResponse
import retrofit2.Response
import retrofit2.http.GET

interface CBRApiService {
	@GET("/daily_json.js")
	suspend fun getCurrencyList(): Response<CBRResponse>
}
