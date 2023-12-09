package com.example.ecoguide.Model


import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Chambres(
    val _id: String,
    val roomName: String,
    val price: Int,
    val image: String,
    val isBooked: Boolean,
    val nbChambreType: Int,
    val nbPlace: Int
): Parcelable {
    // ... existing code ...

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(roomName)
        parcel.writeInt(price)
        parcel.writeString(image)
        parcel.writeByte(if (isBooked) 1 else 0)
        parcel.writeInt(nbChambreType)
        parcel.writeInt(nbPlace)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chambres> {
        override fun createFromParcel(parcel: Parcel): Chambres {
            return Chambres(parcel)
        }

        override fun newArray(size: Int): Array<Chambres?> {
            return arrayOfNulls(size)
        }
    }
}

data class ChambresApiResponse(
    val statusCode: Int,
    val message: String,
    val chambres: List<Chambres>
)
