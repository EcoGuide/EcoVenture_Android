package com.example.ecoguide.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecoguide.Model.Hotel
import com.example.ecoguide.View.HotelDetailActivity
import com.example.myapplication.R

class HoteldetailAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<HoteldetailAdapter.HotelViewHolder>() {
    private var myHotels: List<Hotel> = ArrayList()
    private lateinit var context: Context

    fun setHotels(hotels: List<Hotel>) {
        this.myHotels = hotels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
       /* if(viewType == 0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.accomodation_card, parent, false)
            return HotelViewHolder(view)
        }*/
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_hotel, parent, false)
        return HotelViewHolder(view)

    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = myHotels[position]
        holder.bind(hotel)
    }

    override fun getItemCount(): Int {
        return myHotels.size
    }

/*    override fun getItemViewType(position: Int): Int {
        if(myHotels[position] is Hotel){
            return 1
        }
        return 0
    }*/
    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hotelNameTextView: TextView = itemView.findViewById(R.id.fullnameTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewhotel)
        private val nbStarsTextView: TextView = itemView.findViewById(R.id.reviewsTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val Description: TextView = itemView.findViewById(R.id.text_hotel_description)
        private val Favoris: TextView = itemView.findViewById(R.id.text_star_count)
    init {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val hotel = myHotels[position]
                listener.onItemClick(hotel)
                // Start HotelDetailActivity
                val intent = Intent(context, HotelDetailActivity::class.java)
                intent.putExtra("_id", hotel._id)
                context.startActivity(intent)
            }
        }
    }

        fun bind(hotel: Hotel) {
            hotelNameTextView.text = hotel.hotelname
            locationTextView.text = hotel.location
            nbStarsTextView.text = hotel.nbStars.toString()
            priceTextView.text = hotel.price.toString()
            Description.text= hotel.description
            Favoris.text=hotel.Favoris.toString()
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
