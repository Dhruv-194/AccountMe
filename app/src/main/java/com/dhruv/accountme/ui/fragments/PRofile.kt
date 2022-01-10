package com.dhruv.accountme.ui.fragments

import android.app.Activity
import android.app.Instrumentation
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.FragmentCalendarViewBinding
import com.dhruv.accountme.databinding.FragmentProfileBinding
import com.dhruv.accountme.ui.entities.Profile
import com.dhruv.accountme.ui.util.Constant.PREFERENCE_NAME
import com.dhruv.accountme.ui.util.Constant.PREFERENCE_PROFILE_EXISTANCE_KEY
import com.dhruv.accountme.ui.util.InternalStoragePhoto
import com.dhruv.accountme.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URI
import javax.inject.Inject

@AndroidEntryPoint
class PRofile: Fragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    private val profileViewModel : ProfileViewModel by viewModels()
    private lateinit var filepath: Uri
    private lateinit var bitmap: Bitmap
    private lateinit var mypref : SharedPreferences

    private val takephoto = registerForActivityResult(ActivityResultContracts.GetContent()){ result ->
        filepath= result
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val source = ImageDecoder.createSource(requireContext().contentResolver!!, filepath)
            bitmap = ImageDecoder.decodeBitmap(source)
        }
            saveImagetoStorage("profile", bitmap)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        mypref = requireContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        if(mypref.contains(PREFERENCE_PROFILE_EXISTANCE_KEY)){
            changeVisibilityPostReg()
        }else{
            changeVisibilityForReg()
        }

        binding.profileImage.setOnClickListener{
            takephoto.launch("image/*")
        }


        profileViewModel.profileLiveData.observe(viewLifecycleOwner){
            profile-> if(profile!!.size>=1){
                viewLifecycleOwner.lifecycleScope.launch {
                    val listofImage = loadImagefromStorage()
                    for( i in listofImage){
                        if(i.name.contains("profile")){
                            Glide.with(requireContext()).load(i.bitmap).circleCrop().into(binding.profileImage)
                        }
                    }
                    binding.bankName.setText(profile!![0].bankname)
                    binding.initialBalance.setText(profile!![0].initalbalnce.toString())
                    binding.currentBalance.setText(profile!![0].currentbalnce.toString())
                    binding.materialCheckBox.isChecked = profile!![0].primarybank
                    binding.profileName.setText(profile!![0].name)
                    binding.profileEmail.setText(profile!![0].email)

                }
            }else {
                Toast.makeText( requireContext(), "Complete Profile", Toast.LENGTH_SHORT).show()
        }
        }


        binding.submitProfile.setOnClickListener {
            submitData(binding.profileName.text.toString(),
            binding.profileEmail.text.toString(),
            binding.bankName.text.toString(),
            binding.initialBalance.text.toString(),
            binding.materialCheckBox.isChecked )
        }
    }

    private fun submitData(profileName: String, profileEmail: String, bankName: String, intialBalance: String, checked: Boolean) {
        profileViewModel.insertprofiledata(
            Profile(
                name = profileName,
                email = profileEmail,
                profileimagepath = filepath.toString(),
                bankname = bankName,
                currentbalnce = intialBalance.toFloat(),
                initalbalnce = intialBalance.toFloat(),
                primarybank = checked
            )
        )
        val editor = mypref.edit()
        editor.putBoolean( PREFERENCE_PROFILE_EXISTANCE_KEY, true)
        editor.apply()
        findNavController().navigate(R.id.action_profile_to_calendarview)
    }


    private fun changeVisibilityForReg(){
        binding.submitProfile.visibility = View.VISIBLE
        binding.updateCurrentBalance.visibility = View.GONE
        binding.balanceLayout.visibility = View.GONE
    }

    private fun changeVisibilityPostReg(){
        binding.submitProfile.visibility = View.GONE
        binding.updateCurrentBalance.visibility = View.VISIBLE
        binding.balanceLayout.visibility = View.VISIBLE
    }

    private fun saveImagetoStorage(filename: String, bitmap: Bitmap) : Boolean {

        return try {
            requireContext().openFileOutput("$filename.jpg", MODE_PRIVATE).use {
                    outStream -> if (bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outStream)){
                throw IOException("Could not save BITMAP")
            }
            }
            true
        }
        catch (e: IOException){
            e.printStackTrace()
            false
        }
    }


    private suspend fun loadImagefromStorage():List<InternalStoragePhoto>
    {
        return withContext(Dispatchers.IO){
            val files = requireContext().filesDir.listFiles()
            files.filter {
                it.canRead() && it.isFile && it.name.endsWith("jpg")
            }.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                InternalStoragePhoto(it.name,bitmap)
            }
        }
    }
}