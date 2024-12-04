package com.apis.entities

import com.apis.entities.NotesEntity.primaryKey
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.timestamp
import org.ktorm.schema.varchar

object NotesEntity : Table<Nothing>(tableName = "user_notes") {
    val id = int("id").primaryKey()
    val note = varchar("note_text")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")


}
