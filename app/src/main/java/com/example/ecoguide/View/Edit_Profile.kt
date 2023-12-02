package com.example.ecoguide.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ecoguide.Model.EditUser
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEditProfileBinding
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class Edit_Profile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)


        val EditButon = binding.btnSaveChanges.setOnClickListener {
            val userName = binding.editTextUserName.text.toString()
            val userEmail =  binding.editTextUserEmail.text.toString()
            val userPhone =  binding.editTextUserPhone.text.toString()
            val userPWD =  binding.editTextPassword.text.toString()

            val sharedPref = getSharedPreferences(
                "com.example.myapp.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE
            )
            val useredit = EditUser(userEmail, userPWD, userName)
            val Edittoken = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null);
            Log.d(" Edit_token  ", "${Edittoken.toString()}")


            if (Edittoken != null) {
                //    apiInterface.userlogin(request).enqueue(object : Callback<LoginResponse> {
                apiInterface.EditProfile("Bearer $Edittoken", useredit)
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: retrofit2.Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                 Snackbar.make(
                                    binding.root,
                                    "Your Profile has been succefully Edited : ${
                                        response.errorBody()?.string()
                                    }",
                                    Snackbar.LENGTH_LONG
                                ).show()

                            } else {
                                 Log.d(" Edit_token  ", "${Edittoken.toString()}")

                                Snackbar.make(
                                    binding.root,
                                    "An error was occured while  editting your profile  : ${
                                        response.errorBody()?.string()
                                    }",
                                    Snackbar.LENGTH_LONG
                                ).show()

                                Log.d(
                                    "LoginFail",
                                    "La réponse n'a pas été réussie"
                                )

                         }
                        }

                        override fun onFailure(
                            call: retrofit2.Call<LoginResponse>,
                            t: Throwable
                        ) {
                             Snackbar.make(
                                binding.root,
                                "Erreur de connexion : ${t.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.d("LoginError", "Échec : ${t.message}")
                               }



                    })
            }
    }
}
}