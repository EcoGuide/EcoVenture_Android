package tn.esprit.ecoventura.apiService



import com.example.ecoguide.Model.Chambres
import com.example.ecoguide.Model.Hotel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RoomApi {


    @GET("/api/chambre/{id}")
    suspend fun getOnce(@Header("Authorization") header: String,@Path("id") _id: String): Response<Chambres>
    companion object {
        private var BASE_URL = "http://192.168.1.126:3000/"
        fun create(): RoomApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RoomApi::class.java)
        }
    }
}