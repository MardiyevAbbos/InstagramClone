package com.example.instagramclone.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.R
import com.example.instagramclone.activity.MainActivity
import com.example.instagramclone.adapter.HomeAdapter
import com.example.instagramclone.managers.AuthManager
import com.example.instagramclone.managers.DatabaseManager
import com.example.instagramclone.managers.handler.DBPostHandler
import com.example.instagramclone.managers.handler.DBPostsHandler
import com.example.instagramclone.managers.handler.DBUserHandler
import com.example.instagramclone.model.Data
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.User
import com.example.instagramclone.utils.DialogListener
import com.example.instagramclone.utils.Extensions.toast
import com.example.instagramclone.utils.Utils
import java.lang.Exception
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    private var listener: HomeListener? = null
    private lateinit var rv_home: RecyclerView
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size > 0){
            loadMyFeeds()
        }
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
        rv_home = view.findViewById(R.id.rv_home)
        rv_home.layoutManager = GridLayoutManager(activity, 1)

        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener { listener!!.scrollToUpload() }

        loadMyFeeds()
    }

    private fun refreshAdapter(items: ArrayList<Post>){
        val adapter = HomeAdapter(this, items)
        rv_home.adapter = adapter
    }

    private fun loadMyFeeds(){
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    fun likeOrUnlikePost(post: Post){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)

        DatabaseManager.loadUser(uid, object : DBUserHandler{
            override fun onSuccess(user: User?) {
                if (user!!.device_token != post.device_token){
                    val title = getString(R.string.str_favorite)
                    val body = getString(R.string.str_liked_node).replace("$", user!!.fullname)
                    val data = Data(title, body, post.postImg, "home")
                    Utils.sendNote(data, post.device_token)
                }
            }

            override fun onError(e: Exception) {

            }

        })
    }

    fun showDeleteDialog(post: Post){
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object : DialogListener{
            override fun onCallback(isChosen: Boolean) {
                if (isChosen){
                    deletePost(post)
                }
            }

        })
    }

    private fun deletePost(post: Post){
        DatabaseManager.deletePost(post, object : DBPostHandler{
            override fun onSuccess(post: Post) {
                loadMyFeeds()
            }

            override fun onError(e: Exception) {
            }

        })
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener{
        fun scrollToUpload()
    }

}