package com.example.ecoguide.Model
import android.os.Parcel
import android.os.Parcelable
data class Hotel(
    val _id: String,
    val hotelname: String,
    val location: String,
    val image: String,
    val description: String,
    val Favoris: Int,
    val rating: String,
    val nbStars: Int,
    val nbChambre: Int,
    val price: Int,
    val chambres: List<Chambres>)/* {
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
        parcel.readInt() ?: 0,
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
        parcel.writeInt(price)
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
}*/
data class HotelApiResponse(
    val statusCode: Int,
    val message: String,
    val hotels: List<Hotel>
)