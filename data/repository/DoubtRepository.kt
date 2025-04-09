package com.github.wizerapp.data.repository

import android.util.Log
import com.github.wizerapp.data.model.Doubt
import com.google.firebase.firestore.FirebaseFirestore

class DoubtRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("doubts")


    fun addDoubt(doubt: Doubt, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val doubtData = hashMapOf(
            "studentId" to doubt.studentId,
            "text" to doubt.text,
            "createdAt" to doubt.createdAt,
            "resolved" to doubt.resolved,
            "resolvedBy" to doubt.resolvedBy,
            "solution" to doubt.solution
        )

        collection.add(doubtData)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Doubt saved with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving doubt", e)
                onFailure(e)
            }
    }


    fun getDoubt(doubtId: String, onSuccess: (Doubt) -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(doubtId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val doubt = document.toObject(Doubt::class.java)
                    doubt?.let { onSuccess(it) }
                } else {
                    onFailure(Exception("Doubt not found"))
                }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }


    fun resolveDoubt(doubtId: String, resolvedBy: String, solution: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val updateData = mapOf(
            "resolved" to true,
            "resolvedBy" to resolvedBy,
            "solution" to solution
        )

        collection.document(doubtId).update(updateData)
            .addOnSuccessListener {
                Log.d("Firestore", "Doubt resolved by $resolvedBy with solution: $solution")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error resolving doubt", e)
                onFailure(e)
            }
    }


    fun deleteDoubt(doubtId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(doubtId).delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Doubt deleted successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error deleting doubt", e)
                onFailure(e)
            }
    }


    fun getDoubtsByStudent(studentId: String, onSuccess: (List<Doubt>) -> Unit, onFailure: (Exception) -> Unit) {
        collection.whereEqualTo("studentId", studentId).get()
            .addOnSuccessListener { result ->
                val doubts = result.documents.mapNotNull { it.toObject(Doubt::class.java) }
                onSuccess(doubts)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }


    fun getUnresolvedDoubts(onSuccess: (List<Doubt>) -> Unit, onFailure: (Exception) -> Unit) {
        collection.whereEqualTo("resolved", false).get()
            .addOnSuccessListener { result ->
                val doubts = result.documents.mapNotNull { it.toObject(Doubt::class.java) }
                onSuccess(doubts)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}