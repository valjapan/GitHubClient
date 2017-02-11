package com.valkyrie.nabeshimamac.githubclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.feras.mdv.MarkdownView

class ArticleActivity : AppCompatActivity() {

    companion object {
        private const val REPOSITORIES_EXTRA: String = "article"

        fun intent(context: Context, repository: Repository): Intent =
                Intent(context, ArticleActivity::class.java).putExtra(REPOSITORIES_EXTRA, repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        val repository: Repository = intent.getParcelableExtra(REPOSITORIES_EXTRA)

        val markdownView: MarkdownView = findViewById(R.id.markdownView) as MarkdownView

        // owner/repos
        val strList = repository.full_name.split('/')
        Log.d("LOGGER", "${strList[0]} : ${strList[1]}")
        // strlist[0] + " : " + strlist[1] -> "${strlist[0]} : ${strlist[1]}"

        GithubClient.service.getReadMe(strList[0], strList[1])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ x ->
                    markdownView.loadMarkdownFile(x.download_url)
                }, { error ->
                    Log.e("ERROR", error.message)
                })
    }
}
