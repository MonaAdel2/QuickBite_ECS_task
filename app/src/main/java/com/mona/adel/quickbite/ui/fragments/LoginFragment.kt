package com.mona.adel.quickbite.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.repository.LoginRepoImp
import com.mona.adel.quickbite.databinding.FragmentLoginBinding
import com.mona.adel.quickbite.ui.activities.AdminActivity
import com.mona.adel.quickbite.ui.factories.LoginViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.LoginViewModel
import kotlin.math.log


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"


    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()

        loginViewModel.retrievedUser.observe(requireActivity()){user->
            if (user != null){
                binding.tvErrorMessage.visibility = View.GONE
                saveUserToSharedPreference(user.userId)

                // navigate based on the role
                if(user.role.equals("admin")){
                    // navigate to admin activity
                    startActivity(Intent(requireActivity(), AdminActivity::class.java))
                    requireActivity().finish()
                }else{
                    // navigate to home fragment
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(action)

                }
            }else{
                binding.tvErrorMessage.text = "Wrong email or password."
                binding.tvErrorMessage.visibility = View.VISIBLE
                Log.d(TAG, "onViewCreated: the user is null")
            }
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.txtEtEmail.editText?.text.toString()
            val password = binding.txtEtPassword.editText?.text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()){
                binding.tvErrorMessage.text = "Please fill all the fields."
                binding.tvErrorMessage.visibility = View.VISIBLE
                return@setOnClickListener
            }
            binding.tvErrorMessage.visibility = View.GONE
            loginViewModel.getUserByEmail(email, password)

        }


    }

    private fun saveUserToSharedPreference(userId: Int?){
        if(userId != null){
            val sharedPref = activity?.getSharedPreferences("appPref", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putInt("userId", userId)
            editor?.apply()
        }
    }


    private fun getViewModelReady(){

        val factory = LoginViewModelFactory(
            LoginRepoImp(
                LocalDataSourceImp(requireContext())
            )
        )

        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

}