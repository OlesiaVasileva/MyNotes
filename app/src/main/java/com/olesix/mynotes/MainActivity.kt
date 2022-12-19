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
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.olesix.mynotes.editing.EditActivity
import com.olesix.mynotes.search.SearchActivity


const val LOG_TAG = "LOG_MY_NOTES"
const val INTENT_ID = "NOTE_ID"

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: ActionBar
    private lateinit var imageEmptyScreen: ImageView
    private lateinit var textEmptyScreen: TextView
    private val listOfTestNotes = NotesList.notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val floatAcButton: FloatingActionButton = findViewById(R.id.float_act_button)
        floatAcButton.setOnClickListener {
            val intent = Intent(this@MainActivity, EditActivity::class.java)
            startActivity(intent)
        }
        if (supportActionBar != null) {
            toolbar = supportActionBar as ActionBar
        }
        imageEmptyScreen = findViewById(R.id.image_empty_screen)
        textEmptyScreen = findViewById(R.id.text_empty_screen)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = NoteRecyclerAdapter { note ->
            val intent = Intent(this@MainActivity, EditActivity::class.java)
            intent.putExtra(INTENT_ID, note.id)
            startActivity(intent)
            Log.d(LOG_TAG, "Note.id = ${note.id}")
        }
        recyclerView.adapter = adapter
        setData()
    }

    private fun setData() {
        if (listOfTestNotes.isEmpty() && toolbar != null) {
            toolbar!!.hide()
            imageEmptyScreen.visibility = View.VISIBLE
            textEmptyScreen.visibility = View.VISIBLE
        } else {
            toolbar.show()
            imageEmptyScreen.visibility = View.INVISIBLE
            textEmptyScreen.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            listOfTestNotes.sortByDescending { note -> note.data }
            adapter.notes = listOfTestNotes
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Log.d(LOG_TAG, "Search clicked")
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}