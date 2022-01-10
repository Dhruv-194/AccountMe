package com.dhruv.accountme.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.accountme.ui.entities.Budget
import com.dhruv.accountme.ui.repository.Budgetrepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    val budgetrepo : Budgetrepo
): ViewModel(){
    val allBudgetEnteries: LiveData<List<Budget>> = budgetrepo.getAllBudget()

    fun insertBudget(budget: Budget) =viewModelScope.launch {
        budgetrepo.insertBudget(budget)
    }

    fun updateBudget(amount:Float, purpose:String, id:Int) = viewModelScope.launch {
        budgetrepo.updateBudget(amount, purpose, id)
    }
}