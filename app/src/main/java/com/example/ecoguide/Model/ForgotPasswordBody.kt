package com.example.ecoguide.Model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordBody(
    @SerializedName("email") val email: String
)