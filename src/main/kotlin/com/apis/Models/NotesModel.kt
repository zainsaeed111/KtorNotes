package com.apis.Models

import kotlinx.serialization.Serializable

@Serializable
data class NotesModel (
    val id: Int,
    val noteText: String,
    val createdAt: String,
    val updatedAt: String,
)