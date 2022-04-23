package com.example.instagramclone.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.R
import com.example.instagramclone.activity.MainActivity
import com.example.instagramclone.adapter.SearchAdapter
import com.example.instagramclone.managers.AuthManager
import com.example.instagramclone.managers.DatabaseManager
import com.example.instagramclone.managers.handler.DBFollowHandler
import com.example.instagramclone.managers.handler.DBUserHandler
import com.example.instagramclone.managers.handler.DBUsersHandler
import com.example.instagramclone.model.Data
import com.example.instagramclone.model.User
import com.example.instagramclone.utils.Extensions.toast
import com.example.instagramclone.utils.Utils
import java.lang.Exception

/**
 * In Search Fragment, all registered users can be found by searching keyword and followed.
 */
class SearchFragment : BaseFragment() {
    val TAG = SearchFragment::class.java.simpleName
    private lateinit var rv_search: RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View){
        rv_search = view.findViewById(R.id.rv_search)
        rv_search.layoutManager = GridLayoutManager(activity, 1)

        val et_search = view.findViewById<EditText>(R.id.et_search)
        et_search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim()
                usersByKeyword(keyword)
            }

        })

        loadUsers()
        refreshAdapter(items)
    }

    fun followOrUnfollow(to: User){
        val uid = AuthManager.currentUser()!!.uid
        if (!to.isFollowed){
            followUser(uid, to)
        }else{
            unFollowUser(uid, to)
        }
    }

    private fun followUser(uid: String, to: User){
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.followUser(me!!, to, object : DBFollowHandler{
                    override fun onSuccess(isDone: Boolean) {
                        to.isFollowed = true
                        DatabaseManager.storePostsToMyFeed(uid, to)

                        val title = getString(R.string.str_following)
                        val body = getString(R.string.str_followed_note).replace("$", me.fullname)
                        val data = Data(title, body, me.userImg, "search")
                        Utils.sendNote(data, to.device_token)
                    }

                    override fun onError(e: Exception) {
                    }

                })
            }

            override fun onError(e: Exception) {
            }

        })
    }

    private fun unFollowUser(uid: String, to: User){
        DatabaseManager.loadUser(uid, object : DBUserHandler{
            override fun onSuccess(me: User?) {
                DatabaseManager.unFollowUser(me!!, to, object : DBFollowHandler{
                    override fun onSuccess(isDone: Boolean) {
                        to.isFollowed = false
                        DatabaseManager.removePostsFromMyFeed(uid, to)
                    }

                    override fun onError(e: Exception) {
                    }

                })
            }

            override fun onError(e: Exception) {
            }

        })
    }

    private fun refreshAdapter(items: ArrayList<User>){
        val adapter = SearchAdapter(this, items)
        rv_search.adapter = adapter
    }

    private fun usersByKeyword(keyword: String){
        if (keyword.isEmpty()){
            refreshAdapter(items)
        }

        users.clear()
        for (user in items){
            if (user.fullname.toLowerCase().startsWith(keyword.toLowerCase())){
                users.add(user)
            }
        }
        refreshAdapter(users)
    }

    private fun loadUsers(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object : DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid, object : DBUsersHandler{
                    override fun onSuccess(followings: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid, users, followings))
                        refreshAdapter(items)
                    }

                    override fun onError(e: Exception) {
                    }

                })
            }

            override fun onError(e: Exception) {
            }

        })
    }

    private fun mergedUsers(uid: String, users: ArrayList<User>, followings: ArrayList<User>): ArrayList<User>{
        val items = ArrayList<User>()
        for(u in users){
            val user = u
            for(f in followings){
                if (u.uid == f.uid){
                    user.isFollowed = true
                    break
                }
            }
            if (uid != user.uid){
                items.add(user)
            }
        }
        return items
    }

}