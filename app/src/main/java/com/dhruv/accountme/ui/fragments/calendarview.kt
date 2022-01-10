package com.dhruv.accountme.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.FragmentCalendarViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class calendarview: Fragment(R.layout.fragment_calendar_view) {
    lateinit var binding: FragmentCalendarViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarViewBinding.bind(view)
        setHasOptionsMenu(true)
        activity?.title= "Enter your budget"

        binding.calView.setOnDateChangeListener{ view, year, month, dayofMonth ->
        val selectedDate = "${dayofMonth}/${month+1}/${year}"

        val action = calendarviewDirections.actionCalendarviewToBudgetentry()
            .setSelectedDate(selectedDate)
            findNavController().navigate(action)
        }
    }
}