package com.olesix.mynotes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class NoteRecyclerAdapter(private val notes: List<Note>) : RecyclerView
.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TextView = itemView.findViewById(R.id.note_headline)
        val text: TextView = itemView.findViewById(R.id.note_text)
        val date: TextView = itemView.findViewById(R.id.note_date)
        val color: CardView = itemView.findViewById(R.id.note_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.header.text = notes[position].header
        holder.text.text = notes[position].text
        val stamp = Timestamp(notes[position].data)
        val date = Date(stamp.time)
        val simpleDateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        holder.date.text = simpleDateFormat.format(date).toString()
        holder.color.setCardBackgroundColor(Color.parseColor(notes[position].color))
    }

    override fun getItemCount() = notes.size
}