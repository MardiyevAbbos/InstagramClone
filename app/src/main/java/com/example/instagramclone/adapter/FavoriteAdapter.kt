package com.example.instagramclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.fragments.FavoriteFragment
import com.example.instagramclone.model.Post
import com.google.android.material.imageview.ShapeableImageView

class FavoriteAdapter(var fragment: FavoriteFragment, var items: ArrayList<Post>) : BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_favorite, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder){
            holder.bind(items[position])
        }
    }

    inner class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        private var iv_profile: ShapeableImageView
        private var iv_post: ShapeableImageView
        private var tv_fullname: TextView
        private var tv_time: TextView
        private var tv_caption: TextView
        private var iv_more: ImageView
        private var iv_like: ImageView
        private var iv_share: ImageView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            iv_post = view.findViewById(R.id.iv_post)
            tv_fullname = view.findViewById(R.id.tv_fullname)
            tv_time = view.findViewById(R.id.tv_time)
            tv_caption = view.findViewById(R.id.tv_caption)
            iv_more = view.findViewById(R.id.iv_more)
            iv_like = view.findViewById(R.id.iv_like)
            iv_share = view.findViewById(R.id.iv_share)
        }

        fun bind(post: Post){
            Glide.with(fragment).load(post.image).into(iv_post)
        }

    }

}