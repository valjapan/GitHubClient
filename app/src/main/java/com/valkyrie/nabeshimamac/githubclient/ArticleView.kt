package com.valkyrie.nabeshimamac.githubclient

import android.content.Context
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class ArticleView : FrameLayout {
    constructor(context: Context?) : super(context)

    constructor(context: Context?,
                attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        View.inflate(context, R.layout.content_article_view, this)
    }

    fun <T : View> View.bindView(@IdRes id: Int): Lazy<T> = lazy {
        (findViewById(id) as T)
    }

//    val idTextView: TextView by bindView(R.id.id_text_view)

    val nameTextView: TextView by bindView(R.id.user_name_text_view)

    val descriptionTextView: TextView by bindView(R.id.description_text_view)

    val urlTextView: TextView by bindView(R.id.url_text_view)

//    init {
//        LayoutInflater.from(context).inflate(R.layout.content_article_view, this)
//        profileImageView = findViewById(R.id.profile_image_view) as ImageView
//        titleTextView = findViewById(R.id.title_text_view) as TextView
//        userNameTextView = findViewById(R.id.user_name_text_view) as TextView
//    }

    fun setArticle(article: Repository) {
//        idTextView.text = article.id.toString()
        nameTextView.text = article.name
        descriptionTextView.text = article.description
        urlTextView.text = article.full_name
    }
}
