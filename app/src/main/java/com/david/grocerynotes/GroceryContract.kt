package com.david.grocerynotes

import android.provider.BaseColumns

class GroceryContract {
    constructor()
    class GroceryEntry : BaseColumns{
        val TABLE_NAME = "groceryList"
        val ID = BaseColumns._ID
        val ITEM = "item"
        val AMOUNT = "amount"
        val TIMESTAMP = "timestamp"

    }
}