package com.github.wizerapp.data.model

import com.google.firebase.Timestamp

data class Grade(
    val id: String = "",
    val studentId: String = "",
    val exerciseId: String = "",
    val score: Int = 0,
    val submittedAt: Timestamp = Timestamp.now()
)