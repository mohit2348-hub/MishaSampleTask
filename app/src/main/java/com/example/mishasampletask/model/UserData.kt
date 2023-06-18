package com.example.mishasampletask.model

data class UserData(
    var title: String,
    var desc: String,
    var imageUrl: String,
    var isChecked: Boolean,
    var date: Long
) {
    // Add @JvmOverloads annotation to generate the default constructor

    @JvmOverloads
    constructor() : this("", "", "", true, 0L)
}
