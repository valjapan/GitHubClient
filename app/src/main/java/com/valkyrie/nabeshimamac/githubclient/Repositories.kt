package com.valkyrie.nabeshimamac.githubclient

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.Delegates

data class Repositories(val id: Int,
                        val name: String,
                        val description: String,
                        val full_name: String) : Parcelable {


    private var gitHubApi: GithubAPI by Delegates.notNull()
    private var searchEditText: EditText by Delegates.notNull()
    private var listAdapter: ArticleAdapter by Delegates.notNull()


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Repositories> = object : Parcelable.Creator<Repositories> {
            override fun createFromParcel(source: Parcel): Repositories = source.run {
                Repositories(readInt(), readString(), readString(), readString())
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
            writeString(full_name)
        }
    }

    private fun search(text: String) {
        gitHubApi.searchRepositories(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ x ->
                    searchEditText.text.clear()
                    listAdapter.articles = x
                    listAdapter.notifyDataSetChanged()
                }, { error ->
                    Log.e("ERROR", error.message)
                })
    }

}
