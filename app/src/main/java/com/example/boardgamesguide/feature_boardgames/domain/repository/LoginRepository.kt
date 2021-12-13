package com.example.boardgamesguide.feature_boardgames.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot

interface LoginRepository {

    fun signUpUser(email: String, password: String, fullName: String): Task<AuthResult>

    fun signInUser(email: String, password: String): Task<AuthResult>


    fun saveUser(email: String, name: String): Task<Void>

    fun fetchUser(): Task<QuerySnapshot>

    fun sendForgotPassword(email: String): Task<Void>
}