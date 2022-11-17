package com.olesix.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.olesix.mynotes.editing.EditActivity


const val LOG_TAG = "LOG_MY_NOTES"
const val INTENT_ID = "NOTE_ID"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDataInViews()
        val floatAcButton: FloatingActionButton = findViewById(R.id.float_act_button)
        floatAcButton.setOnClickListener {
            val intent = Intent(this@MainActivity, EditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setDataInViews() {
        val listOfTestNotes = NotesList.notes
        listOfTestNotes.sortByDescending { note ->
            note.data
        }
        if (listOfTestNotes.isNotEmpty()) {
            val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = NoteRecyclerAdapter(listOfTestNotes)
            val adapter = recyclerView.adapter as NoteRecyclerAdapter
            adapter.onItemClick = { note ->
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                intent.putExtra(INTENT_ID, note.id)
                startActivity(intent)
                Log.d(LOG_TAG, "Note.id = ${note.id}")
            }
        } else {
            if (supportActionBar != null) {
                supportActionBar!!.hide()
                val imageEmptyScreen: ImageView = findViewById(R.id.image_empty_screen)
                imageEmptyScreen.visibility = View.VISIBLE
                val textEmptyScreen: TextView = findViewById(R.id.text_empty_screen)
                textEmptyScreen.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setDataInViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Log.d(LOG_TAG, "Search clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}