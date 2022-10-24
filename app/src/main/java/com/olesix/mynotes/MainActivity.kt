package com.olesix.mynotes

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


const val LOG_TAG = "LOG_MY_NOTES"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listOfTestNotes = NotesList.notes
        if (listOfTestNotes.isNotEmpty()) {
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = NoteRecyclerAdapter(listOfTestNotes)
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