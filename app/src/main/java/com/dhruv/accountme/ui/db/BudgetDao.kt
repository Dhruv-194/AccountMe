package com.dhruv.accountme.ui.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhruv.accountme.ui.entities.Budget

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM budget ORDER BY id DESC")
    fun getAllData():LiveData<List<Budget>>

    @Query("UPDATE budget SET amount = :amount, purpose = :purpose, id = :id")
    suspend fun updateBudget(amount:Float, purpose:String, id:Int)
    
    @Delete
    suspend fun deleteEntry(budget: Budget)
}