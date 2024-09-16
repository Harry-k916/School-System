package com.example.mobileapp.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.mobileapp.models.User
import com.example.mobileapp.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel(var navController: NavController,var context: Context){
        var mAuth: FirebaseAuth

        init {
            mAuth= FirebaseAuth.getInstance()
        }

        fun signup(firstname: String,lastname: String,email: String,password: String){
                if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank()){
                    Toast.makeText(context,"Please fill all the fields above",Toast.LENGTH_LONG).show()
                    return
                }else{
                    mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener{
                            if (it.isSuccessful){
                                val userdata = User(firstname,lastname,email,password,mAuth.currentUser!!.uid)
                                val regRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Users/"+mAuth.currentUser!!.uid)
                                regRef.setValue(userdata).addOnCompleteListener {
                                    if (it.isSuccessful){
                                        Toast.makeText(context,"User Successfully Registered",Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                                    }
                                }
                            }else{
                                navController.navigate(ROUTE_LOGIN)
                            }
                        }
                }
        }
    fun login(email: String,password: String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"User successfully logged In",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
            }
        }
    }



}