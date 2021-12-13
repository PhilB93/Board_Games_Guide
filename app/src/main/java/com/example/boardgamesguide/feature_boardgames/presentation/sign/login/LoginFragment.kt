package com.example.boardgamesguide.feature_boardgames.presentation.sign.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.FragmentLoginBinding
import com.example.boardgamesguide.util.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

fun View.showsnackBar(message:String){
    Snackbar.make(this,message, Snackbar.LENGTH_LONG).show()
}
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var binding: FragmentLoginBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.signUpTv?.setOnClickListener {

            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                NavHostFragment.findNavController(this)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
        }
        binding?.signInBtn?.setOnClickListener {
            val emailText = binding?.emailEt?.text?.toString()
            val passwordText = binding?.passwordEt?.text.toString()
            viewModel.signInUser(emailText!!, passwordText).observe(viewLifecycleOwner, {
                when (it) {
                   is Resource.Loading -> {
                        view.showsnackBar("...")
                    }

                    is Resource.Success -> {
                        view.showsnackBar("Login successful")
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            NavHostFragment.findNavController(this)
                                .navigate(
                                    LoginFragmentDirections.actionLoginFragmentToMainFragment()
                                )
                        }
                    }

                    is Resource.Error -> {
                        view.showsnackBar(it.message!!)
                    }
                }
            })
        }



        //forget password
        val dialog = AlertDialog.Builder(requireContext())
        val inflater = (requireActivity()).layoutInflater
        val v = inflater.inflate(R.layout.forgot_password, null)
        dialog.setView(v)
            .setCancelable(false)
        val d = dialog.create()
        val emailEt = v.findViewById<TextInputEditText>(R.id.emailEt)
        val sendBtn = v.findViewById<MaterialButton>(R.id.sendEmailBtn)
        val dismissBtn = v.findViewById<MaterialButton>(R.id.dismissBtn)


        sendBtn.setOnClickListener {
            viewModel.sendResetPassword(emailEt.text.toString()).observeForever {
                if (it is Resource.Success){
                    view.showsnackBar("reset email sent")
                }else{
                    view.showsnackBar(it.message.toString())
                }
            }
        }
        dismissBtn.setOnClickListener {
            d.dismiss()
        }


        binding?.forgotPasswordTv?.setOnClickListener {
            d.show()
        }
    }


}