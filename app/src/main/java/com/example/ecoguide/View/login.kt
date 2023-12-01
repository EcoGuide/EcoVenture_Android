package com.example.ecoguide.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.loginRequest
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Callback
import retrofit2.Response
import android.text.Editable
import android.widget.TextView
import com.example.myapplication.R

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialisez binding ici avant toute chose
        //val emailTextEdit = binding.emailEditText
        //val passwordTextEdit = binding.passwordEditText


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiInterface = RetrofitClient.buildService(ApiService::class.java)
        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, forget_password::class.java)
            startActivity(intent)
        }
// ----------------------Interface Login done / token stored in shared preference----LOGIN -----------------------------------
        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val emailedit = binding.emailEditText

            val passwordedit = binding.passwordEditText
            // _________ Controle de saisie
            emailedit.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val input = emailedit.text.toString().trim()
                    if (input.isEmpty()) {
                        emailedit.error = "Champ obligatoire"
                    }
                    else if(!isEmailValid(input)){
                        emailedit.error = "Email not Valid"
                    }
                    else {
                        emailedit.error = null
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })
            passwordedit.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val input = passwordedit.text.toString().trim()
                    if (input.isEmpty()) {
                        passwordedit.error = "Champ obligatoire"
                    }
                    else if(!isPasswordValid(input)){
                        passwordedit.error = "Email not Valid"
                    }
                    else {
                        passwordedit.error = null
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })
            Log.d("LoginActivity", "Email: $email, Password: $password")
            val request = loginRequest(email, password)
            Toast.makeText(this, "Signing In...", Toast.LENGTH_SHORT).show()

            if (email.isEmpty() || password.isEmpty()) {

                Snackbar.make(
                    binding.root,
                    "L'email et le mot de passe sont requis",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        apiInterface.userlogin(request).enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: retrofit2.Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if (response.isSuccessful) {

                                    val loginResponse = response.body()

                                    // Vérifiez si loginResponse n'est pas null et que le token n'est pas null
                                    loginResponse?.let {
                                        if (it.Token != null) {
                                            val intent = Intent(this@login, profile::class.java)
                                            startActivity(intent)
                                            finish()
                                            val sharedPref = getSharedPreferences(
                                                "com.example.myapp.PREFERENCE_FILE_KEY",
                                                Context.MODE_PRIVATE
                                            )
                                            with(sharedPref.edit()) {
                                                putString("TOKEN_KEY_AUTHENTICATE", it.Token)
                                                apply()
                                            }

                                            Snackbar.make(
                                                binding.root,
                                                "Connexion réussie : ${it.Token}",
                                                Snackbar.LENGTH_LONG
                                            ).show()

                                            Log.d("LoginSuccess", "Token: ${it.Token}")
                                        } else {
                                            // Gérez le cas où le token est null
                                            Snackbar.make(
                                                binding.root,
                                                "La connexion a réussi, mais le token est absent",
                                                Snackbar.LENGTH_LONG
                                            ).show()
                                        }
                                    } ?: run {
                                        // Gérez le cas où loginResponse est null
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
                                call: retrofit2.Call<LoginResponse>,
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
                    }
                }
            }
        }

// -------------------Controle De Saisie--------------------------------

    }
}
    fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "^(?=.*[A-Z])(?=.*\\d).+\$"
        val regex = Regex(passwordRegex)
        return regex.matches(password)
    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val regex = Regex(emailRegex)
        return regex.matches(email)
    }

