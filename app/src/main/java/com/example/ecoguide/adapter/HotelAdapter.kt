package com.example.ecoguide.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecoguide.Model.Hotel
import com.example.myapplication.R

class HotelAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {
    private var myHotels: List<Hotel> = ArrayList()

    fun setHotels(hotels: List<Hotel>) {
        this.myHotels = hotels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.accomodation_card, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = myHotels[position]
        holder.bind(hotel)
    }

    override fun getItemCount(): Int {
        return myHotels.size
    }

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hotelNameTextView: TextView = itemView.findViewById(R.id.fullnameTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewhotel)
        private val nbStarsTextView: TextView = itemView.findViewById(R.id.reviewsTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(myHotels[position])
                }
            }
        }

        fun bind(hotel: Hotel) {
            hotelNameTextView.text = hotel.hotelname
            locationTextView.text = hotel.location
            nbStarsTextView.text = hotel.nbStars.toString()
            priceTextView.text = hotel.price

            val imageUrl = hotel.image
            Log.d("image", "$imageUrl")
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(imageView)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(hotel: Hotel)
    }
}
