package com.example.foodorder.database.connector

import com.example.foodorder.database.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase





class UserConnector{

    //private  var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")





     fun writeNewUser(userId: String, name: String, surname:String,
                                email: String? ,password:String?, address:String?,city:String?, phone:String? ) {
        val user = User(name, surname, email, password, address,city,phone)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

         //database.child("user").child(userId).setValue(user)

         val users: MutableMap<String, User> = HashMap()
         users[userId] = user

         database.setValue(users);


         /* database.child(userId).child("name").setValue(name)
          database.child(userId).child("surname").setValue(surname)
          database.child(userId).child("email").setValue(email)
          database.child(userId).child("password").setValue(password)
          database.child(userId).child("address").setValue(address)
          database.child(userId).child("city").setValue(city)
          database.child(userId).child("phone").setValue(phone)*/


     }

}