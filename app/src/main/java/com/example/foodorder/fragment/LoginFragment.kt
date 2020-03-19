package com.example.foodorder.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.foodorder.MainActivity
import com.example.foodorder.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(),View.OnClickListener {

    lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    //UI Elements
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null

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

        when(v!!.id){
            R.id.login_button -> loginAction()//navController.navigate(R.id.action_loginFragment_to_mainFragment)
            R.id.sign_up_button -> navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }

    }

    private fun loginAction() {

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        Log.i("Email and Password",email +":"+ password)

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(),OnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(requireActivity(), "Successfully Logged In", Toast.LENGTH_LONG).show()
                navController.navigate(R.id.action_loginFragment_to_mainFragment)

                    } else {
                        Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
        }   else {
            Toast.makeText(requireActivity(), "Please enter all details", Toast.LENGTH_SHORT).show()
        }
    }
}
