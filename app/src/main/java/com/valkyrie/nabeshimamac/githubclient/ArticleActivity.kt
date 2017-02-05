package com.valkyrie.nabeshimamac.githubclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

class ArticleActivity : AppCompatActivity() {

    companion object {
        private const val REPOSITORIES_EXTRA: String = "article"

        fun intent(context: Context, repositories: Repositories): Intent =
                Intent(context, ArticleActivity::class.java).putExtra(REPOSITORIES_EXTRA, repositories)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        val webView = findViewById(R.id.web_view) as WebView
        val repositories: Repositories = intent.getParcelableExtra(REPOSITORIES_EXTRA)


        webView.loadUrl("https://github.com/${repositories.full_name}")

    }
}
