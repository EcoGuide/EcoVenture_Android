package tn.esprit.ecoventura.apiService



import Chambres
import com.example.ecoguide.Model.Hotel
import com.example.ecoguide.Model.HotelApiResponse
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelApi {
    @GET("/api/hotels")
    suspend fun getAllHotels(): Response<HotelApiResponse>
    @GET("/api/hotel/{id}")
    suspend fun getOnce(@Path("id") _id: String): Response<Hotel>
    @GET("/api/hotel/{id}/chambres")
    suspend fun getchambreshotel(@Path("id") _id: String): Response< ArrayList<Chambres>>
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