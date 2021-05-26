package com.example.login_contact.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var username: String,
    var email: String,
    var password: String
)
