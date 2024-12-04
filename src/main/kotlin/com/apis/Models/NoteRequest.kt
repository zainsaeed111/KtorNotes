package com.apis.Models

import kotlinx.serialization.Serializable


@Serializable
data class NoteRequest(
    val noteText: String,
)
