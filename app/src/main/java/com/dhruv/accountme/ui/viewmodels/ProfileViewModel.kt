package com.dhruv.accountme.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.accountme.ui.entities.Profile
import com.dhruv.accountme.ui.repository.Profilerepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val profileRepo : Profilerepo
) : ViewModel(){
    val profileLiveData:LiveData<List<Profile>> = profileRepo.getProfile()

    fun insertprofiledata(profile : Profile) = viewModelScope.launch {
        profileRepo.insertProfiledata(profile)
    }

    fun updateCurrentbal(revisedBalance:Float) = viewModelScope.launch {
        profileRepo.updateCurrentbal(revisedBalance)
    }
}