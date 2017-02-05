package com.valkyrie.nabeshimamac.githubclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private var listAdapter: ArticleAdapter by Delegates.notNull()

    private var mAnimation: Animation by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = ArticleAdapter(applicationContext).apply {
            listener = object : ArticleAdapter.OnItemClickListener {
                override fun onItemClick(repositories: Repositories) {
                    ArticleActivity.intent(this@MainActivity, repositories).let {
                        startActivity(it)
                    }
                }
            }
        }

        //mAnimation = AnimationUtils.loadAnimation(context, R.anim.item_enter_anim)
        //Java
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.item_enter_anim)

        GithubClient.service.newRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ x ->
                    listAdapter.articles = x
                    listAdapter.notifyDataSetChanged()
                }, { error ->
                    Log.e("ERROR", error.message)
                })

        val listView: RecyclerView = findViewById(R.id.list_view) as RecyclerView

        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = listAdapter
    }

    private fun setUsersToListView(names: List<String>) {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        adapter.addAll(names)

        runOnUiThread {
            val usersList = findViewById(R.id.list_view) as ListView
            usersList.setAdapter(adapter)
        }
    }
}
