package com.example.youthcare.ui.adminPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcare.*
import com.example.youthcare.repository.models.UserResponse
import okhttp3.Call
import retrofit2.Response
import java.util.ArrayList


class AdminActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        recyclerView = findViewById(R.id.recyclerAdmin)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var users : ArrayList<UserResponse>
        HttpsTrustManager.allowAllSSL()
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        unSafeOkHttpClient()

        retIn.getAllUsers().enqueue(object : retrofit2.Callback<ArrayList<UserResponse>> {
            override fun onResponse(call: retrofit2.Call<ArrayList<UserResponse>>, response: Response<ArrayList<UserResponse>>) {
                if (response.code() == 200) {
                    users = response.body()!!
                         val adapter = UsersAdapter(users)
                    recyclerView.adapter = adapter

                } else {
                    Toast.makeText(
                        this@AdminActivity,
                        "Sorry, you haven't users :(",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<UserResponse>>, t: Throwable) {
                Toast.makeText(
                    this@AdminActivity,
                    "Sorry, you haven't users :(",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

}
