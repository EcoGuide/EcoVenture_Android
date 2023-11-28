package com.example.ecoguide.Model

import com.google.gson.annotations.SerializedName

data class ResetPassword(
    @SerializedName("code") val code: String,
    @SerializedName("password") val password: String

)
