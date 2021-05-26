package com.example.login_contact.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.login_contact.Category


@Entity(tableName = "categorycontact")
data class CategoryContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String,
    val number: String,
    val categoryName: String,
    val owner: String
)
