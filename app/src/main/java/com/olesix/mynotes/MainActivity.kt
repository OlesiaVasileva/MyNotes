package com.olesix.mynotes

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

const val LOG_TAG = "LOG_MY_NOTES"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = R.id.tool_bar
        actionBar.apply {
            title = ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        menu?.getItem(0)?.icon?.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_IN)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search-> {
                Log.d(LOG_TAG, "Search clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}