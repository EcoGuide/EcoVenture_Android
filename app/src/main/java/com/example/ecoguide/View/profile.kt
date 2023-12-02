package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.ecoguide.Model.EditUser
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.LogoutResponse
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

        val sharedPref = getSharedPreferences("com.example.myapp.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null) ;
        Log.d(" Authenticate Token", "${token.toString()}")
//-------------------------------------------------------------------------------------------------------------------
        val editButton = findViewById<TextView>(R.id.profileedit)
        /*   editButton.setOnClickListener {
               val intent = Intent(this, Edit_Profile::class.java)
               startActivity(intent)
               finish()
           }
            */

        //----- Get Token from Share Preferences to include it to header Authorization---------


       //----------------------------------------Fetch User Details -----------------------------------
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
            ).show()
            }

        editButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null)
            val userNameEditText = dialogView.findViewById<EditText>(R.id.editTextUserName)
            val userEmailEditText = dialogView.findViewById<EditText>(R.id.editTextUserEmail)
            val userPhoneEditText = dialogView.findViewById<EditText>(R.id.editTextUserPhone)
            val userPWDEditText = dialogView.findViewById<EditText>(R.id.editTextPassword)

            AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Edit you Profile")
                .setPositiveButton("Save changes") { dialog, which ->
                    val userName = userNameEditText.text.toString()
                    val userEmail = userEmailEditText.text.toString()
                    val userPhone = userPhoneEditText.text.toString()
                    val userPWD = userPWDEditText.text.toString()

                    val sharedPref = getSharedPreferences(
                        "com.example.myapp.PREFERENCE_FILE_KEY",
                        Context.MODE_PRIVATE
                    )
                    val useredit = EditUser(userName, userEmail, userPWD)
                    val Edittoken = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null);
                    Log.d(" Edit_token  ", "${Edittoken.toString()}")


                    if (Edittoken != null) {
                        apiInterface.EditProfile("Bearer $Edittoken", useredit)
                            .enqueue(object : Callback<LoginResponse> {
                                override fun onResponse(
                                    call: Call<LoginResponse>,
                                    response: Response<LoginResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        //  CoroutineScope(Dispatchers.Main).launch {
                                        Snackbar.make(
                                            binding.root,
                                            "Your Profile has been succefully Edited : ${
                                                response.errorBody()?.string()
                                            }",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                        //   }
                                    } else {
                                        //  CoroutineScope(Dispatchers.Main).launch {
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
                                    //}
                                }

                                override fun onFailure(
                                    call: Call<LoginResponse>,
                                    t: Throwable
                                ) {
                                    //  CoroutineScope(Dispatchers.Main).launch {
                                    Snackbar.make(
                                        binding.root,
                                        "Erreur de connexion : ${t.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    Log.d("LoginError", "Échec : ${t.message}")
                                    //  }
                                }


                            })
                    }


                }


                .setNegativeButton("Annuler", null)
                .show()
        }
        //_____________________________________LOGOUT___________________________________________________
        binding.Logout.setOnClickListener {

            try {
                if (token != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        apiInterface.logout("Bearer $token").enqueue(object : Callback<LogoutResponse> {
                            override fun onResponse(
                                call: Call<LogoutResponse>,
                                response: Response<LogoutResponse>
                            ) {
                                if (response.isSuccessful) {

                                    val loginResponse = response.body()
                                    Snackbar.make(
                                        binding.root, " You are Logged out${response.errorBody()?.string()}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    val editor = sharedPref.edit()
                                    editor.remove("TOKEN_KEY_AUTHENTICATE")
                                    editor.apply()
                                    val intent = Intent(this@profile, login::class.java)
                                    startActivity(intent)
                                    finish()// ou editor.commit() si vous avez besoin d'une réponse synchrone
                                } else {
                                     Snackbar.make(
                                        binding.root,
                                        "Échec de la connexion : ${response.errorBody()?.string()}",
                                        Snackbar.LENGTH_LONG
                                    ).show()

                                    Log.d("LoginFail", "La réponse n'a pas été réussie")
                                }
                            }

                            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {

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

        }
     } // Oncreate end -------


}
}