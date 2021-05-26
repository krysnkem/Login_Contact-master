package com.example.login_contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.login_contact.databinding.ActivityCategoryBinding
import com.example.login_contact.db.UsersRepository
import com.example.login_contact.db.entities.CategoryEntiity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoryActivity : AppCompatActivity(), CustomAdapter.OnitemClickListener {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter :CustomAdapter
    private lateinit var usersRepository: UsersRepository
    private lateinit var defaultList: List<CategoryEntiity>
    var owner: String? = null
//    var defaultList = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        owner = intent.getStringExtra(SignInAcitvity.OWNER_NAME)

        if (owner != null){
            adapter=  CustomAdapter(this, owner!! )
        }



        setContentView(binding.root)
        title = "${owner}'s Contacts"
        binding.categoryRecycleView.layoutManager = GridLayoutManager(this, 2)
        setupData(binding)
    }

    override fun onResume() {
        super.onResume()
        usersRepository.getRequiredCategory(owner!!).observe(this, { categories ->
            adapter.submitList(categories)

        })
    }

    private fun setupData(binding: ActivityCategoryBinding) {
        binding.categoryRecycleView.adapter = adapter
        usersRepository = UsersRepository(this)

        if(owner != null){
            defaultList = listOf(

                CategoryEntiity(category = "Family", owner = owner!!),
                CategoryEntiity(category = "Work", owner = owner!!),
                CategoryEntiity(category = "School", owner = owner!!),
                CategoryEntiity(category = "Friends", owner = owner!!)


            )
        }
        CoroutineScope(Dispatchers.IO).launch {
            for (item in defaultList) {
                val category: CategoryEntiity? = usersRepository.checkCategory(item.category, owner=owner!!)
                withContext(Dispatchers.Main) {
                    if (category == null) {

                        usersRepository.addCategory(CategoryEntiity(category = item.category, owner =owner!!))
                    }
                }

            }
        }


        val builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.add_category_dialog, null)

        builder.setView(view)

        val newCategoryField = view.findViewById<EditText>(R.id.categoryName_textField)

        val savebtn = view.findViewById<Button>(R.id.save_category_btn)

        val addCategorybtn = binding.addCategoryBtn

        newCategoryField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                savebtn.isEnabled = p0?.length!! > 2
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        val alertDialog = builder.create()

        savebtn.setOnClickListener {
            val newCategoryName = newCategoryField.text.toString()
//            adapter.addCategory(Category(newCategoryName))
            if (owner != null){
                usersRepository.addCategory(CategoryEntiity(category = newCategoryName, owner = owner!!))
            }


            alertDialog.dismiss()
            newCategoryField.text.clear()

        }


        addCategorybtn.setOnClickListener {
            alertDialog.show()
        }


    }

    override fun onItemClicked(category: CategoryEntiity) {
        val intent = Intent(this, CategoryContactsActivity::class.java)
        intent.putExtra(CATEGORY_NAME, category.category)
        intent.putExtra(OWNER, category.owner)
        startActivity(intent)
    }


    companion object {
        val CATEGORY_NAME = "category name"
        val OWNER = "owner"
    }

}
