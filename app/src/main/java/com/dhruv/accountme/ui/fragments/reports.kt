package com.dhruv.accountme.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.FragmentCalendarViewBinding
import com.dhruv.accountme.databinding.FragmentReportsBinding
import com.dhruv.accountme.ui.adapters.ReportsAdapter
import com.dhruv.accountme.ui.viewmodels.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class reports: Fragment(R.layout.fragment_reports), ReportsAdapter.MyOnClickListener {
    lateinit var binding: FragmentReportsBinding
    private val budgetViewModel : BudgetViewModel by viewModels()
    private lateinit var reportsAdapter:ReportsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        intializeRecyclerView()
        getAllEnteries()
    }

    private fun getAllEnteries() {
    budgetViewModel.allBudgetEnteries.observe(viewLifecycleOwner){
        reportsAdapter.differ.submitList(it)
    }
    }

    private fun intializeRecyclerView() {
        reportsAdapter = ReportsAdapter(this)
        binding.rcvReports.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportsAdapter
        }
    }

    override fun OnClick(position: Int) {
        val currentBudgetITem = reportsAdapter.differ.currentList[position]
        val bottomSheetBinding= updatebottomsheet(currentBudgetITem)
        bottomSheetBinding.show(requireActivity().supportFragmentManager, "Update Budget")
    }
}