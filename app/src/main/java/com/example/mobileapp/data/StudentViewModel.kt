package com.example.mobileapp.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.mobileapp.models.Student
import com.example.mobileapp.navigation.ROUTE_VIEW_STUDENT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class StudentViewModel(var navController: NavController,var context: Context) {

    var authRepository: AuthViewModel

    init {
        authRepository= AuthViewModel(navController,context)
    }
    fun saveStudent(filePath: Uri,firstname: String,lastname: String,gender: String,desc: String,
                   ){
        var id = System.currentTimeMillis().toString()
        var storageReference = FirebaseStorage.getInstance().getReference()
            .child("Picture/$id")
        storageReference.putFile(filePath).addOnCompleteListener{
            if (it.isSuccessful){
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageUrl = it.toString()
                    var houseData = Student(imageUrl, firstname, lastname, gender, desc, id)
                    var dbRef = FirebaseDatabase.getInstance().getReference()
                        .child("Students/$id")
                    dbRef.setValue(houseData)
                    Toast.makeText(context,"Student saved successfully",Toast.LENGTH_LONG).show()

                }
            }else{
                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun viewStudent(student: MutableState<Student>,students: SnapshotStateList<Student>):
    SnapshotStateList<Student>{
        val ref = FirebaseDatabase.getInstance().getReference().child("Students")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                students.clear()
                for (snap in snapshot.children){
                    val value = snap.getValue(Student::class.java)
                    student.value=value!!
                    students.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_LONG).show()
            }
        })
        return students
    }
    fun updateStudent(
        context: Context,
        navController: NavController,
        filePath: Uri?,
        firstname: String?,
        lastname: String?,
        gender: String?,
        desc: String?,
        id: String,
        currentImageUrl: String
    ) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Students/$id")

        // Retrieve the current student data
        databaseReference.get().addOnSuccessListener { dataSnapshot ->
            val currentStudent = dataSnapshot.getValue(Student::class.java)

            if (currentStudent != null) {
                // Prepare updated student data using non-null input or keep current data
                val updatedStudent = Student(
                    imageUrl = if (filePath != null && filePath != Uri.EMPTY) "" else currentStudent.imageUrl, // placeholder for imageUrl if new image is being uploaded
                    firstname = firstname ?: currentStudent.firstname,
                    lastname = lastname ?: currentStudent.lastname,
                    gender = gender ?: currentStudent.gender,
                    desc = desc ?: currentStudent.desc,
                    id = id
                )

                // If a new image is provided, upload it, then update the student record
                if (filePath != null && filePath != Uri.EMPTY) {
                    val storageReference = FirebaseStorage.getInstance().reference
                    val imageRef = storageReference.child("Picture/${UUID.randomUUID()}.jpg")

                    imageRef.putFile(filePath)
                        .addOnSuccessListener {
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                updatedStudent.imageUrl = uri.toString() // Update the image URL
                                databaseReference.setValue(updatedStudent)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                            navController.navigate(ROUTE_VIEW_STUDENT)
                                        } else {
                                            Toast.makeText(context, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // No new image, just update other fields
                    databaseReference.setValue(updatedStudent)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUTE_VIEW_STUDENT)
                            } else {
                                Toast.makeText(context, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(context, "Student not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to retrieve student data", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteStudent(context: Context, id: String, navController: NavController) {

        val progressDialog = ProgressDialog(context).apply {
            setMessage("Deleting student...")
            setCancelable(false)
            show()
        }


        val delRef = FirebaseDatabase.getInstance().getReference("Students/$id")


        delRef.removeValue().addOnCompleteListener { task ->

            progressDialog.dismiss()

            if (task.isSuccessful) {

                Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_SHORT).show()


                navController.navigate(ROUTE_VIEW_STUDENT)
            } else {

                Toast.makeText(context, task.exception?.message ?: "Deletion failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


}