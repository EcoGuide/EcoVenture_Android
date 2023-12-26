package com.example.ecoguide.View

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.ecoguide.Model.EditUser
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.LogoutResponse
import com.example.ecoguide.Model.UserDetailsResponse
import com.example.ecoguide.Service.RetrofitUser.ApiService
import com.example.ecoguide.Service.RetrofitUser.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.biometric.BiometricPrompt
import com.bumptech.glide.Glide
//import com.example.ecoguide.Service.RetrofitUser.RetrofitClient.BASE_URL
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class profile : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding
    val PICK_IMAGE_REQUEST = 1
    val apiInterface = RetrofitClient.buildService(ApiService::class.java)
    private var token_de_passage: String? = null
    private var imageUri: Uri? = null




    fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                uploadImage(selectedImageUri)
            }
        }
    }*/

    fun uploadImage(imageUri: Uri) {
        try {
            val body = prepareImagePart(imageUri)

            token_de_passage?.let { authToken ->
                val call = apiInterface.EditProfilePhoto("Bearer $authToken", body)
                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            Snackbar.make(
                                binding.root,
                                "Image Updated : ${response.errorBody()?.string()}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Error while updating image: ${response.errorBody()?.string()}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Snackbar.make(
                            binding.root,
                            "Error: ${t.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                })
            } ?: run {
                Snackbar.make(
                    binding.root,
                    "Authentication token not found",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Snackbar.make(
                binding.root,
                "An error occurred: ${e.message}",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)

        val sharedPref =
            getSharedPreferences("com.example.myapp.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN_KEY_AUTHENTICATE", null);
        Log.d(" Authenticate Token", "${token.toString()}")
        val editButton = findViewById<TextView>(R.id.profileedit)
        token_de_passage = token;
        binding.btnEditImage.setOnClickListener{
            openImageChooser()
        }
        // --------------------- Edit Image ---------------------
        /* binding.btnEditImage.setOnClickListener {
         try {
             if (token != null) {
                 CoroutineScope(Dispatchers.IO).launch {

                     apiInterface.EditProfilePhoto("Bearer $token")
                         .enqueue(object : Callback<LoginResponse> {
                             override fun onResponse(
                                 call: Call<LoginResponse>,
                                 response: Response<LoginResponse>
                             ) {
                                 if (response.isSuccessful) {

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
                                 call: Call<LoginResponse>,
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
         }  catch (e: Exception) {
             Snackbar.make(
                 binding.root,
                 "An error was occured while updating your image  !! }",
                 Snackbar.LENGTH_LONG
             ).show()

         }
        }
*/
        // <!-------------------- Fetch User Details  Logique ----------------!>
        try {
            if (token != null) {
                CoroutineScope(Dispatchers.IO).launch {

                    apiInterface.UserDetails("Bearer $token")
                        .enqueue(object : Callback<UserDetailsResponse> {
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
                                                val BASE_URL = "http://192.168.1.126:3000/"

                                          //  val imageUrl = "http://localhost:3000/Public/image/406915526_902598478048328_8587398140043988899_n.jpg1702479305823.jpg"
                                            val imageUrl =  user.image
                                             val cleanedImageUrl = imageUrl.replace("http://localhost:3000/",BASE_URL );
                                            Log.d("cleanedImageUrl", cleanedImageUrl)
                                             Glide.with(this@profile)
                                                .load(cleanedImageUrl)
                                                .into(binding.imageViewProfilePicture)
                                            Log.d("imageUrl", cleanedImageUrl)
                                             /* userDetails.image?.let {
                                                     Glide.with(this@profile)
                                                        .load(it)
                                                        .into(binding.imageViewProfilePicture)
                                                }
                                            */






                                            val drawable = binding.imageViewProfilePicture
                                            if (drawable == null) {
                                                Log.d("CheckImageView", "ImageView is empty.")
                                            } else {
                                                Log.d("succes", "ImageView has an image.")
                                            }
                                            /*  Glide.with(this@profile)
                                                                                          .load(imageUrl)
                                                                                          .into(binding.imageViewProfilePicture)
                                                                                      Log.d("imageUrl", imageUrl)
                                          */
                                            /*  userDetails.image?.let {
                                                  Glide.with(this@profile)
                                                      .load(it) // Utilisez l'URL de l'image
                                                      .into(binding.imageViewProfilePicture)
                                              }*/
                                                ?: Log.d("ProfileImage", "Image URL is null")


                                        /*
                                        withContext(Dispatchers.Main) {
        // Affichez l'image en utilisant Glide ou une autre bibliothèque
        Glide.with(this@YourActivity)
            .load(userDetails.image) // Utilisez l'URL de l'image
            .into(yourImageView) // Votre ImageView où afficher l'image
    }
                                         */
                                         //   Log.d("onResponseImage: ",user.image.replace("http://localhost:3000/",BASE_URL))
                                           // Glide.with(baseContext).load(user.image.replace("http://localhost:3000/",BASE_URL)).into(binding.imageViewProfilePicture)
                                        }
                                    } ?: run {
                                        Snackbar.make(
                                            binding.root,
                                            "Réponse de connexion invalide",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
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
                }
            }
        } catch (e: Exception) {
            Snackbar.make(
                binding.root,
                "An error was occured while fetching User Details !! }",
                Snackbar.LENGTH_LONG
            ).show()
        }

        //<!--------------- Edit Profile With Biometric Touch ID Logique  -------------!>
        editButton.setOnClickListener {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verification required")
                .setSubtitle("Please verify your identity")
                .setNegativeButtonText("Cancel")
                .build()

            val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        runOnUiThread {
                            showEditProfileDialog()
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        // Gérer l'échec d'authentification
                        // ...
                    }
                })

            // Afficher le prompt biométrique
            biometricPrompt.authenticate(promptInfo)
        }


        // <!-------------------Logout Logique --------------------!>
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


    //<!-------------------Private Function--------------------!>
    private fun showEditProfileDialog() {
        val apiInterface = RetrofitClient.buildService(ApiService::class.java)

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
    fun getRealPathFromURI(contentUri: Uri): String {
        var path: String = ""
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            path = contentUri.path!!
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(index)
            cursor.close()
        }
        return path
    }
    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type="image/*"
            val mimetypes = arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,mimetypes)
            startActivityForResult(it,REQUEST_CODE_IMAGE)
        }
    }
    companion object {
        const val REQUEST_CODE_IMAGE = 101
    }


    fun prepareImagePart(imageUri: Uri): MultipartBody.Part {
        val inputStream = contentResolver.openInputStream(imageUri)
        val requestFile = inputStream?.readBytes()?.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", "image.jpg", requestFile!!)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data

            binding.imageViewProfilePicture.setImageURI(selectedImageUri)
            imageUri = selectedImageUri
        }
    }
}