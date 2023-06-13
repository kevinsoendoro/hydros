package com.dicoding.picodiploma.hydros.model

data class UserModel(
    val name: String = "",
    val createdAt: String = ""
) {
    constructor() : this("", "")
}
