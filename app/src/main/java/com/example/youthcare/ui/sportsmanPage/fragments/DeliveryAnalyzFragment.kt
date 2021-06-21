package com.example.youthcare.ui.sportsmanPage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.youthcare.R
import com.example.youthcare.repository.models.Analysis
import com.google.firebase.database.FirebaseDatabase


class DeliveryAnalyzFragment : Fragment() {
    var spinnerDoctor: Spinner? = null
    var spinnerType: Spinner? = null
    val doctorNames = arrayOf("Vladimir Shevchenko", "Kirill Petrov", "Andrii Tsukevich")

    val type = arrayOf("Temperature", "HeartBeat", "BreathingRate", "SweatRate", "BMR", "BMI")

    var selectedDoctor :String? = null
    var selectedType :String? = null

    var editTextMeasure: EditText? = null
    var editTextWeight: EditText? = null
    var editHeight: EditText? = null

    var description: String? = null

   val  minTemperature: Double = 36.6;
    val maxTemperature: Double = 37.2;
    val minCalmHeartBeat:  Double = 60.0;
    val maxCalmHeartBeat: Double = 80.0;
    val minBreathingRate: Double = 16.0;
    val maxBreathingRate: Double = 20.0;
    val  minSweatRate: Double = 1.0;
    val maxSweatRate: Double = 1.5;

    val bmrMenFirstMetric = 66;
    val bmrMenSecondMetric = 13.7;
    val bmrMenThirdMetric = 5;
    val bmrMenFourthMetric = 6.8;
    val bmrWomenFirstMetric = 655;
    val bmrWomenSecondMetric = 9.6;
    val bmrWomenThirdMetric = 1.8;
    val bmrWomenFourthMetric = 4.7;

    val bmiUnderweightMetric = 5;
    val bmiHealthyWeightMetric = 85;
    val bmiRiskWeightMetric = 95;
    val underweightCategory = "Underweight";
    val healthyweightCategory = "Healthy/Normal weight";
    val riskCategory = "At risk of overweight";
    val overweightCategory = "Overweight";

    var result: String = "false"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var rootView =  inflater.inflate(R.layout.fragment_delivery_analyz, container, false)
        spinnerDoctor = rootView.findViewById<Spinner>(R.id.spinner_doctor)
        spinnerType = rootView.findViewById<Spinner>(R.id.spinner_type)

        editTextMeasure = rootView.findViewById<EditText>(R.id.editTextMeasure)
        editTextWeight = rootView.findViewById<EditText>(R.id.editTextWeight)
        editHeight = rootView.findViewById<EditText>(R.id.editHeight)

        spinnerDoctor?.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, doctorNames) } as SpinnerAdapter
        spinnerDoctor?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedDoctor = parent?.getItemAtPosition(position).toString()
            }
        }

        spinnerType?.adapter = activity?.applicationContext?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, type) } as SpinnerAdapter
        spinnerType?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedType = parent?.getItemAtPosition(position).toString()
            }
        }


        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button_passAn = view.findViewById<Button>(R.id.button_passAnalusis)

        button_passAn.setOnClickListener {
                sendData()
        }
    }

    fun sendData() {
        var measureAn = editTextMeasure?.text.toString()
        var heightAn = editHeight?.text.toString()
        var weightAn = editTextWeight?.text.toString()


        when(selectedType) {
            "Temperature" -> {
                if (measureAn.toDouble()>= minTemperature && measureAn.toDouble() <= maxTemperature) {
                    description = "The temperature result is good, it equals to  ${measureAn.toString()}. You're excepted to the competition."
                   result = "true"
                } else {
                    description = "The temperature result isn't good, it equals to  ${measureAn.toString()}. You're not excepted to the competition, please, contact your doctor."
                    result == "true"
                }
            }

            "HeartBeat" -> {
                if (measureAn.toInt() >= minCalmHeartBeat && measureAn.toInt() <= maxCalmHeartBeat) {
                    description = "The heartbeat result is good, it equals to  ${measureAn.toString()} per minute. You're excepted to the competition."
                    result == "true"
                } else {
                    description = "The heartbeat result isn't good, it equals to   ${measureAn.toString()} per minute. You're not excepted to the competition, please, contact your doctor"
                    result == "false"
                }
            }
            "BreathingRate" -> {
                if (measureAn.toInt() >= minBreathingRate && measureAn.toInt() <= maxBreathingRate) {
                    description = "The breathing rate result is good, it equals to ${measureAn.toString()} per minute. You're excepted to the competition."
                    result == "true"
                } else {
                    description = "The breathing rate isn't good, it equals to  ${measureAn.toString()} per minute. You're not excepted to the competition, please, contact your doctor."
                    result == "false"
                }
            }
            "SweatRate" -> {
                if (measureAn.toInt() >= minSweatRate && measureAn.toInt() <= maxSweatRate) {
                    description = "The sweating rate result is good, it equals to ${measureAn.toString()} L per hour. You're excepted to the competition."
                    result == "true"
                } else {
                    description = "The sweating rate isn't good, it equals to  ${measureAn.toString()} L per hour. You're not excepted to the competition, please, contact your doctor."
                    result == "false"
                }
            }
            "BMR" -> {

                val bmrFemaleCount: Double = bmrWomenFirstMetric + bmrWomenSecondMetric * weightAn.toInt() + bmrWomenThirdMetric * heightAn.toInt() + bmrWomenFourthMetric
                val res: Double = Math.round(bmrFemaleCount).toDouble()
                description = "Your BMR equals to ${res.toString()} kcals (per day)"
                result == "true"
            }
            "BMI" -> {
                val bmiCount: Double = weightAn.toDouble() / (heightAn.toDouble() * heightAn.toDouble())
                //var bmiCount = 67 / (1.81 * 1.81);
                //var bmiCount = 67 / (1.81 * 1.81);
                var res2: Double = Math.round(bmiCount as Double).toDouble()
                if (res2< bmiUnderweightMetric) {
                    description = "Your BMI result is ${res2.toString()} %. It belongs to the category: '" + underweightCategory.toString() + "'. You're not excepted to the competition, please, contact your doctor."
                    result ==  "false"
                } else if (res2 > bmiUnderweightMetric && res2< bmiHealthyWeightMetric) {
                    description = "Your BMI result is ${res2.toString()} %. It belongs to the category: '" + healthyweightCategory.toString() + "'. You're excepted to the competition."
                    result ==  "true"
                } else if (res2 > bmiHealthyWeightMetric && res2< bmiRiskWeightMetric) {
                    description = "Your BMI result is ${res2.toString()} %. It belongs to the category: '" + riskCategory.toString() + "'. You're not excepted to the competition, please, contact your doctor."
                    result == "false"
                } else if (res2 > bmiRiskWeightMetric) {
                    description = "Your BMI result is ${res2.toString()} %. It belongs to the category: '" + overweightCategory.toString() + "'. You're not excepted to the competition, please, contact your doctor."
                   result == "false"
                }
            }
        }

        val ref  = FirebaseDatabase.getInstance().getReference("analysis")
        val anId = ref.push().key

        val analysis = Analysis(anId.toString(), selectedDoctor.toString(), selectedType.toString(), measureAn.toDouble(), heightAn.toInt(), weightAn.toInt(), description.toString(), result.toString())
        ref.child(anId.toString()).setValue(analysis).addOnCompleteListener {
            Toast.makeText(activity, "Analysis successful added! :)", Toast.LENGTH_SHORT).show()
        }
    }
}