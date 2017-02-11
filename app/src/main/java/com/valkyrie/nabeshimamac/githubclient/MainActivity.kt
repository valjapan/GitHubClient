package com.valkyrie.nabeshimamac.githubclient

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private var listAdapter: ArticleAdapter by Delegates.notNull()
    private var mAnimation: Animation by Delegates.notNull()
    private var spinner: Spinner by Delegates.notNull()
    private var searchEditText: EditText by Delegates.notNull()
    private var searchButton: ImageButton by Delegates.notNull()
    var repository: Repository by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = ArticleAdapter(applicationContext).apply {
            listener = object : ArticleAdapter.OnItemClickListener {
                override fun onItemClick(repository: Repository) {
                    ArticleActivity.intent(this@MainActivity, repository).let {
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
        searchEditText = findViewById(R.id.search_edit_text) as EditText

        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = listAdapter

        spinner = findViewById(R.id.code_spinner) as Spinner

        val list: List<String> = mutableListOf(
                "C", "C++", "CSS", "Go", "HTML", "Java", "JavaScript", "Kotlin",
                "Objective-C", "PHP", "Python", "Ruby", "Scala", "Shell", "Swift")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        searchEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                //キーボードを非表示
                (this@MainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromInputMethod(searchEditText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                // Repositories.search(searchEditText.text.toString())
                true
            }
            false
        }
        searchButton = findViewById(R.id.search_button) as ImageButton
        searchButton.setOnClickListener {
            search(searchEditText.text.toString(), "Java", "stars")
        }

    }

    private fun setUsersToListView(names: List<String>) {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        adapter.addAll(names)

        runOnUiThread {
            val usersList = findViewById(R.id.list_view) as ListView
            usersList.setAdapter(adapter)
        }
    }

    private fun search(searchWord: String, language: String, sort: String) {

        val q: String = "${searchWord}language:${language}"

        Log.d("MAINACTIVITY", q)

        GithubClient.service.searchRepositories(q, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ x ->
                    searchEditText.text.clear()
                    listAdapter.articles = x.items
                    listAdapter.notifyDataSetChanged()

                    Log.d("MAINACTIVITY", x.items.toString())
                }, { error ->
                    Log.e("ERROR", error.message)
                })
    }
}
