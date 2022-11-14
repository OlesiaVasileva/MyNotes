package com.olesix.mynotes.editing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.olesix.mynotes.R


class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val toolbar: Toolbar = findViewById<View>(R.id.edit_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}