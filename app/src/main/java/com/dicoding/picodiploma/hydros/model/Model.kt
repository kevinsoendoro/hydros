package com.dicoding.picodiploma.hydros.model

data class UserModel(
    val name: String = "",
    val listPlants: HashMap<String, DataPlants> = hashMapOf()
)

data class DataPlants(
    val createdAt: Long = 0,
    val date: String = "",
    val plant: String = "",
    val description: String = "",
    val photoUrl: String = "",
    val status: Boolean = false,
)