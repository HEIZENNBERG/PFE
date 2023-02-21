package com.example.my_pfe

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.tasks.Task
import java.io.Serializable

data class Entreprise(
    var nom: String ?= null,
    var code_entreprise: String?= null,
    var email: String ?= null,
    var adress: String?= null,
    var numero: String?= null,
    var categorie: String?= null,
    var description: String?= null,
    var url: String?= null

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(code_entreprise)
        parcel.writeString(email)
        parcel.writeString(adress)
        parcel.writeString(numero)
        parcel.writeString(categorie)
        parcel.writeString(description)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Entreprise> {
        override fun createFromParcel(parcel: Parcel): Entreprise {
            return Entreprise(parcel)
        }

        override fun newArray(size: Int): Array<Entreprise?> {
            return arrayOfNulls(size)
        }
    }
}
