package com.example.challengetest.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "user" , indices = [Index(value = ["emailUser", "id"], unique = true)])
class RegisterUser{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idUser: Int = 0
    @ColumnInfo(name = "first_name")
    var firstName: String = ""
    @ColumnInfo(name = "lastName")
    var lastName: String = ""
    @ColumnInfo(name = "emailUser")
    var emailUser: String =""
    @ColumnInfo(name = "isvisible")
    var isVisible: Boolean = true


    constructor() {}

    constructor(firstName: String, lastName: String, emailUser: String) {
        this.firstName = firstName
        this.lastName = lastName
        this.emailUser = emailUser
    }

    constructor(id: Int, firstName: String, lastName: String, emailUser: String) {
        this.idUser = id
        this.firstName = firstName
        this.lastName = lastName
        this.emailUser = emailUser
    }


}
