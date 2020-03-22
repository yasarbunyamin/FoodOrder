package com.example.foodorder.database.connector

import android.content.ContentValues.TAG
import android.util.Log
import com.example.foodorder.database.User
import com.google.firebase.database.*


class UserConnector{

    //private  var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")





     fun writeNewUser(userId: String, name: String, surname:String,
                                email: String? ,password:String?, address:String?,city:String?, phone:String? ) {
        val user = User(name, surname, email, password, address,city,phone)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

         val userId=database.push().key
         //database.child("user").child(userId).setValue(user)

         //val users: MutableMap<String, User> = HashMap()
         //users[userId] = user

         //database.setValue(users);
         database.child(userId!!).setValue(user)


         /* database.child(userId).child("name").setValue(name)
          database.child(userId).child("surname").setValue(surname)
          database.child(userId).child("email").setValue(email)
          database.child(userId).child("password").setValue(password)
          database.child(userId).child("address").setValue(address)
          database.child(userId).child("city").setValue(city)
          database.child(userId).child("phone").setValue(phone)*/

         val postListener = object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {
                 // Get Post object and use the values to update the UI
                 val post = dataSnapshot.getValue()

             }

             override fun onCancelled(databaseError: DatabaseError) {
                 // Getting Post failed, log a message
                 Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                 // ...
             }
         }
         database.addValueEventListener(postListener)

     }



}