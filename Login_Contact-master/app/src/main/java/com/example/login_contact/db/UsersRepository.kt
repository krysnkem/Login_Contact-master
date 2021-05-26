package com.example.login_contact.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.login_contact.db.entities.CategoryContactEntity
import com.example.login_contact.db.entities.CategoryEntiity
import com.example.login_contact.db.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersRepository(context: Context) {

    // Initialize the database
    val db = UserDatabase.invoke(context)

    // get the Database Access objects for each table
    val categoryDao = db.categoryDao()
    val dbDao = db.userDao()
    val categoryContactDao = db.categoryContactDao()


    //get the list for each entity

    fun getAllUsers(): LiveData<List<UserEntity>> = dbDao.getAllUsers()

    fun getAllCategories(): LiveData<List<CategoryEntiity>> = categoryDao.getAllCategory()


    fun getAllCategoryContact(): LiveData<List<CategoryContactEntity>> =
        categoryContactDao.getAllCategoryContact()



    //get a required query
    fun login(useremail: String, passWord: String): UserEntity? = dbDao.login(useremail, passWord)

    fun getRequiredCategory( owner: String): LiveData<List<CategoryEntiity>> = categoryDao.getallRequiredCategory( owner)

    fun checkCategory(category: String, owner:String): CategoryEntiity? = categoryDao.checkCategory(categoryname = category, owner = owner)

    fun getRequiredCategoryContacts(categoryname:String, owner: String): LiveData<List<CategoryContactEntity>>? = categoryContactDao.getRequiredCategoryContacts(categoryname = categoryname, owner = owner)




    fun insertUser(userEntity: UserEntity) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                dbDao.insertUser(userEntity)
            }

        }
    }

    fun addCategory(categoryEntiity: CategoryEntiity) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                categoryDao.addCategory(categoryEntiity)
            }
        }
    }

    fun addCategoryContact(categoryContactEntity: CategoryContactEntity) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {

                categoryContactDao.addCategoryContact(categoryContactEntity)
            }
        }
    }

}