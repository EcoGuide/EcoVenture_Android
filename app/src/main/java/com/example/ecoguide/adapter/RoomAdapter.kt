package com.example.ecoguide.adapter

import Chambres
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class RoomAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {
    private var myRooms: List<Chambres> = ArrayList()

    fun setRooms(rooms: List<Chambres>) {
        this.myRooms = rooms
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room_type, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = myRooms[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int {
        return myRooms.size
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val roomNameTextView: TextView = itemView.findViewById(R.id.text_room_name)
        private val priceTextView: TextView = itemView.findViewById(R.id.text_room_price)
        private val imageView: ImageView = itemView.findViewById(R.id.image_room)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(myRooms[position])
                }
            }
        }

        fun bind(room: Chambres) {
            roomNameTextView.text = room.roomName
            priceTextView.text = room.price.toString()

            val imageUrl = room.image
            Log.d("image_room", "$imageUrl")
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(imageView)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(room: Chambres)
    }
}
