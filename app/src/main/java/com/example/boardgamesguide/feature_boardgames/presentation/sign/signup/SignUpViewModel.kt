package com.example.boardgamesguide.feature_boardgames.presentation.sign.signup

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
class SignUpViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val networkControl: NetworkControl,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()
    private val _saveUserLiveData = MutableLiveData<Resource<User>>()
    val saveUserLiveData = _saveUserLiveData
    fun signUpUser(email: String, password: String, fullName: String): LiveData<Resource<User>> {
                when {
                    TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty( fullName ) -> {
                        userLiveData.postValue(Resource.Error("field must not be empty", null))
                    }
                    password.length < 8 -> {
                        userLiveData.postValue( Resource.Error("password must not be less than 8", null))
                    }
                    networkControl.isConnected() -> {
                            userLiveData.postValue(Resource.Loading(null))
                        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                            if (it.result?.signInMethods?.size == 0) {
                                repository.signUpUser(email, password, fullName).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            firebaseAuth.currentUser?.sendEmailVerification()
                                            userLiveData.postValue( Resource.Success( User( email = email, fullName = fullName )))
                                        } else {
                                            userLiveData.postValue( Resource.Error( it.exception?.message.toString(), null))
                                        } }
                            } else {
                                userLiveData.postValue(Resource.Error( "email already exist", null))
                            } }
                    } else -> {
                    userLiveData.postValue(Resource.Error("No internet connection", null))
            } }
        return userLiveData
   }

    fun saveUser(email: String, name: String) {
        repository.saveUser(email, name).addOnCompleteListener {
            if (it.isSuccessful) {
            _saveUserLiveData.postValue(Resource.Success(User(email,name)))
            }else{
                _saveUserLiveData.postValue(Resource.Error(it.exception?.message.toString(), null))
            }
        }
    }
}