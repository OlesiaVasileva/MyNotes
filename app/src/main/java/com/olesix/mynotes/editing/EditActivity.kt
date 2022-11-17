package com.olesix.mynotes.editing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.olesix.mynotes.Note
import com.olesix.mynotes.NotesList
import com.olesix.mynotes.R
import java.util.*

const val INTENT_ID = "NOTE_ID"

class EditActivity : AppCompatActivity() {

    private lateinit var editTextHeader: EditText
    private lateinit var editText: EditText
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val toolbar: Toolbar = findViewById(R.id.edit_toolbar)
        setSupportActionBar(toolbar)
        editText = findViewById(R.id.edit_text)
        editTextHeader = findViewById(R.id.edit_header)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        id = intent.getStringExtra(INTENT_ID)
        NotesList.notes.forEach{ note ->
            if (id == note.id) {
                editTextHeader.setText(note.header)
                editText.setText(note.text)
            }
        }
    }

    private fun addNewNote() {
        val id = UUID.randomUUID().toString()
        val header = editTextHeader.text.toString().trim()
        val text = editText.text.toString().trim()
        if (header.isNotEmpty() || text.isNotEmpty()) {
            val date = System.currentTimeMillis()
            val note = Note(id, header, text, date)
            NotesList.addNote(note)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (id.isNullOrEmpty()) {
            addNewNote()
        }
    }
}