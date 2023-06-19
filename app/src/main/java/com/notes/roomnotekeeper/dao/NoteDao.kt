package com.notes.roomnotekeeper.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.notes.roomnotekeeper.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM `note`")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM `note` WHERE id=:id")
    fun getNoteById(id: Int): Flow<Note>

}