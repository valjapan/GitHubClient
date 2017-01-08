package com.valkyrie.nabeshimamac.githubclient

import android.os.Parcel
import android.os.Parcelable

data class Repositories(val id: Int,
                   val name: String,
                   val description: String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Repositories> = object : Parcelable.Creator<Repositories> {
            override fun createFromParcel(source: Parcel): Repositories = source.run {
                Repositories(readInt(), readString(), readString())
            }

            override fun newArray(size: Int): Array<Repositories?> = kotlin.arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeInt(id)
            writeString(name)
            writeString(description)
        }
    }
}
