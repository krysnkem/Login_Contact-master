package com.example.login_contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login_contact.databinding.ActivityCategoryContactsBinding
import com.example.login_contact.db.UsersRepository
import com.example.login_contact.db.entities.CategoryContactEntity

class CategoryContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryContactsBinding
    private var categoryName: String? = null
    private var owner: String? = null

    private lateinit var adapter: ContactAdapter
    private lateinit var usersRepository: UsersRepository

    private lateinit var listOfCategoryContacts: ArrayList<CategoryContactEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryContactsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.categoryContactRecycleView.layoutManager = LinearLayoutManager(this)

        owner = intent.getStringExtra(CategoryActivity.OWNER)
        categoryName = intent.getStringExtra(CategoryActivity.CATEGORY_NAME)
        title = categoryName?.replaceFirstChar{
            it.uppercase()
        }
        adapter = ContactAdapter(categoryName!!)


        setUpData(binding)






    }

    override fun onResume() {
        super.onResume()

//        usersRepository.addCategoryContact(CategoryContactEntity(name ="name",number = "90902342342",categoryName = categoryName!! ))

        usersRepository.getRequiredCategoryContacts(categoryName!!, owner!!)?.observe(this, {list->
            adapter.submitList(list)
        })



//        val contacts: ArrayList<ContactModel> = ArrayList()
//        for(contact in listOfCategoryContacts){
//            contacts.add(ContactModel(contact.name, contact.number))
//        }

    }



    private fun setUpData(binding: ActivityCategoryContactsBinding) {


        usersRepository = UsersRepository(this)

        //bind the adapter to the RecycleView
        binding.categoryContactRecycleView.adapter = adapter

        //set up the Alert dialog
        val builder = AlertDialog.Builder(this)

        //get our template
        val view = layoutInflater.inflate(R.layout.alert_dialog_save_contact, null)


        builder.setView(view)
        listOfCategoryContacts = arrayListOf()




        //get the input fields of the template and the button
        val contactNameInput = view.findViewById<EditText>(R.id.name_textField)
        val contactNumberInput = view.findViewById<EditText>(R.id.number_textField)
        val saveContactBtn = view.findViewById<Button>(R.id.save_ContactBtn)

        contactNumberInput.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveContactBtn.isEnabled = p0?.length == 11
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        //create the alert dialog
        val alertDialog = builder.create()

        saveContactBtn.setOnClickListener {
            val contactName = contactNameInput.text.toString()
            val contactPhoneno = contactNumberInput.text.toString()

            //add the extracted contacts to the repository
            usersRepository.addCategoryContact(CategoryContactEntity(name =contactName,number = contactPhoneno,categoryName = categoryName!! , owner = owner!!))


            alertDialog.dismiss()
            contactNameInput.text.clear()
            contactNumberInput.text.clear()
        }

        binding.categoryContactAddBtn.setOnClickListener {
            alertDialog.show()
        }



    }


}