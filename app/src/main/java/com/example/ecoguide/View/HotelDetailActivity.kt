package com.example.ecoguide.View

import Chambres
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoguide.Model.HotelApiResponse
import com.example.ecoguide.adapter.RoomAdapter
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tn.esprit.ecoventura.apiService.HotelApi

class HotelDetailActivity : AppCompatActivity(), RoomAdapter.OnItemClickListener {
  private var roomList: ArrayList<Chambres> = ArrayList()
  lateinit var roomAdapter: RoomAdapter
lateinit var roomRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_hotel)
        roomRecyclerView = findViewById(R.id.recycler_view_Chambres)
        // Configure the RecyclerView
        val layoutManager = LinearLayoutManager(this)
        roomRecyclerView.layoutManager = layoutManager

        // Initialize and configure the adapter
        roomAdapter = RoomAdapter(this)
        roomRecyclerView.adapter = roomAdapter
        // Views

       val ivhoteldDetails: ImageView = findViewById(R.id.image_hotels)
        val tvNameDetails: TextView = findViewById(R.id.text_hotel_name)
        val tvDescriptionDetails: TextView = findViewById(R.id.text_star_count)
        val tvCarbonFootprint: TextView = findViewById(R.id.text_hotel_location)
        val tvWaterConsumption: TextView = findViewById(R.id.text_hotel_description)
//        val tvRecyclability: TextView = findViewById(R.id.tv_recyclability)

        // get hoteldId from intent
        val _id = intent.getStringExtra("_id")
        if (_id.isNullOrEmpty()) {
            showToast("Invalid hotel ID.")
            finish() // Close the activity if the ID is not provided.
            return
        }
        val apiService = HotelApi.create()
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getOnce(_id ?: "")
                val response2 = apiService.getchambreshotel(_id ?: "")

                if (response.isSuccessful) {
                    val hotel = response.body()


                    //charger l'image depuis l'URL
                    Picasso.get()
                        .load(hotel?.image)
                        .into(ivhoteldDetails)
                    tvNameDetails.text = hotel?.hotelname
                    tvDescriptionDetails.text ="${hotel?.nbStars}"
                    tvCarbonFootprint.text = hotel?.location
                    tvWaterConsumption.text = hotel?.description

                } else {
                    showToast("Failed to fetch hoteld details.")
                    Log.e("API_ERROR", "Error: ${response.code()}")

                }
                if (response2.isSuccessful) {
                    val chambre: ArrayList<Chambres>? = response2.body()

                    if (chambre != null) {
                        roomList = chambre
                        roomAdapter.setRooms(roomList)
                        Log.d("hotelList", "$roomList")
                    } else {
                        showToast("Error: HotelApiResponse is null.")
                    }
                } else {
                    when (response.code()) {
                        401 -> {
                            // Code 401: Unauthorized
                            // Redirect the user to the login page or show an error message
                            // showToast("Unauthorized. Redirecting to login.")
                        }
                        404 -> {
                            // Code 404: Not Found
                            // showToast("Hotels not found.")
                        }
                        else -> {
                            // Show a generic error message
                            // showToast("An error occurred.")
                        }
                    }
                }

            } catch (e: Exception) {
                showToast("An error occurred. Please try again later. ${e.message}")
                Log.e("HotelDetailActivity", "Error: ${e.message}", e)
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    override fun onItemClick(room: Chambres) {
        val intent = Intent(this, HotelDetailActivity::class.java)
        intent.putExtra("_id", room._id)
        startActivity(intent)    }
}