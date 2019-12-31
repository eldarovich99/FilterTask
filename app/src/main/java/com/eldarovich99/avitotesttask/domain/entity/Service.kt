package com.eldarovich99.avitotesttask.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Service(val title: String, var isSelected : Boolean) : Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this::class.java != this::class.java) return false
        other as Service
        return (this.title == other.title && this.isSelected == other.isSelected)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}