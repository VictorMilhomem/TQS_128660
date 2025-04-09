package com.github.wizerapp.data.repository

import com.github.wizerapp.data.model.Student
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

// Change the createNewGroup for the Group assign

class StudentRepository(private val db: FirebaseFirestore) {

    private val studentsRef = db.collection("students")


    fun createStudent(student: Student, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        studentsRef.document(student.id).set(student)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }


    fun getStudentById(studentId: String, onSuccess: (Student) -> Unit, onFailure: (Exception) -> Unit) {
        studentsRef.document(studentId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val student = document.toObject<Student>()
                    student?.let { onSuccess(it) }
                } else {
                    onFailure(Exception("Student not Found"))
                }
            }
            .addOnFailureListener { onFailure(it) }
    }


    fun updateStudent(studentId: String, updatedData: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        studentsRef.document(studentId).update(updatedData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }


    fun deleteStudent(studentId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        studentsRef.document(studentId).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }


    fun getAllStudents(onSuccess: (List<Student>) -> Unit, onFailure: (Exception) -> Unit) {
        studentsRef.get()
            .addOnSuccessListener { documents ->
                val students = documents.mapNotNull { it.toObject<Student>() }
                onSuccess(students)
            }
            .addOnFailureListener { onFailure(it) }
    }


    fun assignStudentToRandomGroup(
        studentId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val groupsRef = db.collection("groups")

        groupsRef.get().addOnSuccessListener { snapshot ->
            if (!snapshot.isEmpty) {
                // Filtrar grupos com vagas disponíveis
                val availableGroups = snapshot.documents.filter { doc ->
                    val members = doc.get("members") as? List<String> ?: emptyList()
                    val capacity = doc.getLong("capacity") ?: 5
                    members.size < capacity
                }

                if (availableGroups.isNotEmpty()) {
                    val selectedGroup = availableGroups.random()
                    val groupId = selectedGroup.id

                    // Atualizar aluno com o groupId
                    db.collection("students").document(studentId)
                        .update("groupId", groupId)
                        .addOnSuccessListener {
                            // Adicionar aluno ao grupo
                            selectedGroup.reference.update("members", FieldValue.arrayUnion(studentId))
                                .addOnSuccessListener { onSuccess() }
                                .addOnFailureListener { onFailure(it) }
                        }
                        .addOnFailureListener { onFailure(it) }
                } else {
                    // Criar um novo grupo se nenhum tiver espaço
                    createNewGroup(studentId, onSuccess, onFailure)
                }
            } else {
                // Se não houver grupos, criar o primeiro grupo
                createNewGroup(studentId, onSuccess, onFailure)
            }
        }.addOnFailureListener { onFailure(it) }
    }

    private fun createNewGroup(
        studentId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val newGroupRef = db.collection("groups").document()
        val groupData = hashMapOf(
            "name" to "Group ${newGroupRef.id.takeLast(5)}",
            "members" to listOf(studentId),
            "capacity" to 5
        )

        newGroupRef.set(groupData)
            .addOnSuccessListener {
                db.collection("students").document(studentId)
                    .update("groupId", newGroupRef.id)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it) }
            }
            .addOnFailureListener { onFailure(it) }
    }

}
