package com.dhruv.accountme.ui.util

import java.text.SimpleDateFormat
import java.util.*

object UtilityFunc {
    fun dateStringtoMili(dateInString:String):Long{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(dateInString)
        return date.time
    }
    fun dateMilitoString(dateInMili:Long):String{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        cal.timeInMillis = dateInMili
        return dateFormat.format(cal.time)
    }
}