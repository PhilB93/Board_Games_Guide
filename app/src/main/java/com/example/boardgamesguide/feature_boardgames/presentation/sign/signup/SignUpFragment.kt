package com.example.boardgamesguide.feature_boardgames.presentation.sign.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.FragmentSignUpBinding
import com.example.boardgamesguide.util.Resource
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

fun View.showsnackBar(message:String){
    Snackbar.make(this,message, Snackbar.LENGTH_LONG).show()
}
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()



    private var binding: FragmentSignUpBinding? = null

    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.signUpBtn?.setOnClickListener {
            val emailText = binding?.emailEt?.text?.toString()
            val passwordText =  binding?.passwordEt?.text.toString()
            val fullNameText =  binding?.fullNameEt?.text?.toString()
            viewModel.signUpUser( emailText.toString(), passwordText, fullNameText.toString()).observe(viewLifecycleOwner, {
                        when (it) {
                            is Resource.Success -> {
                                viewModel.saveUser( it.data?.email.toString(), it.data?.fullName.toString())
                                view.showsnackBar("User account registered")
                            }
                            is Resource.Error -> {
                                view.showsnackBar(it.message!!)
                            }
                            is Resource.Loading -> {
                                view.showsnackBar("...")
                            }
                        } })
        }
        binding?.loginTv?.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.signUpFragment) {
                NavHostFragment.findNavController(this).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
            }
        }

    }




}