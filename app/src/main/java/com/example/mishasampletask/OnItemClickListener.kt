package com.example.mishasampletask

interface OnItemClickListener {
    fun onItemClick(position: Int, title: String, desc: String, date: Long)
}