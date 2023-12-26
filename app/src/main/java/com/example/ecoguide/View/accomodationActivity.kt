package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoguide.Model.Hotel
import com.example.ecoguide.adapter.HotelAdapter
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.text.style.ForegroundColorSpan
import android.graphics.Color
import android.util.Log
import com.example.ecoguide.Model.HotelApiResponse
import tn.esprit.ecoventura.apiService.HotelApi

class AccommodationActivity : AppCompatActivity(), HotelAdapter.OnItemClickListener {
    lateinit var hotelAdapter: HotelAdapter
    lateinit var hotelRecyclerView: RecyclerView
    private var hotelList: ArrayList<Hotel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        setContentView(R.layout.activity_accomodation_main)
        hotelRecyclerView = findViewById(R.id.recycler_view_Hotels)

        // Configure the RecyclerView
        val layoutManager = LinearLayoutManager(this)
        hotelRecyclerView.layoutManager = layoutManager

        // Initialize and configure the adapter
        hotelAdapter = HotelAdapter(this)
        hotelRecyclerView.adapter = hotelAdapter

        loadHotelData()
    }

    private fun loadHotelData() {
        val apiService = HotelApi.create()
        val sharedPref = getSharedPreferences(
            "com.example.myapp.PREFERENCE_FILE_KEY",
            Context.MODE_PRIVATE
        )
        val header = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null) ;

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                Log.d("tag", "zzzzzz")
                // thot token f authorization (header)

                val response = apiService.getAllHotels("Bearer ${header.toString() }")
                Log.d("tag", "${response.body()}")

                if (response.isSuccessful) {
                    val hotelApiResponse: HotelApiResponse? = response.body()

                    if (hotelApiResponse != null) {
                        hotelList = ArrayList(hotelApiResponse.hotels)
                        hotelAdapter.setHotels(hotelList)
                        Log.d("hotelList", "$hotelList")
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
                Log.d("aaw", "${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun styleTextView(textView: TextView, partOne: String, partTwo: String) {
        val spannableString = SpannableString(partOne + partTwo)

        spannableString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            partOne.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#44F1A6")),
            partOne.length,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableString
    }

    override fun onItemClick(hotel: Hotel) {
        val intent = Intent(this, HotelDetailActivity::class.java)
        intent.putExtra("_id", hotel._id)
        startActivity(intent)    }
}