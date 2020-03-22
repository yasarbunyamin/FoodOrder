package com.example.foodorder.fragment

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.foodorder.R
import com.example.foodorder.utils.ProgressDisplay
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment() : Fragment(),View.OnClickListener,ProgressDisplay {

    private val TAG = "LoginFragment"


    //Navigation Component
    lateinit var navController: NavController

    //Firebase Authentication
    private lateinit var auth: FirebaseAuth

    //UI Elements
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    //global variables
    private lateinit var email: String
    private lateinit var password: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.login_button).setOnClickListener(this)
        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        etEmail = view?.findViewById(R.id.login_email)
        etPassword = view?.findViewById(R.id.login_password)
        show()

        when(v!!.id){
            R.id.login_button -> loginAction()
            R.id.sign_up_button -> signUpAction()
        }

    }

    private fun signUpAction() {
        hide()
        navController.navigate(R.id.action_loginFragment_to_signUpFragment)

    }

    private fun loginAction() {

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        val db = Firebase.firestore

        val city = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA"
        )

        db.collection("home").document("LA")
            .set(city)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener {  Log.w(ContentValues.TAG, "Error writing document") }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(),OnCompleteListener { task ->
            if(task.isSuccessful) {
                hide()
                Toast.makeText(requireActivity(), "Successfully Logged In", Toast.LENGTH_LONG).show()

                navController.navigate(R.id.action_loginFragment_to_mainFragment)

                    } else {
                        hide()
                        Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
        }   else {
            hide()
            Toast.makeText(requireActivity(), "Please enter all details", Toast.LENGTH_SHORT).show()
        }
    }

     override fun show(){
        if (requireActivity() is ProgressDisplay ){

            (activity as ProgressDisplay?)!!.show()
        }
    }

     override fun hide() {
        if(requireActivity() is ProgressDisplay){
            (activity as ProgressDisplay?)!!.hide()
        }
    }



}
