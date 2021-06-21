package com.example.youthcare.ui.sportsmanPage.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcare.R
import com.example.youthcare.repository.models.NoteData
import com.example.youthcare.ui.adminPage.UsersAdapter

class NotesAdapter(var notesList: ArrayList<NoteData>,  applicationContext: Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
    val context = applicationContext
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(notesList[position])
        holder.shareButton?.setOnClickListener {
            val title = holder.title?.text.toString()
            val desc = holder.description?.text.toString()
            val s = title + "\n" + desc
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, s)
//            startActivity(context, Intent.createChooser(shareIntent, s))
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var title: TextView? = null
        var description: TextView? = null
        var shareButton: Button? = null
        var deleteButton: Button? = null
        var copyButton: Button? = null
        init {
            title = itemView.findViewById(R.id.titleTv)
            description = itemView.findViewById(R.id.descTv)
            shareButton = itemView.findViewById(R.id.shareBtn)
            deleteButton = itemView.findViewById(R.id.deleteBtn)
            copyButton = itemView.findViewById(R.id.shareBtn)
        }

        fun bindItems(notes: NoteData) {
            title?.text = notes.title
            description?.text = notes.description
        }
    }
}