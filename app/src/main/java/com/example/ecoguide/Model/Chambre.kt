import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Chambres(
    val _id: String,
    val roomName: String,
    val price: Int, // Use an appropriate numeric type here, like Int or Double
    val image: String,
    val isBooked: Boolean,
    val nbChambreType: Int, // Use an appropriate numeric type here, like Int
    val nbPlace: Int // Use an appropriate numeric type here, like Int
    //val reservations
)/* : Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(), // Read the int and convert to boolean
        parcel.readInt(),
        parcel.readInt(),
        // parcel.readInt() ?: "",
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(roomName)
        parcel.writeInt(price)
        parcel.writeString(image)
        parcel.writeInt(if (isBooked) 1 else 0) // Convert boolean to int
        parcel.writeInt(nbChambreType)
        parcel.writeInt(nbPlace)
        // parcel.writeInt(price as Int)
        //parcel.writeString(price)
    }

    companion object CREATOR : Parcelable.Creator<Chambres> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Chambres {
            return Chambres(parcel)
        }

        override fun newArray(size: Int): Array<Chambres?> {
            return arrayOfNulls(size)
        }
    }
}*/

data class ChambresApiResponse(
    val statusCode: Int,
    val message: String,
    val chambres: List<Chambres>
)
