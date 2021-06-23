package com.example.youthcare.ui.sportsmanPage.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.youthcare.ApiInterface
import com.example.youthcare.R
import com.example.youthcare.RetrofitInstance
import com.example.youthcare.api.SessionManager
import com.example.youthcare.repository.models.NoteData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        var titleNote: EditText? = null
        var descriptionNote: EditText? = null
        var buttonAddNote: Button? = null


        titleNote = findViewById(R.id.titleEt)
        descriptionNote = findViewById(R.id.descEt)
        buttonAddNote = findViewById(R.id.addBtn)
        val retIn = RetrofitInstance.getRetrofitInstance(applicationContext).create(ApiInterface::class.java)
        var sessionManager = SessionManager(applicationContext)
        buttonAddNote.setOnClickListener {
            retIn.createNote(
                NoteData(
                    sessionManager.fetchUserId().toString(), titleNote.text.toString(),
                    descriptionNote.text.toString()
                )
            ).enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Toast.makeText(applicationContext, "Note add successfully", Toast.LENGTH_LONG)
                        .show()
                    val intent = Intent(this@AddNoteActivity, NotesActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, "Something go wrong", Toast.LENGTH_LONG)
                        .show()
                }

            })
        }
    }
}