
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ecoguide.View.BookingFormActivity
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoventura.apiService.RoomApi

class RoomDetailActivity : AppCompatActivity() {

    private lateinit var room: Chambres

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_room)

        val imageView: ImageView = findViewById(R.id.imageViewRoom)
        val roomNameTextView: TextView = findViewById(R.id.roomNameTextView)
        val capacityTextView: TextView = findViewById(R.id.capacityTextView)
        val roomPriceTextView: TextView = findViewById(R.id.roomPriceTextView)
        val bookRoomButton: Button = findViewById(R.id.bookRoomButton)

        // Get roomId from intent
        val _id = intent.getStringExtra("_id")

        // Initialize RoomApi
        val roomApi = RoomApi.create()

        // Make a network request to get room details
        val response = roomApi.getOnce(_id ?: "")
        /*call.enqueue(object : Callback<Chambres> {
            override fun onResponse(call: Call<Chambres>, response: Response<Chambres>) {
                if (response.isSuccessful) {
                    room = response.body()!!

                    // Bind room details to views
                    imageView.setImageResource(R.drawable.sample_room_image) // Replace with actual image loading logic
                    roomNameTextView.text = room.name
                    capacityTextView.text = "Capacity: ${room.capacity}"
                    roomPriceTextView.text = "Room Price: ${room.price}"

                    // Set click listener for the book button
                    bookRoomButton.setOnClickListener {
                        val intent = Intent(this@RoomDetailActivity, BookingFormActivity::class.java)
                        intent.putExtra("ROOM_OBJECT", room)
                        startActivity(intent)
                    }
                } else {
                    // Handle error
                    showToast("Failed to fetch room details")
                }
            }

            override fun onFailure(call: Call<Room>, t: Throwable) {
                // Handle failure
                showToast("Network request failed: ${t.message}")
            }
        })*/
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}