package com.example.mishasampletask.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mishasampletask.R


class DescViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.descItemTitle)
    val titleCheckBoxView: CheckBox = itemView.findViewById(R.id.descTextCheckBox)

}
