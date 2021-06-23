package com.example.youthcare.ui.doctorPage

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.youthcare.ApiInterface
import com.example.youthcare.R
import com.example.youthcare.RetrofitInstance
import com.example.youthcare.api.SessionManager
import com.example.youthcare.repository.models.LoginResponse
import com.example.youthcare.repository.models.SignInBody
import com.example.youthcare.ui.adminPage.AdminActivity
import com.example.youthcare.ui.sportsmanPage.LoginSportsman
import com.example.youthcare.ui.sportsmanPage.MainPageSportsman
import com.example.youthcare.unSafeOkHttpClient
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*

class LoginDoctor : AppCompatActivity() {
    lateinit var textInputNameDoctor: TextInputLayout
    lateinit var textInputSurnameDoctor: TextInputLayout
    lateinit var textInputUsernameDoctor: TextInputLayout
    lateinit var textInputEmailDoctor: TextInputLayout
    lateinit var textInputPasswordDoctor: TextInputLayout
    lateinit var selectDate: TextView
    lateinit var logInButton: Button
    lateinit var loginText: TextView
    private var loginModeActive = false

    lateinit var selectDateDoctor: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_doctor)
        textInputNameDoctor = findViewById(R.id.textInputNameDoctor)
        textInputSurnameDoctor = findViewById(R.id.textInputSurnameDoctor)
        textInputUsernameDoctor = findViewById(R.id.usernameDoctor)
        textInputEmailDoctor = findViewById(R.id.emailDoctor)
        textInputPasswordDoctor = findViewById(R.id.textInputPasswordDoctor)
        selectDate = findViewById(R.id.labelSelectDate)
        logInButton = findViewById(R.id.loginSignUpButtonPas)
        loginText = findViewById(R.id.TextViewSignUpPas)
        selectDateDoctor = findViewById(R.id.selectDateDoctor)
        val retIn = RetrofitInstance.getRetrofitInstance(applicationContext).create(ApiInterface::class.java)
        var sessionManager = SessionManager(this)

        logInButton.setOnClickListener {
            retIn.signin(SignInBody(textInputUsernameDoctor.editText?.text.toString(), textInputPasswordDoctor.editText?.text.toString())).enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    unSafeOkHttpClient()
                    Toast.makeText(
                        this@LoginDoctor,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                    val loginRespone = response.body()
                    if (response.code() == 200 && !textInputUsernameDoctor.editText?.text.toString().equals("admin123", true) && !textInputPasswordDoctor.editText?.text.toString().equals("abcdeF!12",true)) {
                        sessionManager.saveAuthToken(loginRespone!!.authToken)
                        Toast.makeText(this@LoginDoctor, "Login success! Hello ${textInputUsernameDoctor.editText?.text.toString()}", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginDoctor, MainPageDoctor::class.java)
                        startActivity(intent)
                    } else if (response.code() == 200 && textInputUsernameDoctor.editText?.text.toString().equals("admin123", true) &&textInputPasswordDoctor.editText?.text.toString().equals("abcdeF!12",true)){
                        val intent = Intent(this@LoginDoctor, AdminActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginDoctor, "Login failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        selectDateDoctor.setOnClickListener {
            val myCalendar = Calendar.getInstance();
            val day = myCalendar.get(Calendar.DAY_OF_MONTH);
            val year = myCalendar.get(Calendar.YEAR);
            val month = myCalendar.get(Calendar.MONTH);
            val dpc =  DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDay = "$year/${month+1}/$dayOfMonth"
                var selectedDate = findViewById<TextView>(R.id.selectedDateDoc);
                selectedDate.setText(selectedDay)
                val sdf = SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDay)
            }, year, month, day)

            dpc.datePicker.maxDate = Date().time - 86400000
            dpc.show()
        }
    }

    fun loginSignUp(view: View) {}
    fun goToLogin(view: View) {
        if (loginModeActive) {
            loginModeActive = false
            logInButton!!.text = "Sign Up"
            loginText!!.text = "Tap to Log In"
            textInputEmailDoctor?.visibility = View.VISIBLE
            textInputNameDoctor?.visibility = View.VISIBLE
            textInputSurnameDoctor?.visibility = View.VISIBLE
            selectDate?.visibility = View.VISIBLE
            selectDateDoctor?.visibility = View.VISIBLE
        } else {
            loginModeActive = true
            logInButton!!.text = "Log in"
            loginText!!.text = "Tap Sign Up"
            textInputEmailDoctor?.visibility = View.GONE
            textInputNameDoctor?.visibility = View.GONE
            textInputSurnameDoctor?.visibility = View.GONE
            selectDate?.visibility = View.GONE
            selectDateDoctor?.visibility = View.GONE
        }
    }

    fun goToSportsmanPage(view: View) {
        val intent = Intent(this@LoginDoctor, LoginSportsman::class.java)
        startActivity(intent)}

    fun selectDateDoc(view: View) {

    }
}