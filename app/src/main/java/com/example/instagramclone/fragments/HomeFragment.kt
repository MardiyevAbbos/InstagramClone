package com.example.instagramclone.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.R
import com.example.instagramclone.adapter.HomeAdapter
import com.example.instagramclone.model.Post
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    private var listener: HomeListener? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    /**
     * onAttach is for communication of Fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener){
            context
        }else{
            throw RuntimeException("$context must implement HomeListener")
        }
    }

    /**
     * onDetach is for communication of Fragments
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    private fun initViews(view: View){
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)

        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener { listener!!.scrollToUpload() }

        refreshAdapter(loadPosts())
    }

    private fun refreshAdapter(items: ArrayList<Post>){
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun loadPosts(): ArrayList<Post>{
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1649452814966-87450893154a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxN3x8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1524758631624-e2822e304c36?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2OXx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1606054534744-a3b13e35c574?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dHJpcHxlbnwwfDJ8MHx8&auto=format&fit=crop&w=500&q=60"))
        return items
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener{
        fun scrollToUpload()
    }

}