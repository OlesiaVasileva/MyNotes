package com.olesix.mynotes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class NoteRecyclerAdapter(private val onItemClick: ((Note) -> Unit)?) : RecyclerView
.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {

    private lateinit var notes: List<Note>
    private val simpleDateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TextView = itemView.findViewById(R.id.note_headline)
        val text: TextView = itemView.findViewById(R.id.note_text)
        val date: TextView = itemView.findViewById(R.id.note_date)
        val bgColor: CardView = itemView.findViewById(R.id.note_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.header.text = note.header
        holder.text.text = note.text
        val date = Date(note.date)
        holder.date.text = simpleDateFormat.format(date).toString()
        holder.bgColor.setCardBackgroundColor(Color.parseColor(note.color))
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(note)
        }
    }

    override fun getItemCount() = notes.size

    fun setData(listOfNotes: MutableList<Note>) {
        listOfNotes.sortByDescending { note -> note.date }
        notes = listOfNotes
        notifyDataSetChanged()
    }
}