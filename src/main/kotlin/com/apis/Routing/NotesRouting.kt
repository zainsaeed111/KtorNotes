package com.apis.Routing

import com.apis.Models.NoteRequest
import com.apis.Models.NoteResponse
import com.apis.Models.NotesModel
import com.apis.db.DatabaseConnection
import com.apis.entities.NotesEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import java.time.Instant

fun Application.notesRouting() {

    val db = DatabaseConnection.database

    routing {

        get("/GetNotes") {

            val notesData = db.from(NotesEntity).select()
                .map {
                    val id = it[NotesEntity.id]
                    val noteText = it[NotesEntity.note]
                    val createdAt = it[NotesEntity.createdAt].toString()
                    val updatedAT = it[NotesEntity.updatedAt].toString()
                    NotesModel(
                        id ?: -1, noteText ?: "", createdAt, updatedAT

                    )
                }

            call.respond(notesData)
        }

        post("/AddNotes") {

            val request = call.receive<NoteRequest>()
            log.info("Received request data: $request") // Log the incoming request data


            if (request.noteText.isNullOrEmpty()){
                call.respond(HttpStatusCode.BadRequest)
            }
            // Insert note and get generated ID
            val insertedId = db.insertAndGenerateKey(NotesEntity) {
                set(it.note, request.noteText)
                set(it.createdAt, Instant.now())  // Set creation time
                set(it.updatedAt, Instant.now())  // Set update time
            } as Int?  // Cast to Int (the auto-incremented ID)

            if (insertedId != null) {
                // Fetch the inserted note using the generated ID
                val addedNote = db.from(NotesEntity)
                    .select()
                    .where { NotesEntity.id eq insertedId }
                    .map {
                        NotesModel(
                            id = it[NotesEntity.id] ?: -1,
                            noteText = it[NotesEntity.note] ?: "",
                            createdAt = it[NotesEntity.createdAt].toString(),
                            updatedAt = it[NotesEntity.updatedAt].toString()
                        )
                    }.firstOrNull()

                if (addedNote != null) {
                    call.respond(HttpStatusCode.OK, NoteResponse(true, "Note insert success", addedNote))
                } else {
                    call.respond(HttpStatusCode.BadRequest, NoteResponse(false, "Note fetch failed after insert", null))
                }

            } else {
                call.respond(HttpStatusCode.BadRequest, NoteResponse(false, "Note insert failed", null))
            }
        }


        put("/UpdateNotes/{id}") {
            val noteId = call.parameters["id"]?.toIntOrNull()
            if (noteId == null) {
                call.respond(HttpStatusCode.BadRequest, NoteResponse(false, "Invalid note ID", null))
                return@put
            }

            val request = call.receive<NoteRequest>()
            log.info("Received update request for note ID $noteId with data: $request")

            try {
                val dbNote = db.from(NotesEntity).select()
                    .where { NotesEntity.id eq noteId }
                    .map { row ->
                        NotesModel(
                            id = row[NotesEntity.id] ?: -1,
                            noteText = row[NotesEntity.note].toString(),
                            createdAt = row[NotesEntity.createdAt].toString(),
                            updatedAt = row[NotesEntity.updatedAt].toString()
                        )
                    }
                    .firstOrNull()

                if (dbNote == null) {
                    call.respond(HttpStatusCode.BadRequest, NoteResponse(false, "Note not found", null))
                    return@put
                }

                val updatedRows = db.update(NotesEntity) {
                    set(it.note, request.noteText)
                    set(it.updatedAt, Instant.now())
                    where { it.id eq noteId }
                }

                if (updatedRows > 0) {
                    val updatedNote = db.from(NotesEntity).select()
                        .where { NotesEntity.id eq noteId }
                        .map { row ->
                            NotesModel(
                                id = row[NotesEntity.id] ?: -1,
                                noteText = row[NotesEntity.note].toString(),
                                createdAt = row[NotesEntity.createdAt].toString(),
                                updatedAt = row[NotesEntity.updatedAt].toString()
                            )
                        }
                        .firstOrNull()

                    call.respond(HttpStatusCode.OK, NoteResponse(true, "Note updated successfully", updatedNote))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, NoteResponse(false, "Failed to update note", null))
                }
            } catch (e: Exception) {
                log.error("Error updating note: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, NoteResponse(false, "Internal server error", null))
            }



        }


        delete("/DeleteNotes/{id}") {
            val noteId=call.parameters["id"]?.toIntOrNull()
            if (noteId == null) {
                call.respond(HttpStatusCode.BadRequest, NoteResponse(false, "Invalid note ID", null))
                return@delete
            }
            log.info("Deleting note with id $noteId")

             try {
                 val deletedNotes = db.delete(NotesEntity){
                     it.id eq noteId
                 }
                 if (deletedNotes > 0) {
                     call.respond(HttpStatusCode.OK, NoteResponse(true, "Note deleted successfully", null))
                 }else{
                     call.respond(HttpStatusCode.NotFound, NoteResponse(false, "Note not found", null))
                 }


             }catch (e:Exception){
                 log.error("Error deleting note: ${e.message}", e)
                 call.respond(HttpStatusCode.InternalServerError, NoteResponse(false, "Internal server error", null))
             }




        }

    }



    }
