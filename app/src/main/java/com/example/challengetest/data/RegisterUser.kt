package com.example.challengetest.data


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
class RegisterUser{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var idUser: Int = 0
    @ColumnInfo(name = "first_name")
    var firstName: String = ""
    @ColumnInfo(name = "lastName")
    var lastName: String = ""
    @ColumnInfo(name = "emailUser")
    var emailUser: String =""


    constructor() {}

    constructor(firstName: String, lastName: String, emailUser: String) {
        this.firstName = firstName
        this.lastName = lastName
        this.emailUser = emailUser
    }

    val isInValid get() = firstName.isNullOrEmpty() && lastName.isNullOrEmpty() && emailUser.isNullOrEmpty()
}
