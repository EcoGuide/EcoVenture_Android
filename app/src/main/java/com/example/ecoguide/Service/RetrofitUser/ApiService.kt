package com.example.ecoguide.Service.RetrofitUser

import com.example.ecoguide.Model.EditUser
import com.example.ecoguide.Model.ForgotPasswordBody
import com.example.ecoguide.Model.LoginResponse
import com.example.ecoguide.Model.LogoutResponse
import com.example.ecoguide.Model.ResetPassword
import com.example.ecoguide.Model.User
import com.example.ecoguide.Model.UserDetailsResponse
import com.example.ecoguide.Model.loginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("SignIn")
    fun userlogin(@Body request: loginRequest): Call<LoginResponse>
    @POST("signupU")
    fun signup_User(@Body user: User): Call<LoginResponse>
    @POST("forgot-password")
      fun forgetPassword(@Body body: ForgotPasswordBody): Call<LoginResponse>
    @POST("reset-password")
    suspend fun resetPassword(@Header("Authorization") header: String,@Body body: ResetPassword): Call<LoginResponse>
    @GET("UserDetails")
    fun UserDetails(@Header("Authorization") token: String): Call<UserDetailsResponse>
    @POST("EditProfile")
    fun EditProfile(@Header("Authorization") token: String, @Body user: EditUser): Call<LoginResponse>


    @GET("logout")
     fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

/*
    @POST("reset-password")
    suspend fun resetPassword(@Header("Authorization") header: String,@Body body: ResetPassword): Call<LoginResponse>
     */
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