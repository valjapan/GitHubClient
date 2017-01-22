package com.valkyrie.nabeshimamac.githubclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by NabeshimaMAC on 2017/01/22.
 */

class BeautifulListViewAdapter(private val mContext: Context, private val mList: List<String>) : BaseAdapter() {

    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.content_article_view, parent, false)
            holder = ViewHolder(convertView)

            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.idTextView.text = getItem(position).toString()
        holder.nameTextView.text = getItem(position).toString()
        holder.descriptionTextView.text = getItem(position).toString()


        convertView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_enter_anim))

        return convertView
    }

    class ViewHolder(view: View) {


        internal var idTextView: TextView

        internal var nameTextView: TextView

        internal var descriptionTextView: TextView


        init {
            idTextView = view.findViewById(R.id.id_text_view) as TextView
            nameTextView = view.findViewById(R.id.user_name_text_view) as TextView
            descriptionTextView = view.findViewById(R.id.description_text_view) as TextView

        }
    }
}