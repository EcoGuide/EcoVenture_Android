package tn.esprit.ecoventura.apiService



import com.example.ecoguide.Model.HotelApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HotelApi {
    @GET("/api/hotels")
    suspend fun getAllHotels(): Response<HotelApiResponse>

    companion object {
        private var BASE_URL = "http://192.168.117.1:3000/"
        fun create(): HotelApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(HotelApi::class.java)
        }
    }
}