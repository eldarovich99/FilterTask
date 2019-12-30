package com.eldarovich99.avitotesttask.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Service(val title: String, var isSelected : Boolean) : Parcelable