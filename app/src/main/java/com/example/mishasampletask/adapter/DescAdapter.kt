package com.example.mishasampletask.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mishasampletask.R
import com.example.mishasampletask.model.UserData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.values

class DescAdapter(private val dataList: List<UserData>, private val context: Context) :
    RecyclerView.Adapter<DescViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_desc, parent, false)
        return DescViewHolder(view)
    }

    override fun onBindViewHolder(holder: DescViewHolder, position: Int) {
        val dataObject = dataList[position]
        holder.titleTextView.text = dataObject.title
        holder.titleCheckBoxView.isChecked = dataObject.isChecked
        if (!holder.titleCheckBoxView.isChecked) {
            holder.titleTextView.paintFlags =
                holder.titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.titleTextView.paintFlags =
                holder.titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        }
        holder.titleCheckBoxView.setOnClickListener {
            if (!holder.titleCheckBoxView.isChecked) {
                updateData(
                    holder.titleCheckBoxView.isChecked,
                    holder.titleTextView.text.toString()
                )
            } else {
                updateData(
                    holder.titleCheckBoxView.isChecked,
                    holder.titleTextView.text.toString()
                )
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun updateData(isChecked: Boolean, path: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("UserData/${path}")
        val reference = databaseReference.child("checked")
        reference.setValue(isChecked).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Item updated successfully
                Log.d("UPDATED", "updateData:")
                Toast.makeText(context, "UPDATED", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("UPDATED_FAILED", "updateData:")
                Toast.makeText(context, " FAILED TO UPDATED", Toast.LENGTH_SHORT).show()
                // Error occurred while updating the item
            }
        }


    }
}