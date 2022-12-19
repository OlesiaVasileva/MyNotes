package com.olesix.mynotes.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olesix.mynotes.*
import com.olesix.mynotes.editing.EditActivity
import android.text.Editable

import android.text.TextWatcher


class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: NoteRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listOfFoundNotes: MutableList<Note>

    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        editText = findViewById(R.id.search_edittext)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = NoteRecyclerAdapter { note ->
            val intent = Intent(this@SearchActivity, EditActivity::class.java)
            intent.putExtra(INTENT_ID, note.id)
            startActivity(intent)
            Log.d(LOG_TAG, "Note.id = ${note.id}")
        }
        recyclerView.adapter = adapter
        adapter.notes = NotesList.notes
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                listOfFoundNotes = NotesList.getNotesBySearch(s.toString())
                setData()
            }
        })
    }

    private fun setData() {
        listOfFoundNotes.sortByDescending { note -> note.data }
        adapter.notes = listOfFoundNotes
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel_search -> {
                Log.d(LOG_TAG, "Cancel search clicked")
                if (editText.text.isEmpty()) {
                    onBackPressed()
                } else {
                    editText.text.clear()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}