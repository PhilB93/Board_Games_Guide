package com.example.boardgamesguide.feature_boardgames.presentation.sign.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.boardgamesguide.feature_boardgames.domain.login.User
import com.example.boardgamesguide.feature_boardgames.domain.repository.LoginRepository
import com.example.boardgamesguide.util.NetworkControl
import com.example.boardgamesguide.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val networkControl: NetworkControl,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()

    private val sendResetPasswordLiveData = MutableLiveData<Resource<User>>()
    fun signInUser(email: String, password: String): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) && TextUtils.isEmpty(password) -> {
                userLiveData.postValue(Resource.Error("Enter email and password",null ))
            }
            networkControl.isConnected() -> {
                userLiveData.postValue(Resource.Loading(null))
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                    //check if email exists
                    if (it.result?.signInMethods?.size == 0) {
                        userLiveData.postValue(Resource.Error("Email does not exist", null))
                    } else {
                        repository.signInUser(email, password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                               // firebaseAuth.currentUser?.isEmailVerified?.let { verified ->
                              //      if (verified) {
                                        repository.fetchUser().addOnCompleteListener { userTask ->
                                            if (userTask.isSuccessful) {
                                                userTask.result?.documents?.forEach {
                                                    if (it.data!!["email"] == email) {
                                                        val name = it.data?.getValue("fullName")
                                                        userLiveData.postValue(Resource.Success(User(firebaseAuth.currentUser?.email!!, name?.toString()!!)
                                                            )) } }
                                            } else {
                                                userLiveData.postValue(Resource.Error(userTask.exception?.message.toString(),null ))
                                            }
                                        }
                                    }
//                            else {
//                                        userLiveData.postValue(Resource.Error( "Email is not verified, check your email", null))
//                                    }

                             else {
                                userLiveData.postValue(Resource.Error( task.exception?.message.toString(),null,))

                            } } } }
            }
            else -> {
                userLiveData.postValue(Resource.Error( "No internet connection",null))
            }
        }
        return userLiveData
    }



    fun sendResetPassword(email: String): LiveData<Resource<User>> {

        when {
            TextUtils.isEmpty(email) -> {
                sendResetPasswordLiveData.postValue(Resource.Error("Enter registered email",null))
            }
            networkControl.isConnected() -> {
                repository.sendForgotPassword(email).addOnCompleteListener { task ->
                    sendResetPasswordLiveData.postValue(Resource.Loading(null))
                    if (task.isSuccessful) {
                        sendResetPasswordLiveData.postValue(Resource.Success(User()))
                    } else {
                        sendResetPasswordLiveData.postValue(
                            Resource.Error(
                                task.exception?.message.toString(),
                                null

                            )
                        )
                    }
                }
            }
            else -> {
                sendResetPasswordLiveData.postValue(Resource.Error("No internet connection",null ))
            }
        }
        return sendResetPasswordLiveData
    }
}