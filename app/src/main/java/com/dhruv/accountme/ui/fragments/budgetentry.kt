package com.dhruv.accountme.ui.fragments

import android.os.BugreportManager
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.FragmentBudgetEntryBinding
import com.dhruv.accountme.databinding.FragmentCalendarViewBinding
import com.dhruv.accountme.ui.entities.Budget
import com.dhruv.accountme.ui.util.UtilityFunc.dateStringtoMili
import com.dhruv.accountme.ui.viewmodels.BudgetViewModel
import com.dhruv.accountme.ui.viewmodels.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonDisposableHandle.parent
@AndroidEntryPoint
class budgetentry: Fragment(R.layout.fragment_budget_entry) {
    lateinit var binding: FragmentBudgetEntryBinding
    val args: budgetentryArgs by navArgs()
    private val profileViewModel : ProfileViewModel by viewModels()
    private var currentBalance:Float =0.0f
    private lateinit var bankName: String
    private lateinit var debitcredit : String
    private lateinit var remainingbalance: String
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBudgetEntryBinding.bind(view)
        activity?.title = "Enter budget for: ${args.selectedDate}"

        getProfileDate()
        setSpinnerForDebitCredit()

        binding.bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bankName = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                bankName = "NONE"
            }
        }

        binding.debitCreditSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               debitcredit = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            debitcredit="Debit"
            }
        }
        binding.editAmount.addTextChangedListener{ it->
            it.let {
                val enteredAmt = it.toString()

                val temp = if(debitcredit.equals("Debit")){
                    (currentBalance-enteredAmt.toFloat())
                }else{
                    (currentBalance+enteredAmt.toFloat())
                }
                remainingbalance = temp.toString()
                binding.remainingBalance.text = remainingbalance
            }
        }

        binding.submitBudgetEntry.setOnClickListener{
            val amount = binding.editAmount.text.toString()
            val purpose = binding.editPurpose.text.toString()
            val date = dateStringtoMili(args.selectedDate!!).toString()
            val revisedCurrent = remainingbalance

            submitBudegtEntryDb(
                bankName,
                debitcredit,
                amount,
                purpose,
                date,
                revisedCurrent
            )

        }
    }

    private fun submitBudegtEntryDb(bankName: String, debitcredit: String, amount: String, purpose: String, date: String, revisedCurrent: String) {
        var amounttoInsert = amount.toFloat()
        if(debitcredit.equals("Debit")){
            amounttoInsert = -1*amounttoInsert
        }
        budgetViewModel.insertBudget(
            Budget(
                date = date,
                bankname = bankName,
                amount = amounttoInsert,
                purpose = purpose,
                creditdebit = debitcredit
            )
        )
        profileViewModel.updateCurrentbal(revisedBalance = revisedCurrent.toFloat())
        Snackbar.make(binding.budgetEntryConstraint, "Entry added", Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_budgetentry_to_calendarview)
    }

    private fun setSpinnerForDebitCredit() {
        val debitcreditArray = ArrayList<String>()
        debitcreditArray.add("Debit")
        debitcreditArray.add("Credit")
        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,debitcreditArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.debitCreditSpinner.adapter = arrayAdapter
    }

    private fun getProfileDate() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner){
            val bankNames = ArrayList<String>()
            bankNames.add(it[0].bankname)
            currentBalance = it[0].currentbalnce
            binding.remainingBalance.text = it[0].currentbalnce.toString()
            val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,bankNames)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.bankSpinner.adapter = arrayAdapter
        }
    }
}