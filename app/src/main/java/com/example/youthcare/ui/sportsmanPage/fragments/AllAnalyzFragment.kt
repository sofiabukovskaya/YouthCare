package com.example.youthcare.ui.sportsmanPage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcare.R
import com.example.youthcare.repository.models.Analysis
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class AnalysisFire(
        val analysisType: String = "",
        val doctorName: String = "",
        val description: String = "",
        val result: String = ""
)
class AnalysisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class AllAnalyzFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var dataBaseReference: DatabaseReference


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var  rootView = inflater.inflate(R.layout.fragment_all_analyz, container, false)
        dataBaseReference = FirebaseDatabase.getInstance().getReference("analysis")
        recyclerView = rootView.findViewById(R.id.recycler1)


        val options = FirebaseRecyclerOptions.Builder<AnalysisFire>().setQuery(dataBaseReference, AnalysisFire::class.java).setLifecycleOwner(this).build()
        val adapter = object : FirebaseRecyclerAdapter<AnalysisFire, AnalysisViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisViewHolder {
               val view = LayoutInflater.from(activity).inflate(R.layout.analysis, parent, false)
                return  AnalysisViewHolder(view)
            }

            override fun onBindViewHolder(holder: AnalysisViewHolder, position:Int, model:AnalysisFire) {
                val numberAn:TextView = holder.itemView.findViewById(R.id.textView16)
                val typeAn: TextView = holder.itemView.findViewById(R.id.textView23)
                val docName: TextView = holder.itemView.findViewById(R.id.textView24)
                val descAn: TextView = holder.itemView.findViewById(R.id.textView25)
                val res: ImageView = holder.itemView.findViewById(R.id.imageView)

                numberAn.text = (position + 1).toString()
                typeAn.text = model.analysisType
                docName.text = model.doctorName
                descAn.text = model.description
                if(model.result.equals("true", true)) {
                    res.setImageResource(R.drawable.ic_happy)
                } else {
                    res.setImageResource(R.drawable.ic_sad)
                }
            }
        }

        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return  rootView
    }

}
