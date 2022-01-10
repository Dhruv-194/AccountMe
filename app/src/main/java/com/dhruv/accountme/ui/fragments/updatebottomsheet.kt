package com.dhruv.accountme.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.UpdateBudgetBottomSheetBinding
import com.dhruv.accountme.ui.entities.Budget
import com.dhruv.accountme.ui.util.UtilityFunc.dateMilitoString
import com.dhruv.accountme.ui.viewmodels.BudgetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class updatebottomsheet(val currentBudgetItem: Budget) : BottomSheetDialogFragment(){

    lateinit var binding: UpdateBudgetBottomSheetBinding
    val budgetViewModel:BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_budget_bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UpdateBudgetBottomSheetBinding.bind(view)
        binding.updateAmount.setText(currentBudgetItem.amount.toString())
        binding.updatePerpose.setText(currentBudgetItem.purpose)
        val dateFormat = dateMilitoString(currentBudgetItem.date.toLong())
       /* fun dateMilitoString(dateInMili:Long):String{
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val cal = Calendar.getInstance()
            cal.timeInMillis = dateInMili
            return dateFormat.format(cal.time)
        }*/
        binding.updateDate.setText(dateFormat)
      /*  binding.updateBudgetEntry.setOnClickListener{
             val updatedAmount = binding.updateAmount.text.toString()
            val updatedPurpose = binding.updatePerpose.text.toString()

            budgetViewModel.updateBudget(
                updatedAmount.toFloat(),
                updatedPurpose,
                currentBudgetItem.id!!
            )
            dismiss()
        }*/
    }
}