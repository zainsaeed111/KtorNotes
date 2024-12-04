package com.apis.Models

import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse (
    val status: Boolean?=null,
    val message: String?=null,
    val data: NotesModel? = null  // Change data type to NotesModel
)