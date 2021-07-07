package com.denbofa.persistence

import androidx.lifecycle.ViewModel

class ShoppingViewModel: ViewModel() {

    fun addShoppingItem(shoppingItem: ShoppingModel, database: ShoppingDatabase){
        database.ShoppingDAO().addShoppingItem(shoppingItem)
    }


}