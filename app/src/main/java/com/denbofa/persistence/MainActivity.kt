package com.denbofa.persistence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.denbofa.persistence.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myShoppingAdapter: ShoppingAdapter
    private lateinit var myShoppingList: MutableList<ShoppingModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myShoppingList = mutableListOf()

        myShoppingAdapter = ShoppingAdapter(myShoppingList){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("CATEGORY_KEY", it.category)
        }
        binding.recyclerView.adapter = myShoppingAdapter

        val db = Room.databaseBuilder(applicationContext, ShoppingDatabase::class.java, "shopping-database").allowMainThreadQueries().build()

        val shoppingDAO = db.ShoppingDAO()

        shoppingDAO.getAllShoppingItems().observe(this, {
            myShoppingAdapter = ShoppingAdapter(myShoppingList){ }
            binding.recyclerView.adapter = myShoppingAdapter
            myShoppingAdapter.notifyDataSetChanged()
        })


        binding.button.setOnClickListener {
            val category: String = binding.editText.text.toString()
            val description: String = binding.editText2.text.toString()

            val shoppingItem = ShoppingModel(category,description)

            shoppingDAO.addShoppingItem(shoppingItem)

            myShoppingList.add(shoppingItem)
            myShoppingAdapter.notifyDataSetChanged()
        }
    }
}