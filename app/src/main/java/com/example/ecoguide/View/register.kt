package com.example.ecoguide.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.User
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Callback
import retrofit2.Response

class register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_register)
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)

        binding.signUpButton.setOnClickListener {
            val emailBody = binding.emailEditText.text.toString().trim()
            val passwordBody = binding.passwordEditText.text.toString().trim()
            val nameBody = binding.name.text.toString().trim()
            val telephoneBody = binding.phonenumber.text.toString().trim()

            // Log.d("LoginActivity", "Email: $email, Password: $password")
            // val user = User(email, password,name,numberphone)
            Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show()
            val user =User(emailBody,passwordBody,nameBody,telephoneBody)
            if (emailBody.isEmpty() || passwordBody.isEmpty()|| nameBody.isEmpty()
                ||telephoneBody.isEmpty()) {

                Snackbar.make(binding.root, "All inputs are required", Snackbar.LENGTH_SHORT).show()
            } else {
                // apiInterface.userlogin(request).enqueue(object : Callback<LoginResponse> {

                apiInterface.signup_User(user).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            Snackbar.make(binding.root, "You are succefuffly registred : ${response.errorBody()?.string()}", Snackbar.LENGTH_LONG).show()

                        } else {
                            Snackbar.make(binding.root, "Cannot Register! : ${response.errorBody()?.string()}", Snackbar.LENGTH_LONG).show()
                            Log.d("LoginFail", "La réponse n'a pas été réussie")
                        }

                    }

                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                        // Gestion des erreurs de réseau ou des exceptions inattendues
                        Snackbar.make(binding.root, "Erreur de connexion : ${t.message}", Snackbar.LENGTH_LONG).show()
                        Log.d("LoginError", "Échec : ${t.message}")
                    }

                })
            }
        }

    }
}