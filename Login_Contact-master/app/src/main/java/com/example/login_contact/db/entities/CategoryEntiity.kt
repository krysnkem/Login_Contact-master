package com.example.login_contact.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntiity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val category: String,
    val owner: String

)
