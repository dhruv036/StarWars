package io.dhruv.starwars.modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filter (
    var id:Int,
    var name: String,
    var isSelected: Boolean = false
):Parcelable