package com.github.wizerapp.data.repository

import android.util.Log
import com.github.wizerapp.data.model.Meeting
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class MeetingRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("meetings")


    fun addMeeting(meeting: Meeting, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val meetingData = hashMapOf(
            "groupId" to meeting.groupId,
            "date" to meeting.date,
            "location" to meeting.location,
            "attendees" to meeting.attendees
        )

        collection.add(meetingData)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Meeting created with ID: ${documentReference.id}")
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error creating meeting", e)
                onFailure(e)
            }
    }


    fun getMeeting(meetingId: String, onSuccess: (Meeting) -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(meetingId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val meeting = document.toObject(Meeting::class.java)
                    meeting?.let { onSuccess(it) }
                } else {
                    onFailure(Exception("Meeting not found"))
                }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }


    fun addAttendee(meetingId: String, studentId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val meetingRef = FirebaseFirestore.getInstance().collection("meetings").document(meetingId)

        meetingRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val attendees = document.get("attendees") as? MutableList<String> ?: mutableListOf()

                if (!attendees.contains(studentId)) {
                    attendees.add(studentId) // ✅ Add student ID

                    val updateData = mapOf("attendees" to attendees.toList()) // ✅ Convert to List<String>

                    meetingRef.update(updateData)
                        .addOnSuccessListener {
                            Log.d("Firestore", "✅ Attendance confirmed for $studentId")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "❌ Firestore update failed: ${e.message}")
                            onFailure(e)
                        }
                } else {
                    Log.w("Firestore", "⚠ Student $studentId already confirmed")
                    onFailure(Exception("Student already confirmed"))
                }
            } else {
                onFailure(Exception("Meeting not found"))
            }
        }.addOnFailureListener { e ->
            Log.e("Firestore", "❌ Error fetching meeting: ${e.message}")
            onFailure(e)
        }
    }



    fun updateMeeting(meetingId: String, newDate: Timestamp, newLocation: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(meetingId).update(
            "date", newDate,
            "location", newLocation
        ).addOnSuccessListener {
            Log.d("Firestore", "Meeting updated: new date - $newDate, new location - $newLocation")
            onSuccess()
        }.addOnFailureListener { e ->
            Log.e("Firestore", "Error updating meeting", e)
            onFailure(e)
        }
    }


    fun deleteMeeting(meetingId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(meetingId).delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Meeting deleted successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error deleting meeting", e)
                onFailure(e)
            }
    }


    fun getMeetingsByGroup(groupId: String, onSuccess: (List<Meeting>) -> Unit, onFailure: (Exception) -> Unit) {
        collection.whereEqualTo("groupId", groupId).get()
            .addOnSuccessListener { result ->
                val meetings = result.documents.mapNotNull { it.toObject(Meeting::class.java) }
                onSuccess(meetings)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }


    fun getUpcomingMeetings(onSuccess: (List<Meeting>) -> Unit, onFailure: (Exception) -> Unit) {
        collection.whereGreaterThan("date", Timestamp.now()).get()
            .addOnSuccessListener { result ->
                val meetings = result.documents.mapNotNull { it.toObject(Meeting::class.java) }
                onSuccess(meetings)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}