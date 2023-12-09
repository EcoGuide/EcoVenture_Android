package com.example.ecoguide.View


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ecoguide.Model.Chambres
import com.example.ecoguide.Model.Guide
import com.example.myapplication.R
import com.squareup.picasso.Picasso

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tn.esprit.ecoventura.apiService.RoomApi

class roomDetailActivity : AppCompatActivity() {
//    lateinit var chambreRecyclerView: RecyclerView
//    private var guideList: ArrayList<guide> = ArrayList()
//    lateinit var guideAdapter: guideAdapter

    private lateinit var room: Chambres

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_room)
        val roomnameTextView: TextView = findViewById(R.id.roomNameTextView)
        val nbroomTextView: TextView = findViewById(R.id.capacityTextView)
        val imageView: ImageView = findViewById(R.id.imageViewRoom)
        val priceTextView:TextView = findViewById(R.id.roomPriceTextView)
        val bookNowButton: Button = findViewById(R.id.bookRoomButton)



        // get guidedId from intent
        val _id = intent.getStringExtra("_id")
        Log.d("idd",_id.toString())
        if (_id.isNullOrEmpty()) {
            showToast("Invalid room ID.")
            finish() // Close the activity if the ID is not provided.
            return
        }
        val apiService = RoomApi.create()
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getOnce(_id ?: "")
                if (response.isSuccessful) {
                    room = response.body()!!
                    Log.d("Room details",room.toString())

                    roomnameTextView.text = room.roomName?: "N/A"
                    nbroomTextView.text = "${room.nbChambreType?:0}"
                    priceTextView.text = "${room.price?:0}"
                    Picasso.get()
                        .load(room.image)
                        .into(imageView)

                } else {
                    showToast("Failed to fetch guided details.")
                    Log.e("API_ERROR", "Error: ${response.code()}")
                    Log.d("Room detailssss", {response.code()}.toString())

                }
            } catch (e: Exception) {
                showToast("An error occurred. Please try again later. ${e.message}")
                Log.e("roomDetailActivity", "Error: ${e.message}", e)
            }

        }
        bookNowButton.setOnClickListener {
            val intent = Intent(this, BookingFormRActivity::class.java)
            intent.putExtra("chambres", room)
            startActivity(intent)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}