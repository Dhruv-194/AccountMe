package com.dhruv.accountme.ui.repository

import com.dhruv.accountme.ui.db.BudgetDao
import com.dhruv.accountme.ui.entities.Budget
import java.time.temporal.TemporalAmount
import javax.inject.Inject

class Budgetrepo @Inject constructor(
    val budgetDao: BudgetDao
){
    suspend fun insertBudget(budget: Budget) = budgetDao.insertBudget(budget)

    fun getAllBudget() = budgetDao.getAllData()

    suspend fun updateBudget(amount: Float, purpose: String, id: Int)= budgetDao.updateBudget(amount, purpose, id)

    suspend fun deleteEntry(budget: Budget) = budgetDao.deleteEntry(budget)
}