package com.example.youthcare.ui.doctorPage

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.youthcare.R
import com.example.youthcare.ui.sportsmanPage.LoginSportsman
import java.text.SimpleDateFormat
import java.util.*

class LoginDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_doctor)
        var selectDateDoctor: Button = findViewById(R.id.selectDateDoctor);
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
    fun goToLogin(view: View) {}
    fun goToSportsmanPage(view: View) {       val intent = Intent(this@LoginDoctor, LoginSportsman::class.java)
        startActivity(intent)}

    fun selectDateDoc(view: View) {

    }
}