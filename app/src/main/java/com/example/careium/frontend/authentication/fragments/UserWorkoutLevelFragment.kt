package com.example.careium.frontend.authentication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.careium.R
import com.example.careium.core.authentication.Authenticator
import com.example.careium.databinding.FragmentUserInfoBinding
import com.example.careium.databinding.FragmentWorkoutLevelBinding
import com.example.careium.databinding.LayoutErrorCustomViewBinding
import com.example.careium.frontend.factory.ErrorAlertDialog
import com.example.careium.frontend.factory.FutureGoal
import com.example.careium.frontend.factory.Gender
import com.example.careium.frontend.factory.WorkoutLevel

class UserWorkoutLevelFragment : Fragment(R.layout.fragment_workout_level) {
    private lateinit var binding: FragmentWorkoutLevelBinding
    private val user = Authenticator.user

    companion object {
        fun newInstance() = UserWorkoutLevelFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWorkoutLevelBinding.bind(view)

        updateUserDataUI()
        handleClickButtons()
    }

    private fun handleClickButtons() {
        binding.infoTransition.nextIcon.setOnClickListener {
            var workoutLevel: WorkoutLevel? = null
            when {
                binding.levelSedentary.isChecked -> workoutLevel = WorkoutLevel.Sedentary
                binding.levelSlightlyActive.isChecked -> workoutLevel = WorkoutLevel.SlightlyActive
                binding.levelModeratelyActive.isChecked -> workoutLevel = WorkoutLevel.ModeratelyActive
                binding.levelVeryActive.isChecked -> workoutLevel = WorkoutLevel.VeryActive
                binding.levelExtremelyActive.isChecked -> workoutLevel = WorkoutLevel.ExtremelyActive
            }

            when (workoutLevel) {
                null -> alert(
                    getString(R.string.error_title),
                    getString(R.string.error_future_workout_level_message)
                )
                else -> {
                    saveUserData(workoutLevel)
                    openGoalScreen()
                }
            }

        }

        binding.infoTransition.backIcon.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.auth_frame, UserInfoFragment.newInstance())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

    }


    private fun alert(title: String, message: String) {
        val view: LayoutErrorCustomViewBinding = binding.infoErrorView
        ErrorAlertDialog.alert(view, title, message)
    }

    private fun openGoalScreen() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.auth_frame, UserGoalFragment.newInstance())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

    private fun saveUserData(workoutLevel: WorkoutLevel) {
        user.workoutLevel = workoutLevel
    }

    private fun updateUserDataUI() {
        if (user.workoutLevel != null) {
            when (user.workoutLevel) {
                WorkoutLevel.Sedentary -> binding.levelSedentary.isChecked = true
                WorkoutLevel.SlightlyActive -> binding.levelSlightlyActive.isChecked = true
                WorkoutLevel.ModeratelyActive -> binding.levelModeratelyActive.isChecked = true
                WorkoutLevel.VeryActive -> binding.levelVeryActive.isChecked = true
                WorkoutLevel.ExtremelyActive -> binding.levelExtremelyActive.isChecked = true
                else -> {}
            }
        }
    }

}