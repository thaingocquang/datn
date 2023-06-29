package com.example.careium.core.database.authentication

import android.util.Log

class Login(private var email: String, private var password: String) : Auth() {

    override fun run() {


        this.authInstance.signInWithEmailAndPassword(this.email, this.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("quang-test", "signInWithEmail:success-"+this.authInstance.app.options.projectId)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("quang-test", "signInWithEmail:failure", task.exception)
                }
//                this.authInstance.currentUser?.email

                //set the view model with the callback from database authentication
                this.authViewModel.mutableIsAuthComplete.value = task.isSuccessful
            }


    }

    fun isUserLoggedIn(authViewModel: AuthViewModel) {
        this.authViewModel = authViewModel
        this.start()
    }

}