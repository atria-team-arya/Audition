package com.atria.myapplication.viewModel.login

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.atria.myapplication.MainActivity
import com.atria.myapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class LoginFragmentViewModel(
    val context: Context,
    val view: View
) : ViewModel() {

    private val firebaseFireStore  = FirebaseFirestore.getInstance()


    fun showConfirmDialog(code:String,ph:String,onPositiveClick:()->Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Send OTP For Confirmation ?")
            .setMessage("One Time Password Will be Send To The provided Number $code$ph For Confirmation !")
            .setPositiveButton("Continue") { s, v ->
                onPositiveClick()
            }
            .setNegativeButton("Back",{s,v->})
            .show()
    }

    fun checkIfUserExists(text: String,onUserChecked:(Boolean)->Unit) {
        firebaseFireStore
            .collection("Users")
            .whereEqualTo("phNumber",text)
            .get()
            .addOnSuccessListener {
                // true if user doesn't exists
                 onUserChecked(it.isEmpty || it==null)
            }.addOnFailureListener {
                onUserChecked(true)
            }
    }


}