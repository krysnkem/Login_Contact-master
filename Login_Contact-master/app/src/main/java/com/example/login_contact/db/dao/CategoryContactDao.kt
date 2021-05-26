package com.example.login_contact.db.dao

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.login_contact.db.entities.CategoryContactEntity

@Dao
interface CategoryContactDao {

    @Query("select * from categorycontact")
    fun getAllCategoryContact(): LiveData<List<CategoryContactEntity>>

    @Insert
    fun addCategoryContact(categoryContactEntity: CategoryContactEntity)

    @Update
    fun updateCategoryContact(categoryContactEntity: CategoryContactEntity)

    @Delete
    fun deleteCategoryContact(categoryContactEntity: CategoryContactEntity)

    @Query("Select * from categorycontact where categoryName= :categoryname and owner= :owner")
    fun getRequiredCategoryContacts(categoryname: String, owner: String):LiveData<List <CategoryContactEntity>>?

   }