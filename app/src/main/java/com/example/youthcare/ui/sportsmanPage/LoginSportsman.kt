package com.example.youthcare.ui.sportsmanPage

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.youthcare.*
import com.example.youthcare.api.SessionManager
import com.example.youthcare.repository.models.LoginResponse
import com.example.youthcare.repository.models.SignInBody
import com.example.youthcare.ui.adminPage.AdminActivity
import com.example.youthcare.ui.doctorPage.LoginDoctor
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*


class LoginSportsman : AppCompatActivity() {
    val TAG = "LoginSportsman"

    private var textInputEmail: TextInputLayout? = null
    lateinit var textInputName: TextInputLayout
    private var textInputSurname: TextInputLayout? = null
    lateinit var textInputPassword: TextInputLayout
    lateinit var textInputUserName: TextInputLayout

    lateinit var loginSignUpButton: Button
        lateinit var selectDate: Button
    private var TextViewSignUp: TextView? = null
    private var TextViewSelectDate: TextView? = null
    private var loginModeActive = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginsportsman_activity)
        var sessionManager = SessionManager(this)
        textInputEmail = findViewById(R.id.emailSportsman);
        textInputName = findViewById(R.id.textInputNameSportsman);
        textInputSurname = findViewById(R.id.textInputSurnameSportsman);
        textInputPassword = findViewById(R.id.textInputPasswordSportsman);
        textInputUserName = findViewById(R.id.username);
        TextViewSelectDate = findViewById(R.id.selecteddATE);
        selectDate = findViewById(R.id.buttonSelectedDate);
        loginSignUpButton = findViewById(R.id.loginSignUpButtonPas);
        TextViewSignUp = findViewById(R.id.loginSport);



        selectDate.setOnClickListener {
            val myCalendar = Calendar.getInstance();
            val day = myCalendar.get(Calendar.DAY_OF_MONTH);
            val year = myCalendar.get(Calendar.YEAR);
            val month = myCalendar.get(Calendar.MONTH);
            val dpc =  DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDay = "$year/${month+1}/$dayOfMonth"
                var selectedDate = findViewById<TextView>(R.id.selectedDateSportsman);
                selectedDate.setText(selectedDay)
                val sdf = SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDay)
            }, year, month, day)

            dpc.datePicker.maxDate = Date().time - 86400000
            dpc.show()
        }

        loginSignUpButton.setOnClickListener {
            validateEmail()
            validateSurname()
            validateSurname()
            validatePassword()
            validateUserName()
            unSafeOkHttpClient()
            trustAllCertificates()
            HttpsTrustManager.allowAllSSL()
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            unSafeOkHttpClient()
            retIn.signin(SignInBody(textInputUserName.editText?.text.toString(), textInputPassword.editText?.text.toString())).enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    unSafeOkHttpClient()
                    Toast.makeText(
                            this@LoginSportsman,
                            t.message,
                            Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                    val loginRespone = response.body()
                    if (response.code() == 200 && !textInputUserName.editText?.text.toString().equals("admin123", true) && !textInputPassword.editText?.text.toString().equals("abcdeF!12",true)) {
                        sessionManager.saveAuthToken(loginRespone!!.authToken)
//                        dsds()
                        Toast.makeText(this@LoginSportsman, "Login success! Hello ${textInputUserName.editText?.text.toString()}", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginSportsman, MainPageSportsman::class.java)
                        startActivity(intent)
                    } else if (response.code() == 200 && textInputUserName.editText?.text.toString().equals("admin123", true) &&textInputPassword.editText?.text.toString().equals("abcdeF!12",true)){
                        val intent = Intent(this@LoginSportsman, AdminActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginSportsman, "Login failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            })


        }

    }

//    fun dsds() {
//        var sessionManager = SessionManager(this)
//        HttpsTrustManager.allowAllSSL()
//        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
//        unSafeOkHttpClient()
//        retIn.getInfoSportsman(accessToken = "${sessionManager.fetchAuthToken()}").enqueue(object : retrofit2.Callback<SportsmanDataResponse> {
//            override fun onResponse(call: Call<SportsmanDataResponse>, response: Response<SportsmanDataResponse>) {
//                val sportsmanResponse = response.body()
//                if (response.code() == 200) {
//                    Log.d("sae", "onResponse:  name ${response.body()?.sportsManUser?.name.toString()}  email ${response.body()?.sportsManUser?.email.toString()}")
//                } else {
//
//                }
//            }
//
//            override fun onFailure(call: Call<SportsmanDataResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

    private fun validateEmail(): Boolean {
        val inputEmail = textInputEmail!!.editText!!.text.toString().trim { it <= ' ' }
        return if (inputEmail.isEmpty()) {
            textInputEmail!!.error = "Please input your email"
            false
        } else {
            textInputEmail!!.error = ""
            true
        }
    }

    private fun validateName(): Boolean {
        val inputName = textInputName!!.editText!!.text.toString().trim { it <= ' ' }
        return if (inputName.isEmpty()) {
            textInputName!!.error = "Please input your name"
            false
        } else if (inputName.length > 15) {
            textInputName!!.error = "Name length have to be less than 15"
            false
        } else {
            textInputName!!.error = ""
            true
        }
    }

    private fun validateSurname(): Boolean {
        val inputSurname = textInputUserName!!.editText!!.text.toString().trim { it <= ' ' }
        return if (inputSurname.isEmpty()) {
            textInputName!!.error = "Please input your surname"
            false
        } else if (inputSurname.length > 15) {
            textInputName!!.error = "Surname length have to be less than 15"
            false
        } else {
            textInputName!!.error = ""
            true
        }
    }

    private fun validateUserName(): Boolean {
        val inputUserName = textInputUserName!!.editText!!.text.toString().trim { it <= ' ' }
        return if (inputUserName.isEmpty()) {
            textInputName!!.error = "Please input your surname"
            false
        } else if (inputUserName.length > 15) {
            textInputName!!.error = "Surname length have to be less than 15"
            false
        } else {
            textInputName!!.error = ""
            true
        }
    }

    private fun validatePassword(): Boolean {
        val inputPassword = textInputPassword!!.editText!!.text.toString().trim { it <= ' ' }
        return if (inputPassword.isEmpty()) {
            textInputPassword!!.error = "Please input your password"
            false
        } else if (inputPassword.length < 7) {
            textInputPassword!!.error = "Password have to be than than 6"
            false
        }  else {
            textInputPassword!!.error = ""
            true
        }
    }




    fun goToLogin(view: View) {
        if (loginModeActive) {
            loginModeActive = false
            loginSignUpButton!!.text = "Sign Up"
            TextViewSignUp!!.text = "Tap to Log In"
            textInputEmail?.visibility = View.VISIBLE
            textInputName?.visibility = View.VISIBLE
            textInputSurname?.visibility = View.VISIBLE
            TextViewSelectDate?.visibility = View.VISIBLE
            selectDate?.visibility = View.VISIBLE
        } else {
            loginModeActive = true
            loginSignUpButton!!.text = "Log in"
            TextViewSignUp!!.text = "Tap Sign Up"
            textInputEmail?.visibility = View.GONE
            textInputName?.visibility = View.GONE
            textInputSurname?.visibility = View.GONE
            TextViewSelectDate?.visibility = View.GONE
            selectDate?.visibility = View.GONE
        }
    }
    fun goToDoctorReg(view: View) {
        val intent = Intent(this@LoginSportsman, LoginDoctor::class.java)
        startActivity(intent)
    }

    fun selectDateSport(view: View) {

    }
}

//private fun <T> Call<T>.enqueue(callback: Callback) {
//
//}


