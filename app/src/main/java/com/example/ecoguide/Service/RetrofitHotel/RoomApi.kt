package tn.esprit.ecoventura.apiService



import Chambres
import ChambresApiResponse
import com.example.ecoguide.Model.Hotel
import com.example.ecoguide.Model.HotelApiResponse
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApi {
    @GET("/api/chambres")
    suspend fun getAllchambres(): Response<ChambresApiResponse>
    @GET("/api/chambre/{id}")
     fun getOnce(@Path("id") _id: String): Response<Chambres>

    companion object {
        private var BASE_URL = "http://192.168.117.1:3000/"
        fun create(): RoomApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RoomApi::class.java)
        }
    }
}