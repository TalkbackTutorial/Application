package com.github.talkbacktutorial.activities.challenges.lesson6.model

/**
 * This class is a model that is part of lesson 6 challenge
 * TODO: Change Documentation
 * Author: khaliqdadmohmand
 * Referenced: https://github.com/khaliqdadmohmand/Android_Chat/blob/main/app/src/main/java/com/emericoapp/mychat/model/ChatModel.java
 * */
class ChatModel {
    private var id = 0
    private var message: String? = null
    private var isMe = false

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun isMe(): Boolean {
        return isMe
    }

    fun setMe(me: Boolean) {
        isMe = me
    }
}