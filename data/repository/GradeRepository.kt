package com.github.wizerapp.data.repository

import android.util.Log
import com.github.wizerapp.data.model.Grade
import com.google.firebase.firestore.FirebaseFirestore

class GradeRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("grades")

    fun addGrade(grade: Grade, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val gradeData = hashMapOf(
            "studentId" to grade.studentId,
            "exerciseId" to grade.exerciseId,
            "score" to grade.score,
            "submittedAt" to grade.submittedAt
        )

        collection.add(gradeData)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Grade salved with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving grade", e)
                onFailure(e)
            }
    }


    fun getGrade(gradeId: String, onSuccess: (Grade) -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(gradeId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val grade = document.toObject(Grade::class.java)
                    grade?.let { onSuccess(it) }
                } else {
                    onFailure(Exception("Grade not found"))
                }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }


    fun updateGrade(gradeId: String, newScore: Int, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(gradeId).update("score", newScore)
            .addOnSuccessListener {
                Log.d("Firestore", "Grade Updated to $newScore")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error updating grade", e)
                onFailure(e)
            }
    }


    fun deleteGrade(gradeId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        collection.document(gradeId).delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Grade deleted")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error deleting grade", e)
                onFailure(e)
            }
    }


    fun getGradesByStudent(studentId: String, onSuccess: (List<Grade>) -> Unit, onFailure: (Exception) -> Unit) {
        collection.whereEqualTo("studentId", studentId).get()
            .addOnSuccessListener { result ->
                val grades = result.documents.mapNotNull { it.toObject(Grade::class.java) }
                onSuccess(grades)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}