package com.example.my_pfe

import android.os.Parcel
import android.os.Parcelable

data class AnnonceItem(
    var nom: String ?= null,
    var code_entreprise: String?= null,
    var email: String ?= null,
    val descriptionAnnonce: String ?= null,
    val nomAnnonce: String?= null,
    val departement : String?= null,
    val adress : String?= null,
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
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(code_entreprise)
        parcel.writeString(email)
        parcel.writeString(descriptionAnnonce)
        parcel.writeString(nomAnnonce)
        parcel.writeString(departement)
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

