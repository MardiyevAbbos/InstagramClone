package com.example.instagramclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.fragments.HomeFragment
import com.example.instagramclone.model.User
import com.google.android.material.imageview.ShapeableImageView

class StoryAdapter(var fragment: HomeFragment, var items: ArrayList<User>) : BaseAdapter(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_view, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoryViewHolder){
            holder.bind(items[position], position)
        }
    }

    inner class StoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private var iv_profile: ShapeableImageView
        private var fullname: TextView
        private var ll_background: FrameLayout
        private var iv_add: ShapeableImageView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            fullname = view.findViewById(R.id.tv_fullName)
            ll_background = view.findViewById(R.id.ll_background)
            iv_add = view.findViewById(R.id.iv_add)
        }

        fun bind(user: User, position: Int){
            if (position == 0) {
                ll_background.setBackgroundResource(R.drawable.shape_circle_border_white)
                iv_add.visibility = View.VISIBLE
            } else {
                iv_add.visibility = View.GONE
                ll_background.setBackgroundResource(R.drawable.shape_circle_border_instagram)
            }

            fullname.text = user.fullname
            Glide.with(fragment).load(user.userImg).placeholder(R.drawable.ic_person).error(R.drawable.ic_person).into(iv_profile)
        }

    }

}