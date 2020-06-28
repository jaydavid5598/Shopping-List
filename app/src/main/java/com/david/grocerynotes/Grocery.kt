package com.david.grocerynotes

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_grocery.*


class Grocery : AppCompatActivity() {
    private var adapter : GroceryAdapter? = null
    private var itemAmount = 0
    private var database: SQLiteDatabase ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery)

        val dbHelper = GroceryDBHelper(this)
        database = dbHelper.writableDatabase
        adapter = cursor()?.let { GroceryAdapter(this, it) }
        rvGroceryList.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(viewHolder.itemView.tag as Long)
            }
        }).attachToRecyclerView(rvGroceryList)

        lessAmount.setOnClickListener {
            decAmount()
        }
        moreAmount.setOnClickListener {
            incAmount()
        }
        addToList.setOnClickListener {
            addToLists()
        }

    }

    private fun decAmount(){
        if(itemAmount > 0){
            itemAmount--
            AmountCount.text = itemAmount.toString()
        }
    }

    private fun incAmount(){
        itemAmount++
        AmountCount.text = itemAmount.toString()
    }

    private fun addToLists(){
        if(itemToBuy.text.toString().trim().isEmpty() || itemAmount == 0 ){
            return
        }

        val item = itemToBuy.text.toString()
        val cv = ContentValues()
        cv.put(GroceryContract.GroceryEntry().ITEM, item)
        cv.put(GroceryContract.GroceryEntry().AMOUNT, itemAmount)

        database?.insert(GroceryContract.GroceryEntry().TABLE_NAME, null, cv)
        cursor()?.let { adapter?.swapCursor(it) }
        itemToBuy.text.clear()

    }

    private fun removeItem(id: Long) {
        database?.delete(GroceryContract.GroceryEntry().TABLE_NAME,
        GroceryContract.GroceryEntry().ID + "=" +id, null)
        cursor()?.let { adapter?.swapCursor(it) }
    }

    private fun cursor(): Cursor? {
        return database?.query(
            GroceryContract.GroceryEntry().TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            GroceryContract.GroceryEntry().TIMESTAMP +  " DESC")
    }
}
