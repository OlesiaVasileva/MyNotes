package com.olesix.mynotes.editing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.olesix.mynotes.model.Note
import com.olesix.mynotes.R
import com.olesix.mynotes.viewmodel.EditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

const val INTENT_ID = "NOTE_ID"

class EditActivity : AppCompatActivity() {

    private lateinit var editTextHeader: EditText
    private lateinit var editText: EditText
    private lateinit var textViewDate: TextView
    private lateinit var bottomAppBar: BottomAppBar
    private val editViewModel: EditViewModel by viewModel()
    private lateinit var note: Note
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
        bottomAppBar = findViewById(R.id.bottom_appbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val floatActButton: FloatingActionButton = findViewById(R.id.float_act_button)
        floatActButton.setOnClickListener {
            onBackPressed()
        }
        id = intent.getStringExtra(INTENT_ID)
        if (id != null) {
            editViewModel.getNoteById(id!!)
            val noteObserver = Observer<Note> { note ->
                this.note = note
                textViewDate.visibility = View.VISIBLE
                editTextHeader.setText(note.header)
                editText.setText(note.text)
                val date = Date(note.date)
                textViewDate.text = this.getString(
                    R.string.date_display,
                    simpleDateFormat.format(date).toString()
                )
            }
            editViewModel.note.observe(this, noteObserver)
        }
        bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    deleteNoteFromDialog()
                    true
                }
                R.id.action_share -> {
                    shareNoteFromDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun deleteNoteFromDialog() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.alertDialogCustom)
        dialogBuilder.setMessage(getString(R.string.message_alert_dialog))
            .setPositiveButton(
                getString(R.string.button_delete)
            ) { _, _ ->
                if (id != null) {
                    editViewModel.deleteNote(note)
                }
                finish()
            }
            .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
                dialog.cancel()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.title_alert_dialog))
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.color_float_button
            )
        )
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.color_float_button
            )
        )
    }

    private fun shareNoteFromDialog() {
        val header = editTextHeader.text.toString().trim()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, header)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun addNewNote() {
        val id = UUID.randomUUID().toString()
        val header = editTextHeader.text.toString().trim()
        val text = editText.text.toString().trim()
        if (header.isNotEmpty() || text.isNotEmpty()) {
            val date = System.currentTimeMillis()
            val note = Note(id, header, text, date, "")
            editViewModel.addNote(note)
        }
    }

    override fun onBackPressed() {
        addNote()
        super.onBackPressed()
    }

    private fun addNote() {
        if (id.isNullOrEmpty()) {
            addNewNote()
        } else {
            if (editTextHeader.text.toString().isEmpty() && editText.text.toString().isEmpty()) {
                editViewModel.deleteNote(note)
            } else if (editTextHeader.text.toString() != note.header ||
                editText.text.toString() != note.text
            ) {
                note.header = editTextHeader.text.toString()
                note.text = editText.text.toString()
                updateNote(note)
            }
        }
    }

    private fun updateNote(newNote: Note) {
        newNote.header = editTextHeader.text.toString()
        newNote.text = editText.text.toString()
        newNote.date = System.currentTimeMillis()
        editViewModel.updateNote(newNote)
    }
}