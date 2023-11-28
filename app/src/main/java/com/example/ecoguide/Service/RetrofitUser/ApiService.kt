package com.example.ecoguide.Service.RetrofitUser

import com.example.ecoguide.Model.ForgotPasswordBody
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.User
import com.example.ecoguide.Model.loginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("SignIn")
    fun userlogin(@Body request: loginRequest): Call<LoginResponse>
    @POST("signupU")
    fun signup_User(@Body user: User): Call<LoginResponse>
    @POST("forgot-password")
    //   fun forget_password(@Body telephone: String): Call<LoginResponse>
    fun forgetPassword(@Body body: ForgotPasswordBody): Call<LoginResponse>

    // @Multipart
    /* @POST("signupU")
     fun signup_User(
        @Part("email") name: String,
        @Part("password") email: String,
        @Part("name") password: String,
        @Part("telephone") telephone: String
         // @Part Image: MultipartBody.Part
     ):Call<User>*/
}