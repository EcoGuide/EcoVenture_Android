package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivitySplashcreenBinding

class splashscreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashcreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashcreen)

        val sharedPref =
            getSharedPreferences("com.example.myapp.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null);
        if(token != null){
            val intent =Intent(this,profile::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent =Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivitySplashcreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.splach.alpha = 0f
        binding.splach.animate().setDuration(1500).alpha(1f).withEndAction {
            val intent = Intent(this@splashscreen, login::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}