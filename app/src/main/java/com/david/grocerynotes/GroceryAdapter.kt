package com.david.grocerynotes

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grocery_layout.view.*

class GroceryAdapter(private val context: Context, private var cursor: Cursor) : RecyclerView.Adapter<GroceryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.grocery_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
    return cursor.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       if(!cursor.moveToPosition(position)){
           return
       }
        val name = cursor.getString(cursor.getColumnIndex(GroceryContract.GroceryEntry().ITEM))
        val amount = cursor.getString(cursor.getColumnIndex(GroceryContract.GroceryEntry().AMOUNT))
        val id:Long = cursor.getLong(cursor.getColumnIndex(GroceryContract.GroceryEntry().ID))

        holder.itemView.itemLayout.text = name
        holder.itemView.amountLayout.text = amount.toString()
        holder.itemView.setTag(id)
    }

    fun swapCursor(newCursor : Cursor){
        cursor.close()
        cursor = newCursor
        notifyDataSetChanged()
    }
}