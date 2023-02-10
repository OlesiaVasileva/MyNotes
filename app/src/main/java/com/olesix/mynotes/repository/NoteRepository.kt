package com.olesix.mynotes.repository

import android.util.Log
import com.olesix.mynotes.model.Note
import com.olesix.mynotes.db.NoteDao
import com.olesix.mynotes.mainscreen.LOG_TAG
import com.olesix.mynotes.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun getAll(): Response<MutableList<Note>> {
        return try {
            Response.Success(withContext(Dispatchers.IO) {
                Log.d(LOG_TAG, "getAll: ${Thread.currentThread().name}")
                noteDao.getAll()
            })
        } catch (e: IOException) {
            Response.Exception(e)
        }
    }

    suspend fun getById(id: String): Note {
        return withContext(Dispatchers.IO) {
            Log.d(LOG_TAG, "getById: ${Thread.currentThread().name}")
            noteDao.getById(id)
        }
    }

    suspend fun insert(note: Note) {
        return withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    suspend fun update(note: Note) {
        return withContext(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    suspend fun delete(note: Note) {
        return withContext(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }
}
