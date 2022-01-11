package com.dhruv.accountme.ui.fragments

import android.content.ClipData
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.FragmentCalendarViewBinding
import com.dhruv.accountme.databinding.FragmentReportsBinding
import com.dhruv.accountme.ui.adapters.ReportsAdapter
import com.dhruv.accountme.ui.viewmodels.BudgetViewModel
import com.google.android.material.snackbar.Snackbar
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


        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val budget = reportsAdapter.differ.currentList[pos]

                budgetViewModel.deleteEntry(budget)
                Snackbar.make(view, "Item Deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("UNDO"){
                        budgetViewModel.insertBudget(budget)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rcvReports)
        }

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