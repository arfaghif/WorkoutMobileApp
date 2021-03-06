package com.example.workout.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.workout.HistoryDetails
import com.example.workout.NewsDetails
import com.example.workout.R
import com.example.workout.WorkoutApplication
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var calendarView: CalendarView
    private lateinit var dateView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val current = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        calendarView = root.findViewById<CalendarView>(R.id.calender)
        dateView = root.findViewById<TextView>(R.id.dateView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = year.toString() + "-" + (month+1) + "-" +dayOfMonth
            dateView.text = date

            val format = SimpleDateFormat("yyyy-MM-dd")
            val selected: Date = Date((year-1900), month, dayOfMonth)
            val selectedDate = format.format(selected)
            println(selectedDate)


            val detailIntent = Intent(activity, HistoryDetails::class.java)

            detailIntent.putExtra("title", "History Logs");
            detailIntent.putExtra("date", date);
            detailIntent.putExtra("dateUnformatted", selectedDate);

            activity?.startActivity(detailIntent);
        }

        dateView.text = "Select a date"


        return root
    }
}