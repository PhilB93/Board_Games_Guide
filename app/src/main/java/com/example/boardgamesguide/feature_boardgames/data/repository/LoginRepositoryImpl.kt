package com.example.boardgamesguide.feature_boardgames.data.repository

import com.example.boardgamesguide.feature_boardgames.data.login.FireBaseSource
import com.example.boardgamesguide.feature_boardgames.domain.repository.LoginRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val fireBaseSource: FireBaseSource):LoginRepository {

    override fun signUpUser(email: String, password: String, fullName: String) = fireBaseSource.signUpUser(email, password, fullName)

    override fun signInUser(email: String, password: String) = fireBaseSource.signInUser(email, password)

    override fun saveUser(email: String, name: String) = fireBaseSource.saveUser(email, name)

    override fun fetchUser(): Task<QuerySnapshot> = fireBaseSource.fetchUser()

    override fun sendForgotPassword(email: String): Task<Void> =fireBaseSource.sendForgotPassword(email)
}