package com.example.walkmenu.loginui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.walkmenu.R
import com.example.walkmenu.databinding.FragmentLoginEnterPhoneBinding
import com.google.android.material.snackbar.Snackbar

class LoginEnterPhoneFragment : Fragment() {

    private lateinit var binding: FragmentLoginEnterPhoneBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_enter_phone, container, false)

        binding.loginButton.setOnClickListener {
            val phone = binding.loginPhoneEdit.text.toString()
            if(validatePhone(phone)) {
                val action = LoginEnterPhoneFragmentDirections.actionLoginEnterPhoneFragmentToLoginValidateOTPFragment(phone)
                Navigation.findNavController(it).navigate(action)
            }
        }

        return binding.root
    }

    fun validatePhone(phone: String): Boolean {
        return if(phone.matches(Regex("[1-9][0-9]{9}")))
            true
        else {
            Snackbar.make(binding.enterPhoneCoordinator, "Invalid phone number", Snackbar.LENGTH_SHORT).show()
            false
        }
    }

}