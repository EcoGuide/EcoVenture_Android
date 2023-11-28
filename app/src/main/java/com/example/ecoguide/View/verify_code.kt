package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class verify_code : AppCompatActivity() {
    /*
     private lateinit var binding: Activityverify_code

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val codeiput = binding.etCodeInput.text.toString().trim()
        binding = ActivitySendCodeVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.verifycode.setOnClickListener {
            if(!codeiput.isEmpty()) {
                val sharedPref = getSharedPreferences(
                    "com.example.myapp.PREFERENCE_FILE_KEY",
                    Context.MODE_PRIVATE
                )
                // Créez un éditeur et sauvegardez le token
                with(sharedPref.edit()) {
                    putString("CodeVerification", codeiput)
                    apply()

                }
                val intent = Intent(this, reset_password::class.java)
                startActivity(intent)
            }
        }


    }

*/


}