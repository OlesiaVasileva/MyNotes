package com.olesix.mynotes.db

import androidx.room.*
import com.olesix.mynotes.Note


@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes")
    fun getAll(): List<Note>

    @Query("SELECT * FROM Notes WHERE id = :id")
    fun getById(id: Long): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}