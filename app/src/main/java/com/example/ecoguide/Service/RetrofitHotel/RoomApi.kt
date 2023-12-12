package tn.esprit.ecoventura.apiService



import com.example.ecoguide.Model.Chambres
import com.example.ecoguide.Model.ChambresApiResponse
import com.example.ecoguide.Model.Hotel
<<<<<<< Updated upstream
import com.example.ecoguide.Model.HotelApiResponse
import com.google.gson.Gson
=======
import com.example.ecoguide.Model.ReservationRRequest
import com.example.ecoguide.Model.ReservationRResponse
>>>>>>> Stashed changes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomApi {

    @GET("/api/chambre/{id}")
<<<<<<< Updated upstream
     fun getOnce(@Path("id") _id: String): Response<Chambres>
=======
    suspend fun getOnce(@Path("id") _id: String): Response<Chambres>
    @POST("/api/reservationH/{id}/add")
    suspend fun addRoomReservation(
        @Path("id") chambreId: String,
        @Body reservationRRequest: ReservationRRequest
    ): Response<ReservationRResponse>
>>>>>>> Stashed changes

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