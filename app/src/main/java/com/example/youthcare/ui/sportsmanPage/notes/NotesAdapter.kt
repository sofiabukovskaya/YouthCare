package com.example.youthcare.ui.sportsmanPage.notes

import android.content.Context
import android.content.Intent
import android.text.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcare.ApiInterface
import com.example.youthcare.R
import com.example.youthcare.RetrofitInstance
import com.example.youthcare.repository.models.NoteData
import com.example.youthcare.repository.models.NoteResponse
import com.example.youthcare.ui.adminPage.UsersAdapter
import retrofit2.Call
import retrofit2.Response

class NotesAdapter(var notesList: ArrayList<NoteResponse>,  applicationContext: Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
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
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, s)
            notifyDataSetChanged()
            val intent = Intent.createChooser(shareIntent, s)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
        holder.copyButton?.setOnClickListener {
            val title = holder.title?.text.toString()
            val desc = holder.description?.text.toString()
            val copyText = title + "\n" + desc
            val clipBoard = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.text = copyText
            Toast.makeText(context, "Copied...", Toast.LENGTH_SHORT).show()
        }

        holder.deleteButton?.setOnClickListener {
            val retIn = RetrofitInstance.getRetrofitInstance(context).create(ApiInterface::class.java)
            retIn.deleteNote(notesList[position].id.toString()).enqueue(object: retrofit2.Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    notifyItemRemoved(position)
                    notesList.removeAt(position).id
                    notifyDataSetChanged()

                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    print("not delete + ${t.message}")
                }

            })
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var title: TextView? = null
        var description: TextView? = null
        var shareButton: ImageButton? = null
        var deleteButton: ImageButton? = null
        var copyButton: ImageButton? = null
        init {
            title = itemView.findViewById(R.id.titleTv)
            description = itemView.findViewById(R.id.descTv)
            shareButton = itemView.findViewById(R.id.shareBtn)
            deleteButton = itemView.findViewById(R.id.deleteBtn)
            copyButton = itemView.findViewById(R.id.copyBtn)
        }

        fun bindItems(notes: NoteResponse) {
            title?.text = notes.title
            description?.text = notes.description
        }
    }
}