package com.olesix.mynotes.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.olesix.mynotes.LOG_TAG
import com.olesix.mynotes.R

class SearchActivity : AppCompatActivity() {

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