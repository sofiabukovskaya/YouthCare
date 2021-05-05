package com.example.youthcare.ui.sportsmanPage.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.youthcare.*
import com.example.youthcare.api.SessionManager
import com.example.youthcare.repository.models.SportsmanData
import com.example.youthcare.repository.models.SportsmanUpdateResponse
import com.example.youthcare.ui.sportsmanPage.LoginSportsman
import retrofit2.Call
import retrofit2.Response

class ProfileFragment : Fragment() {

    var editNameSportsman : EditText? = null
    var editSportsmanSurname: EditText? = null
    var editSportsmanMiddleName: EditText? = null
    var editSportsmanPhoneNumber: EditText? = null
    var editSportsmanAddress: EditText? = null
    var editSportsmanEmail: EditText? = null
    var editSportsmanUserName: EditText? = null
    var editSportsmanPassword: EditText? = null
    var editId: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         var rootview = inflater.inflate(R.layout.fragment_profile, container, false)
        getInfoSportsman()
        editNameSportsman = rootview.findViewById<EditText>(R.id.editNameSportsman)
        editSportsmanSurname = rootview.findViewById<EditText>(R.id.editSportsmanSurname)
        editSportsmanMiddleName = rootview.findViewById<EditText>(R.id.editSportsmanMiddleName)
        editSportsmanPhoneNumber = rootview.findViewById<EditText>(R.id.editSportsmanPhoneNumber)
        editSportsmanAddress = rootview.findViewById<EditText>(R.id.editSportsmanAddress)
        editSportsmanEmail = rootview.findViewById<EditText>(R.id.editSportsmanEmail)
        editSportsmanUserName = rootview.findViewById<EditText>(R.id.editSportsmanUserName)
        editSportsmanPassword = rootview.findViewById<EditText>(R.id.editSportsmanPassword)
        editId = rootview.findViewById<EditText>(R.id.editID)
        return  rootview
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val button_logout = view.findViewById<Button>(R.id.button_logout_sportsman)

        val button_update_info = view.findViewById<Button>(R.id.button_update_profile)
        getInfoSportsman()

        button_update_info.setOnClickListener {
            updateInfo()
        }
        button_logout.setOnClickListener {
            context?.getSharedPreferences(context!!.getString(R.string.app_name), 0)?.edit()?.clear()
            Toast.makeText(activity, "You have logout!", Toast.LENGTH_LONG ).show();
            val intent = Intent (activity, LoginSportsman::class.java)
            activity?.startActivity(intent)
        }

    }


    fun updateInfo() {
        HttpsTrustManager.allowAllSSL()
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        unSafeOkHttpClient()
        retIn.updateInfoSportsman(SportsmanUpdateResponse(editNameSportsman?.text.toString(), editSportsmanSurname?.text.toString(),
                editSportsmanMiddleName?.text.toString(), editSportsmanPhoneNumber?.text.toString(),editSportsmanAddress?.text.toString(), editSportsmanEmail?.text.toString(), editSportsmanUserName?.text.toString(), "Sportsman", editSportsmanPassword?.text.toString())).enqueue(object : retrofit2.Callback<SportsmanUpdateResponse> {
            override fun onResponse(call: Call<SportsmanUpdateResponse>, response: Response<SportsmanUpdateResponse>) {
                if (response.code() == 200) {
                        Toast.makeText(activity, "Data update successfully", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SportsmanUpdateResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getInfoSportsman() {
        var sessionManager = SessionManager(activity)
        HttpsTrustManager.allowAllSSL()
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        unSafeOkHttpClient()
        retIn.getInfoSportsman(accessToken = "Bearer ${sessionManager.fetchAuthToken()}").enqueue(object : retrofit2.Callback<SportsmanData> {
            override fun onResponse(call: Call<SportsmanData>, response: Response<SportsmanData>) {
                if (response.code() == 200) {
                    Log.d("sae", "onResponse:  name ${response.body()?.name.toString()}")
                    val nameSport: String = response.body()?.name.toString()
                    val surnameSport: String = response.body()?.surname.toString()
                    val middleNameSport: String = response.body()?.middleName.toString()
                    val phoneSport: String = response.body()?.phoneNumber.toString()
                    val addressSport: String = response.body()?.address.toString()
                    val emailSport: String = response.body()?.email.toString()
                    val userNameSport: String = response.body()?.username.toString()
                    val passeord: String = response.body()?.password.toString()
                    editNameSportsman?.setText(nameSport)
                    editSportsmanSurname?.setText(surnameSport)
                    editSportsmanMiddleName?.setText(middleNameSport)
                    editSportsmanPhoneNumber?.setText(phoneSport)
                    editSportsmanAddress?.setText(addressSport)
                    editSportsmanEmail?.setText(emailSport)
                    editSportsmanUserName?.setText(userNameSport)
                    editSportsmanPassword?.setText(passeord)
                } else {

                }
            }

            override fun onFailure(call: Call<SportsmanData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}


