package com.asykurkhamid.coroutine_http.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TodoModel (
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
):Parcelable