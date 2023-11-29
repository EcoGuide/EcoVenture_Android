package com.example.ecoguide.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.ResetPassword
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityResetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Callback
import retrofit2.Response

class reset_password : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)


        binding.resetPasswordButton.setOnClickListener {
            val newPwd= binding.newPasswordEditText.text.toString().trim();
            val ConfirmnewPwd= binding.confirmPasswordEditText.text.toString().trim();

            //     Log.d("LoginActivity", "Email: $email, Password: $password")
            // val request = loginRequest(email, password)

            if (newPwd.isEmpty() && ConfirmnewPwd.isEmpty()) {
                Snackbar.make(binding.root, "All inputs ares required", Snackbar.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, " Resetting your password.", Toast.LENGTH_SHORT).show()

                val sharedPref = getSharedPreferences(
                    "com.example.myapp.PREFERENCE_FILE_KEY",
                    Context.MODE_PRIVATE
                )
                //-----------------Get Code from forget_password_activity-----------------
                val savedCodeInput = sharedPref.getString("CodeVerification", null) // Utilisez null ou une valeur par défaut si vous le souhaitez
                Log.d("Code veriffication", "${savedCodeInput.toString()}")

                val resetPasswordBody = ResetPassword( savedCodeInput.toString(), newPwd)
                //----- Get Token from Share Preferences to include it to header Authorization---------
                val header = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null) ;
                Log.d("Code veriffication", "${header.toString()}")

                apiInterface.resetPassword(  "Bearer ${header.toString() }",resetPasswordBody).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                             Snackbar.make(binding.root, "Your Password has been succefully reseted: ${response.errorBody()?.string()}", Snackbar.LENGTH_LONG).show()
                        } else {
                             Snackbar.make(binding.root, "An error was occured while resetting your  : ${response.errorBody()?.string()}", Snackbar.LENGTH_LONG).show()
                         }

                    }
                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                         Snackbar.make(binding.root, "Server shut down: ${t.message}", Snackbar.LENGTH_LONG).show()
                     }

                })
            }
        }

    }
}

/*

 */