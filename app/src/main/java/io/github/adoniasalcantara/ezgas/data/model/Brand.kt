package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Brand(val id: String, val name: String) : Parcelable