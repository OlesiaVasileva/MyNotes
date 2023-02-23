package com.olesix.mynotes.mainscreen

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.olesix.mynotes.R
import com.olesix.mynotes.adapter.NoteRecyclerAdapter
import com.olesix.mynotes.editing.EditActivity
import com.olesix.mynotes.model.Note
import com.olesix.mynotes.search.SearchActivity
import com.olesix.mynotes.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

const val LOG_TAG = "LOG_MY_NOTES"
const val INTENT_ID = "NOTE_ID"

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageEmptyScreen: ImageView
    private lateinit var textEmptyScreen: TextView
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var NET_REQUEST_RESPONSE: String
    private lateinit var mainThreadHandler: Handler
    private lateinit var uIThreadHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val floatAcButton: FloatingActionButton = findViewById(R.id.float_act_button)
        floatAcButton.setOnClickListener {
            val intent = Intent(this@MainActivity, EditActivity::class.java)
            startActivity(intent)
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
        val notesListObserver = Observer<List<Note>> { notes ->
            if (notes.isNullOrEmpty()) {
                supportActionBar?.hide()
                adapter.setData(mutableListOf())
                imageEmptyScreen.visibility = View.VISIBLE
                textEmptyScreen.visibility = View.VISIBLE
            } else {
                adapter.setData(notes as MutableList<Note>)
                supportActionBar?.show()
                imageEmptyScreen.visibility = View.INVISIBLE
                textEmptyScreen.visibility = View.INVISIBLE
            }
        }
        mainViewModel.getAllNotes()
        mainViewModel.listOfNotes.observe(this, notesListObserver)

        makeRequestUIThread()
    }

    private fun makeRequestUIThread() {
        val handlerThread = HandlerThread("UIThread")
        handlerThread.start()
        uIThreadHandler = Handler(handlerThread.looper)
        uIThreadHandler.post {
            netRequest()
        }
    }

    private fun netRequest() {
        Log.d(LOG_TAG, "networkRequest: ${Thread.currentThread().name}")
        Thread.sleep(5000)
        val newThread = Thread {
            setResult("Response after 5 sec")
        }
        newThread.start()
    }

    private fun setResult(result: String) {
        Log.d(LOG_TAG, "setResult: ${Thread.currentThread().name}")
        synchSetResult(result)
        postResultMainThread()
    }

    private fun synchSetResult(result: String) {
        val lock = ReentrantLock()
        lock.withLock {
            NET_REQUEST_RESPONSE = result
        }
    }

    private fun postResultMainThread() {
        mainThreadHandler = Handler(Looper.getMainLooper())
        mainThreadHandler.post {
            postResult()
        }
    }

    private fun postResult() {
        Log.d(LOG_TAG, "postResult: ${Thread.currentThread().name}")
        if (!NET_REQUEST_RESPONSE.isNullOrEmpty()) {
            Toast.makeText(this, NET_REQUEST_RESPONSE, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        mainViewModel.getAllNotes()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        uIThreadHandler.removeCallbacksAndMessages(null)
        mainThreadHandler.removeCallbacksAndMessages(null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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