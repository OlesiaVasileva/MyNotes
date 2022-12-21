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
import android.view.View
import android.widget.ImageView
import android.widget.TextView


class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var imageEmptyScreen: ImageView
    private lateinit var textEmptyScreen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        searchEditText = findViewById(R.id.search_edittext)
        imageEmptyScreen = findViewById(R.id.image_empty_screen)
        textEmptyScreen = findViewById(R.id.text_empty_screen)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter: NoteRecyclerAdapter = NoteRecyclerAdapter { note ->
            val intent = Intent(this@SearchActivity, EditActivity::class.java)
            intent.putExtra(INTENT_ID, note.id)
            startActivity(intent)
            Log.d(LOG_TAG, "Note.id = ${note.id}")
        }
        recyclerView.adapter = adapter
        adapter.setData(NotesList.getListOfNotes())
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                adapter.setData(NotesList.getNotesBySearch(s.toString()))
                showImageEmptyScreen(NotesList.getNotesBySearch(s.toString()).isEmpty())
            }
        })
    }

    private fun showImageEmptyScreen(listIsEmpty: Boolean) {
        if (listIsEmpty) {
            imageEmptyScreen.visibility = View.VISIBLE
            textEmptyScreen.visibility = View.VISIBLE
        } else {
            imageEmptyScreen.visibility = View.INVISIBLE
            textEmptyScreen.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel_search -> {
                Log.d(LOG_TAG, "Cancel search clicked")
                if (searchEditText.text.isEmpty()) {
                    onBackPressed()
                } else {
                    searchEditText.text.clear()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}