package com.dhruv.accountme.ui.entities

import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile"
)
data class Profile(
@PrimaryKey(autoGenerate = true )
var id: Int? =null,
val name: String,
val email: String,
val profileimagepath: String,
val bankname: String,
val currentbalnce: Float,
val initalbalnce: Float,
val primarybank: Boolean
) {
}