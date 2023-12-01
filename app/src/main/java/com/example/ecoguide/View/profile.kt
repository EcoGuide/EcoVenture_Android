package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.ResetPassword
import com.example.ecoguide.Model.UserDetailsResponse
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)
        //----- Get Token from Share Preferences to include it to header Authorization---------
        val sharedPref = getSharedPreferences(
            "com.example.myapp.PREFERENCE_FILE_KEY",
            Context.MODE_PRIVATE
        )
         val token = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null) ;
        Log.d(" Authenticate Token", "${token.toString()}")
        try {
            if (token != null) {
                CoroutineScope(Dispatchers.IO).launch {

                apiInterface.UserDetails("Bearer $token").enqueue(object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        if (response.isSuccessful) {
                            val userDetails = response.body()
                            userDetails?.let { user ->

                                runOnUiThread {
                                     binding.Username.text = user.name
                                    binding.textViewEmail.text = user.email
                                    binding.textViewPhoneNumber.text = user.telephone
                                }
                            } ?: run {
                                Snackbar.make(
                                    binding.root,
                                    "Réponse de connexion invalide",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            // Gérez les autres cas de réponse non réussie
                            Snackbar.make(
                                binding.root,
                                "Échec de la connexion : ${response.errorBody()?.string()}",
                                Snackbar.LENGTH_LONG
                            ).show()

                            Log.d("LoginFail", "La réponse n'a pas été réussie")
                        }
                    }

                    override fun onFailure(
                        call: Call<UserDetailsResponse>,
                        t: Throwable
                    ) {
                        // Gestion des erreurs de réseau ou des exceptions inattendues
                        Snackbar.make(
                            binding.root,
                            "Erreur de connexion : ${t.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                        Log.d("LoginError", "Échec : ${t.message}")
                    }


                })
            }}
            }catch (e: Exception){
            Snackbar.make(
                binding.root,
                "An error was occured while fetching User Details !! }",
                Snackbar.LENGTH_LONG
            ).show()            }


     }

}