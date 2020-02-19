package com.moose.githublite.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moose.githublite.R
import com.moose.githublite.model.GithubEvents

class EventListAdapter(val events: List<GithubEvents>) : RecyclerView.Adapter<EventListViewHolder>() {
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

    fun bind(event: GithubEvents) {
        if (event.type == "ForkEvent"){
            eventMsg.text = "${event.actor.login} forked ${event.repo.name.split("/")[1]} from ${event.repo.name.split("/")[0]} on ${event.created_at.split("T")[0]}"
            Glide.with(this.itemView)
                .load(event.actor.avatar_url)
                .into(userImg)
        }
        else if(event.type == "CreateEvent"){
            eventMsg.text = "${event.actor.login} created ${event.repo.name.split("/")[1]} on ${event.created_at.split("T")[0]}"
            Glide.with(this.itemView)
                .load(event.actor.avatar_url)
                .into(userImg)
        }
        else if (event.type == "WatchEvent"){
            eventMsg.text = "${event.actor.login} starred ${event.repo.name} on ${event.created_at.split("T")[0]}"
            Glide.with(this.itemView)
                .load(event.actor.avatar_url)
                .into(userImg)
        }
        else if (event.type == "PublicEvent"){
            eventMsg.text = "${event.actor.login} made ${event.repo.name} public ${event.created_at.split("T")[0]}"
            Glide.with(this.itemView)
                .load(event.actor.avatar_url)
                .into(userImg)
        }
    }
}
