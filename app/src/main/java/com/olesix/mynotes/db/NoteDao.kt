package com.olesix.mynotes.db

import androidx.room.*
import com.olesix.mynotes.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes")
    suspend fun getAll(): MutableList<Note>

    @Query("SELECT * FROM Notes WHERE id = :id")
    suspend fun getById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}