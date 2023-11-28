package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCodeVerificationBinding

class Code_Verification : AppCompatActivity() {
    private lateinit var binding: ActivityCodeVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_code_verification)
        binding = ActivityCodeVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifycode.setOnClickListener {
            val codeiput = binding.etCodeInput.text.toString().trim()

            if(!codeiput.isEmpty()) {
                val sharedPref = getSharedPreferences(
                    "com.example.myapp.PREFERENCE_FILE_KEY",
                    Context.MODE_PRIVATE
                )
                 with(sharedPref.edit()) {
                    putString("CodeVerification", codeiput)
                    apply()

                }
                val intent = Intent(this, reset_password::class.java)
                startActivity(intent)
            }
        }


    }




}