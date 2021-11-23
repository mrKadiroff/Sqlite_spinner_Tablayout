package com.example.home_1.models

import android.os.Parcel
import android.os.Parcelable

class Contact() : Parcelable {

    var id: Int? = null
    var name: String? = null
    var phoneNumber: String? = null
    var kategoriya: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        phoneNumber = parcel.readString()
        kategoriya = parcel.readString()
    }

    constructor(name: String?, phoneNumber: String?, kategoriya: String?) : this() {
        this.name = name
        this.phoneNumber = phoneNumber
        this.kategoriya = kategoriya
    }

    constructor(id: Int?, name: String?, phoneNumber: String?, kategoriya: String?) : this() {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
        this.kategoriya = kategoriya
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeString(kategoriya)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }


}