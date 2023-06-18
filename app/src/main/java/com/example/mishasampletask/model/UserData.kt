package com.example.mishasampletask.model

data class UserData(
    var title: String,
    var desc: String,
    var imageUrl: String,
    var isChecked: Boolean
) {
    // Add @JvmOverloads annotation to generate the default constructor

    @JvmOverloads
    constructor() : this("", "", "", true)
}
