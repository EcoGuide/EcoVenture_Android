package com.example.ecoguide.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoguide.Model.Chambres
import com.example.ecoguide.Model.ReservationRRequest
import com.example.myapplication.R
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tn.esprit.ecoventura.apiService.RoomApi
import java.util.Calendar
import java.util.Date


class BookingFormActivity : AppCompatActivity() {

    private lateinit var nbDayEditText: EditText
    private lateinit var totalPriceTextView: TextView
    private lateinit var chambres: Chambres
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var calendar: DatePicker
    private lateinit var startDate: Date // Added to store the selected start date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookingformaccomodation)
        nbDayEditText = findViewById(R.id.daysEditText)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        val bookNowButton: Button = findViewById(R.id.bookRButton)

        chambres = intent.getParcelableExtra("chambres") ?: Chambres("", "", 100, "", true, 5, 3)

        startDate = Calendar.getInstance().time

        // Create a MaterialDatePicker for date selection
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        // Set a listener for when a date is selected
        datePicker.addOnPositiveButtonClickListener {
            startDate = Date(it) // Update the startDate with the selected date
        }
        // Add TextWatcher to calculate total price dynamically
        nbDayEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Calculate total price when the text changes
                calculateAndDisplayTotalPrice(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this implementation
            }
        })
        findViewById<DatePicker>(R.id.dateRPicker).setOnClickListener {
            datePicker.show(supportFragmentManager, "DateRPicker")
        }
        bookNowButton.setOnClickListener {
            Log.d("button", "clicked")
            handleBooking()
        }
        // Fetch room availability and set available dates in the date picker
    }
    private fun calculateAndDisplayTotalPrice(hoursText: String) {
        if (hoursText.isNotBlank()) {
            val numberOfHours = hoursText.toInt()
            val pricePerHour = chambres.price // Assuming pricePerHour is a property in the room model
            val totalPrice = numberOfHours * pricePerHour
            totalPriceTextView.text = "Total Price: $totalPrice"
            val reservationRequest = ReservationRRequest(
                userId = "655aa08d78adce5f7b9a9159", // Replace with the actual user ID
                startDate = startDate,
                nbdays = numberOfHours, // Assuming nbDays is equivalent to the number of hours in your case
                totalPrice = totalPrice
            )
        } else {
            // Handle the case where the nbDayEditText is empty
            totalPriceTextView.text = ""
        }
    }
    private fun handleBooking() {
        Log.d("button", "clicked")
        val selectedDate: Date = Calendar.getInstance().time?: return
        val numberOfHours: Int = nbDayEditText.text.toString().toIntOrNull() ?: return

        // Assuming you have the user ID and location, you need to replace these with actual values

        val reservationRequest =  ReservationRRequest(
            userId = "655aa08d78adce5f7b9a9159", // Replace with the actual user ID
            startDate = selectedDate,
            nbdays = numberOfHours, // Assuming nbDays is equivalent to the number of hours in your case
            totalPrice = 200
        )

        // Assuming you have a valid guide ID from the previous activity
        val chambreId = chambres._id

        // Make the API call to add a reservation
        val roomApi = RoomApi.create()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = roomApi.addRoomReservation(chambreId, reservationRequest)
                if (response.isSuccessful) {
                    // Reservation added successfully
                    val reservationResponse = response.body()
                    Log.d("button", "works")
                    Toast.makeText(
                        this@BookingFormRActivity,
                        "Reservation added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle any further actions upon successful reservation
                } else {
                    // Handle API error
                    Log.d("button", "doesn't work")
                    Toast.makeText(
                        this@BookingFormRActivity,
                        "Failed to add reservation. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Handle exception
                Toast.makeText(
                    this@BookingFormRActivity,
                    "An error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("button", "Problem")
                Log.e("BookingFormRActivity", "Error: ${e.message}", e)
            }
        }}

}