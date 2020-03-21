package com.example.foodorder.database

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class User (
    var name: String,
    var surname:String,
    var email: String?,
    var password: String?,
    var address: String?,
    var city:  String?,
    var phone: String?
)