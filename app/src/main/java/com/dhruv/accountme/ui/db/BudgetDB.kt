package com.dhruv.accountme.ui.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dhruv.accountme.ui.entities.Budget
import com.dhruv.accountme.ui.entities.Profile

@Database(
    entities = [Budget::class, Profile::class],
    version = 1
)
abstract class BudgetDB : RoomDatabase(){
    abstract fun getBudgetDao(): BudgetDao
    abstract fun getProfileDao(): ProfileDao

}