package com.example.careium.frontend.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.careium.R
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.realtime.UserData
import com.example.careium.core.models.User
import com.example.careium.databinding.FragmentProfileBinding
import com.example.careium.frontend.factory.UserDataViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userDataViewModel: UserDataViewModel

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        getUserData()

        val editProfileText: TextView = binding.editProfileBtn
        editProfileText.setOnClickListener {
            //TODO: activate editing user profile by enable editing EditViews in profile fragment layout
            try {
                val userID: String? = FirebaseAuth.getInstance().currentUser?.uid
                val root = FirebaseDatabase.getInstance().reference
                val node = root.child("users").child(userID!!)
                node.child("weight").setValue(binding.userWeightVal.text.toString().toDouble())

                Toast.makeText(context, "Đã lưu thông tin", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.d("database", e.toString())
            }
        }
    }

    private fun getUserData() {
        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)

        binding.waitContainer.visibility = View.VISIBLE

        if (InternetConnection.isConnected(this.requireContext())) {
            val userData = UserData()
            userData.getUserData(userDataViewModel)

            userDataViewModel.mutableUserData.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    binding.waitContainer.visibility = View.GONE
                    updateTextView(userDataViewModel.mutableUserData.value!!)
                }
            }
        } else {
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun updateTextView(user: User) {

        binding.userName.text = getString(R.string.user_name_profile, user.name)
        binding.userHeightVal.text = user.height.toString()
        binding.userWeightVal.setText(user.weight.toString())
        binding.userAgeVal.text = getString(R.string.age_val, user.age)
        binding.userDesiredWeight.text = getString(R.string.desired_weight_val, user.desiredWeight)
        binding.userGenderVal.text = getString(R.string.gender_val, user.gender!!.name)
        binding.userTypeVal.text = getString(R.string.type_val, user.futureGoal!!.name)
    }

}