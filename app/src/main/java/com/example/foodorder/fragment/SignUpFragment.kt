package com.example.foodorder.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.foodorder.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpFragment : Fragment() {

    lateinit var navController: NavController

    //UI Elements
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "SignUpFragment"

    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initialise()
    }

    private fun initialise() {

        etFirstName = view?.findViewById(R.id.name)
        etLastName = view?.findViewById(R.id.surname)
        btnCreateAccount = view!!.findViewById(R.id.register_submit)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener {
            createNewAccount()
        }


    }

    private fun createNewAccount() {
        etEmail = view?.findViewById(R.id.register_email)
        etPassword = view?.findViewById(R.id.register_password)

        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {
            mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->

                    if (task.isSuccessful) {

                        //Registration OK
                        val firebaseUser = mAuth!!.currentUser!!

                        navController.navigate(R.id.action_signUpFragment_to_loginFragment)
                        Log.d("Tag", firebaseUser.toString())

                    } else {


                        Handler(Looper.getMainLooper()).post {
                          //  Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        }

                        if(task.exception is FirebaseAuthWeakPasswordException ){
                                Toast.makeText(requireActivity(),"Invalid Password at least 6 characters", Toast.LENGTH_SHORT).show()
                        }   else {
                            Toast.makeText(
                                requireActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
                }


        } else {
            Toast.makeText(requireActivity(), "Please enter all details", Toast.LENGTH_SHORT).show()
        }
    }

}





