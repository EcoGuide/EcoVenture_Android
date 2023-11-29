package com.example.ecoguide.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ecoguide.Model.ForgotPasswordBody
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityForgetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Callback
import retrofit2.Response

class forget_password : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
// --------------------------------- Variables -----------------------------------
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val smsInput = findViewById<EditText>(R.id.smsInput)
        val recoveryMethod = findViewById<RadioGroup>(R.id.recoveryMethod)


// --------------------------------- Switch  Between MAIL  AND SMS ------------------------------
        recoveryMethod.setOnCheckedChangeListener { group, checkedId ->
            // Masquez les deux champs de saisie par défaut
            emailInput.visibility = View.GONE
            smsInput.visibility = View.GONE

            // Affichez le champ de saisie approprié en fonction de l'option sélectionnée
            when (checkedId) {
                R.id.emailOption -> emailInput.visibility = View.VISIBLE
                R.id.smsOption -> smsInput.visibility = View.VISIBLE
            }
        }


// _______________________ Send code with MAIL __________________________________

        binding.sendCodeButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val telephone = binding.smsInput.text.toString().trim()
            //     Log.d("LoginActivity", "Email: $email, Password: $password")
            // val request = loginRequest(email, password)

            if (email.isEmpty() ){
                Snackbar.make(binding.root, "Mail input is required", Snackbar.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Sending Mail...", Toast.LENGTH_SHORT).show()


                val forgotPasswordBody = ForgotPasswordBody(email = email)
                     apiInterface.forgetPassword(forgotPasswordBody).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: retrofit2.Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                Snackbar.make(binding.root, "Mail has been succeffully,Check your inbox : ${response.errorBody()?.string()}", Snackbar.LENGTH_LONG).show()
                                 val intent = Intent(this@forget_password, Code_Verification::class.java)
                                startActivity(intent)

                            } else {
                                 Snackbar.make(binding.root, "An error was occured while sending mail : ${response.errorBody()?.string()}", Snackbar.LENGTH_LONG).show()
                                //Log.d("LoginFail", "La réponse n'a pas été réussie")
                            }

                        }

                        override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                             Snackbar.make(binding.root, "Coonection Error: ${t.message}", Snackbar.LENGTH_LONG).show()
                            //Log.d("LoginError", "Échec : ${t.message}")
                        }

                    })

                }
            }
            }
        }






