package com.olesix.mynotes.editing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.olesix.mynotes.Note
import com.olesix.mynotes.NotesList
import com.olesix.mynotes.R
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

const val INTENT_ID = "NOTE_ID"

class EditActivity : AppCompatActivity() {

    private lateinit var editTextHeader: EditText
    private lateinit var editText: EditText
    private lateinit var textViewDate: TextView
    var id: String? = null
    private val simpleDateFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val toolbar: Toolbar = findViewById(R.id.edit_toolbar)
        setSupportActionBar(toolbar)
        editText = findViewById(R.id.edit_text)
        editTextHeader = findViewById(R.id.edit_header)
        textViewDate = findViewById(R.id.text_view_date)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        id = intent.getStringExtra(INTENT_ID)
        if (id != null) {
            textViewDate.visibility = View.VISIBLE
        }
        NotesList.notes.forEach { note ->
            if (id == note.id) {
                editTextHeader.setText(note.header)
                editText.setText(note.text)
                val date = Date(note.data)
                textViewDate.text = this.getString(
                    R.string.date_display,
                    simpleDateFormat.format(date).toString()
                )
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
        } else {
            NotesList.notes.forEach { note ->
                if (id == note.id) {
                    if (editTextHeader.text.toString().isEmpty() &&
                        editText.text.toString().isEmpty()
                    ) {
                        NotesList.notes.remove(note)
                        return
                    } else if (editTextHeader.text.toString() != note.header ||
                        editText.text.toString() != note.text
                    ) {
                        note.header = editTextHeader.text.toString()
                        note.text = editText.text.toString()
                        note.data = System.currentTimeMillis()
                        val date = Date(note.data)
                        textViewDate.text = this.getString(
                            R.string.date_display,
                            simpleDateFormat.format(date).toString()
                        )
                    }
                }
            }
        }
    }
}