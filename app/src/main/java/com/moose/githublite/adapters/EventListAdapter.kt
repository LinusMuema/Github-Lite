package com.moose.githublite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moose.githublite.R
import com.moose.githublite.model.Event

class EventListAdapter(private val events: ArrayList<Event>) : RecyclerView.Adapter<EventListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false)
        return EventListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        return holder.bind(events[position])
    }
}

class EventListViewHolder(view: View) :RecyclerView.ViewHolder(view) {

    private val userImg:ImageView = view.findViewById(R.id.user_img)
    private val eventMsg:TextView = view.findViewById(R.id.event_msg)

    fun bind(event: Event) {
        eventMsg.text = event.message
        Glide.with(itemView.context).load(event.image).into(userImg)
    }
}
