package com.dhruv.accountme.ui.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dhruv.accountme.ui.db.BudgetDB
import com.dhruv.accountme.ui.entities.Budget
import com.dhruv.accountme.ui.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesbudgetdb(
       @ApplicationContext context: Context
    )= Room.databaseBuilder(
        context,
        BudgetDB::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesprofiledao(db: BudgetDB) = db.getProfileDao()

    @Provides
    @Singleton
    fun providesbudgetdao(db: BudgetDB) = db.getBudgetDao()
}