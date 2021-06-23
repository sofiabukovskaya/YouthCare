package com.example.youthcare.ui.sportsmanPage

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.youthcare.*
import com.example.youthcare.api.SessionManager
import com.example.youthcare.repository.models.*
import com.example.youthcare.ui.adminPage.AdminActivity
import com.example.youthcare.ui.doctorPage.LoginDoctor
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.text.Typography.section


class LoginSportsman : AppCompatActivity() {
    val TAG = "LoginSportsman"

    private var textInputEmail: TextInputLayout? = null
    lateinit var textInputName: TextInputLayout
    private var textInputSurname: TextInputLayout? = null
    lateinit var textInputPassword: TextInputLayout
    lateinit var textInputUserName: TextInputLayout
    lateinit var selectAsectionText: TextView
    lateinit var loginSignUpButton: Button
        lateinit var selectDate: Button
    private var TextViewSignUp: TextView? = null
    private var TextViewSelectDate: TextView? = null
    private var loginModeActive = false
    var gender = arrayOf("Female", "Male");
    var spinner: Spinner? = null

    var spinner_sections: Spinner? = null

    @RequiresApi(Build.VERSION_CODES.O)
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
        selectAsectionText = findViewById(R.id.selectASectionText)
        TextViewSignUp = findViewById(R.id.loginSport);
        spinner_sections = findViewById(R.id.spinner_section);
        var selectedSectionText:String = ""
        var selectedGender:String = ""
        var selectedDate = findViewById<TextView>(R.id.selectedDateSportsman);
        var sections : ArrayList<Section>
        var sectionList : ArrayList<String> = ArrayList<String>()
        val retIn = RetrofitInstance.getRetrofitInstance(applicationContext).create(ApiInterface::class.java)
        val retrofitSection = RetrofitInstance.getRetrofitInstance(applicationContext).create(ApiInterface::class.java);
        retrofitSection.getSections().enqueue(object : retrofit2.Callback<ArrayList<Section>> {
            override fun onResponse(
                call: Call<ArrayList<Section>>,
                response: Response<ArrayList<Section>>
            ) {
                sections = response.body()!!
                for (section in sections) {
                    sectionList.add(section.name)
                }
                val spinnerAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, sectionList)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_sections?.adapter = spinnerAdapter

            }

            override fun onFailure(call: Call<ArrayList<Section>>, t: Throwable) {}
        })

        spinner_sections?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSectionText = sectionList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spinner = findViewById(R.id.spinner_sample)
        val adapterGender = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, gender)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapterGender
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGender = gender[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        selectDate.setOnClickListener {
            val myCalendar = Calendar.getInstance();
            val day = myCalendar.get(Calendar.DAY_OF_MONTH);
            val year = myCalendar.get(Calendar.YEAR);
            val month = myCalendar.get(Calendar.MONTH);
            val dpc =  DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDay = "$year/${month+1}/$dayOfMonth"
                selectedDate.setText(selectedDay)
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

            unSafeOkHttpClient()

            if(!loginModeActive) {
                retIn.registerUser(UserBody(textInputName.editText?.text.toString(),
                    textInputSurname?.editText?.text.toString(),selectedGender,
                    textInputUserName.editText?.text.toString(), LocalDate.parse(selectedDate?.text.toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                textInputEmail?.editText?.text.toString(),textInputPassword.editText?.text.toString(),
                selectedSectionText, "Sportsman")).enqueue(object : retrofit2.Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Toast.makeText(
                            this@LoginSportsman,
                           "You are succesfull register",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            this@LoginSportsman,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            } else{
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

    }

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
            spinner?.visibility =  View.GONE
            spinner_sections?.visibility = View.GONE
            TextViewSignUp!!.text = "Tap Sign Up"
            textInputEmail?.visibility = View.GONE
            textInputName?.visibility = View.GONE
            textInputSurname?.visibility = View.GONE
            TextViewSelectDate?.visibility = View.GONE
            selectAsectionText?.visibility = View.GONE
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


