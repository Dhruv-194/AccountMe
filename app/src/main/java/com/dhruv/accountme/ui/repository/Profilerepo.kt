package com.dhruv.accountme.ui.repository

import com.dhruv.accountme.ui.db.ProfileDao
import com.dhruv.accountme.ui.entities.Profile
import javax.inject.Inject

class Profilerepo @Inject constructor(
    val profileDao: ProfileDao
) {
    fun getProfile() = profileDao.getProfileData()

    suspend fun insertProfiledata(profile:Profile) = profileDao.insertProfileData(profile)
    suspend fun updateCurrentbal(revisedBalance:Float) = profileDao.updateCurrentbal(revisedBalance)
}