package com.example.youthcare.ui.sportsmanPage.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcare.ApiInterface
import com.example.youthcare.R
import com.example.youthcare.RetrofitInstance
import com.example.youthcare.RetrofitSections
import com.example.youthcare.api.SessionManager
import com.example.youthcare.repository.models.NoteData
import com.example.youthcare.repository.models.NoteResponse
import com.example.youthcare.repository.models.UserResponse
import com.example.youthcare.ui.adminPage.UsersAdapter
import com.example.youthcare.ui.sportsmanPage.LoginSportsman
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class NotesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        var addNoteButton: FloatingActionButton? = null
        recyclerView = findViewById(R.id.notesLv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var  notes: ArrayList<NoteResponse>
        val retIn = RetrofitInstance.getRetrofitInstance(applicationContext).create(ApiInterface::class.java)
        var sessionManager = SessionManager(applicationContext)
        retIn.getNotes().enqueue(object : retrofit2.Callback<ArrayList<NoteResponse>>{
            override fun onResponse(
                call: Call<ArrayList<NoteResponse>>,
                response: Response<ArrayList<NoteResponse>>
            ) {
                    notes = response.body()!!
                    val adapter = NotesAdapter(notes, applicationContext)
                    recyclerView.adapter = adapter

            }

            override fun onFailure(call: Call<ArrayList<NoteResponse>>, t: Throwable) {
                Toast.makeText(
                    this@NotesActivity,
                    "Sorry, you haven't notes :( + ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                print("ERRROOOR ${t.message}")
            }

        })
        addNoteButton  = findViewById(R.id.floatingActionButton2)
        addNoteButton.setOnClickListener {
            val intent = Intent (this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

}