package com.example.youthcare.ui.adminPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcare.ApiInterface
import com.example.youthcare.R
import com.example.youthcare.RetrofitInstance
import com.example.youthcare.repository.models.UserResponse
import com.example.youthcare.unSafeOkHttpClient
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class UsersAdapter(var usersList: ArrayList<UserResponse>, applicationContext: Context) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    val context = applicationContext
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.all_users, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(usersList[position])
        holder.deleteButton?.setOnClickListener {
            val retIn = RetrofitInstance.getRetrofitInstance(context).create(ApiInterface::class.java)
            unSafeOkHttpClient()
            retIn.deleteCurrentUser(usersList[position].id.toString()).enqueue(object :
                retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    notifyItemRemoved(position)
                    usersList.removeAt(position)
                    notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    print("not delete")
                }

            })

        }
    }
     override fun getItemCount(): Int {
        return usersList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var deleteButton: ImageButton? = null

        var textId: TextView? = null
        var textViewName: TextView? = null
        var textViewSurname: TextView? = null
        var textViewMiddleName: TextView? = null
        var textEmail: TextView? = null
        var textUserName: TextView? = null
        var textAddress: TextView? = null
        var textPhone: TextView? = null
        init {
           deleteButton  = itemView.findViewById<ImageButton>(R.id.delete)
             textId = itemView.findViewById<TextView>(R.id.textId)
          textViewName = itemView.findViewById<TextView>(R.id.textName)
           textViewSurname  = itemView.findViewById<TextView>(R.id.textSurname)
        textViewMiddleName  = itemView.findViewById<TextView>(R.id.textMiddlename)
            textEmail  = itemView.findViewById<TextView>(R.id.textEmail)
             textUserName  = itemView.findViewById<TextView>(R.id.textUsername)
             textAddress  = itemView.findViewById<TextView>(R.id.textAddress)
             textPhone  = itemView.findViewById<TextView>(R.id.textPhone)
        }
        fun bindItems(user: UserResponse) {
            textId?.text = user.id
            textViewMiddleName?.text = user.middleName
            textViewName?.text = user.name
            textViewSurname?.text = user.surname
            textEmail?.text = user.email
            textUserName?.text = user.username
            textAddress?.text = user.address
            textPhone?.text = user.phoneNumber

        }

    }

}



