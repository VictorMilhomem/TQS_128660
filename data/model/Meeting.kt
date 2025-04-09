package com.github.wizerapp.data.model

import com.google.firebase.Timestamp

data class Meeting(
    val id: String = "",
    val groupId: String = "",
    val date: Timestamp = Timestamp.now(),
    val location: String = "",
    val attendees: List<String> = emptyList()
)