package com.example.instagramclone.managers

import android.util.Log
import com.example.instagramclone.managers.handler.AuthHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthManager {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn(): Boolean{
        return currentUser() != null
    }

    fun currentUser(): FirebaseUser?{
        return firebaseAuth.currentUser
    }

    fun signIn(email: String, password: String, handler: AuthHandler){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val uid = currentUser()!!.uid
                handler.onSuccess(uid)
            }else{
                handler.onError(task.exception)
            }
        }
    }

    fun signUp(email: String, password: String, handler: AuthHandler){
        Log.d("@@@@", "signUp: $email")
        Log.d("@@@@", "signUp: $password")
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val uid = currentUser()!!.uid
                handler.onSuccess(uid)
            }else{
                handler.onError(task.exception)
            }
        }
    }

    fun signOut(){
        firebaseAuth.signOut()
    }

}