package com.valkyrie.nabeshimamac.githubclient

import android.os.Parcel
import android.os.Parcelable

data class Repository(val id: Int,
                      val name: String,
                      val description: String,
                      val full_name: String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Repository> = object : Parcelable.Creator<Repository> {
            override fun createFromParcel(source: Parcel): Repository = source.run {
                Repository(readInt(), readString(), readString(), readString())
            }

            override fun newArray(size: Int): Array<Repository?> = kotlin.arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeInt(id)
            writeString(name)
            writeString(description)
            writeString(full_name)
        }
    }

}
