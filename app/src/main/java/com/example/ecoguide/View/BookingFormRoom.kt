package com.example.ecoguide.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoguide.Model.Chambres
import com.example.myapplication.R
import com.google.android.material.datepicker.MaterialDatePicker


class BookingFormActivity : AppCompatActivity() {

    private lateinit var hoursEditText: EditText
    private lateinit var totalPriceTextView: TextView
    private lateinit var chambres: Chambres
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var calendar: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookingformaccomodation)
        hoursEditText = findViewById(R.id.daysEditText)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)

        chambres = intent.getParcelableExtra("chambres") ?: Chambres("", "", 100, "", true, 5, 3)

        // Add TextWatcher to calculate total price dynamically
        hoursEditText.addTextChangedListener(object : TextWatcher {
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

        // Fetch room availability and set available dates in the date picker
    }
    private fun calculateAndDisplayTotalPrice(hoursText: String) {
        if (hoursText.isNotBlank()) {
            val numberOfHours = hoursText.toInt()
            val pricePerHour = 15 // Assuming pricePerHour is a property in the room model
            val totalPrice = numberOfHours * pricePerHour
            totalPriceTextView.text = "Total Price: $totalPrice" // Update the UI with the total price
        } else {
            // Handle the case where the hoursEditText is empty
            totalPriceTextView.text = ""
        }
    }

}