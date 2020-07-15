package com.example.walkmenu.loginui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.walkmenu.MainActivity
import com.example.walkmenu.R
import com.example.walkmenu.databinding.FragmentLoginEnterDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginEnterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLoginEnterDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_enter_details, container, false)

        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        setLoading()
        db.collection(getString(R.string.firestore_collection_user_data)).document(user!!.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.data.isNullOrEmpty()) {
                        setNotLoading()
                        binding.getStartedButton.setOnClickListener {

                            setLoading()

                            val userData = hashMapOf(getString(R.string.firestore_collection_user_data_field_name) to binding.loginNameEdit.text.toString(),
                                    getString(R.string.firestore_collection_user_data_field_email) to binding.loginEmailEdit.text.toString())

                            db.collection(getString(R.string.firestore_collection_user_data)).document(user.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        val intent = Intent(activity, MainActivity::class.java)
                                        startActivity(intent)
                                        requireActivity().finish()
                                    }
                                    .addOnFailureListener {
                                        setNotLoading()
                                        Toast.makeText(context, getString(R.string.unable_to_add_account), Toast.LENGTH_SHORT).show()
                                    }
                        }
                    } else {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }

        return binding.root

    }

    private fun setLoading() {
        binding.enterDetailsProgress.visibility = View.VISIBLE
        binding.getStartedButton.isEnabled = false
        binding.enterDetailsConstraint.visibility = View.INVISIBLE
    }

    private fun setNotLoading() {
        binding.enterDetailsProgress.visibility = View.GONE
        binding.getStartedButton.isEnabled = true
        binding.enterDetailsConstraint.visibility = View.VISIBLE
    }

}