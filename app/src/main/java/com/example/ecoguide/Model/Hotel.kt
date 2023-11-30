package com.example.ecoguide.Model
import Chambres
import android.os.Parcel
import android.os.Parcelable
data class Hotel(
    val _id: String,
    val hotelname: String,
    val location: String,
    val image: String,
    val description: String,
    val Favoris: Comparable<*>,
    val rating: String,
    val nbStars: Comparable<*>,
    val nbChambre: Comparable<*>,
    val price: String,
    val chambres: List<Chambres>):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() ?: "",
        parcel.readInt() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Chambres.CREATOR) ?: emptyList(),
        ) override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hotelname)
        parcel.writeString(location)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(rating)
        parcel.writeString(price)
        parcel.writeInt(Favoris as Int)
        parcel.writeInt(nbStars as Int)
        parcel.writeInt(nbChambre as Int)
        parcel.writeTypedList(chambres)
        //parcel.writeString(price)

    } companion object CREATOR : Parcelable.Creator<Hotel> {
        override fun createFromParcel(parcel: Parcel): Hotel {
            return Hotel(parcel)
        }
        override fun newArray(size: Int): Array<Hotel?> {
            return arrayOfNulls(size)
        }


    }
}
data class HotelApiResponse(
    val statusCode: Int,
    val message: String,
    val hotels: List<Hotel>
)