package com.example.login_contact.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.login_contact.db.entities.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData <List<UserEntity>>

    @Insert
    fun insertUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM user  where email=:useremail and password= :passWord")
    fun login(useremail:String, passWord:String): UserEntity?
}