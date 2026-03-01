package com.example.mobile_smart_pantry_project_iv.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val UUID: String,
    val Name: String,
    var Quantity: Int,
    val Category: String,
    val ImageRef: String
) : java.io.Serializable
