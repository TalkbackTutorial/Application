package com.github.talkbacktutorial.activities.challenges.lesson6.adapter

import android.content.Context
import com.github.talkbacktutorial.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.talkbacktutorial.activities.challenges.lesson6.model.ChatModel


class ChatAdapter(chatModels: ArrayList<ChatModel>, context: Context?) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private var chatModels: ArrayList<ChatModel>? = null
    private var context: Context? = null

    fun ChatAdapter(chatModels: ArrayList<ChatModel>?, context: Context?) {
        this.chatModels = chatModels
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.my_text_message, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = chatModels!![position].getMessage()
    }

    override fun getItemCount(): Int {
        return chatModels!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView

        init {
            message = itemView.findViewById(R.id.message_body)
        }
    }

}