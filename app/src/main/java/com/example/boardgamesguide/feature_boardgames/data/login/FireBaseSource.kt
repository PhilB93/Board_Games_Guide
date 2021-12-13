package com.example.boardgamesguide.feature_boardgames.data.login

import com.example.boardgamesguide.feature_boardgames.domain.login.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FireBaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    fun signUpUser(email:String,password:String,fullName:String) = firebaseAuth.createUserWithEmailAndPassword(email,password)


    fun signInUser(email: String,password: String) = firebaseAuth.signInWithEmailAndPassword(email,password)

    fun signInWithGoogle(acct: GoogleSignInAccount) = firebaseAuth.signInWithCredential(
        GoogleAuthProvider.getCredential(acct.idToken,null))

    fun saveUser(email: String,name:String) = firestore
        .collection("users")
        .document(email)
        .set(User(email = email,fullName = name))

    fun fetchUser()=firestore.collection("users").get()

    fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)

}