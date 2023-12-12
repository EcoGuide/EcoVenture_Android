package com.example.ecoguide.Model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class ReservationR(
    val id: String,
    val chambreId: String,
    val userId: String,
    val startDate: Date,
    val nbdays: Int,
    val totalPrice: Int
)

data class ReservationRRequest(
    val userId: String,
    val startDate: Date,
    val nbdays: Int,
    val totalPrice: Int
)

data class ReservationRResponse(
    val statusCode: Int,
    val message: String,
    val reservation: ReservationR
)