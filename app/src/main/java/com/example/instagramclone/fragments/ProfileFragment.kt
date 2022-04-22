package com.example.instagramclone.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.adapter.ProfileAdapter
import com.example.instagramclone.managers.AuthManager
import com.example.instagramclone.managers.DatabaseManager
import com.example.instagramclone.managers.StorageManager
import com.example.instagramclone.managers.handler.DBPostsHandler
import com.example.instagramclone.managers.handler.DBUserHandler
import com.example.instagramclone.managers.handler.DBUsersHandler
import com.example.instagramclone.managers.handler.StorageHandler
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.User
import com.example.instagramclone.utils.DialogListener
import com.example.instagramclone.utils.Utils
import com.google.android.material.imageview.ShapeableImageView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import java.lang.Exception

/**
 * In ProfileFragment, posts that user uploaded can be seen and user is able to change his/her profile photo
 */
class ProfileFragment : BaseFragment() {
    val TAG = ProfileFragment::class.java.simpleName
    private lateinit var rv_profile: RecyclerView
    private lateinit var iv_profile: ShapeableImageView
    private lateinit var tv_fullname: TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_posts: TextView
    private lateinit var tv_followers: TextView
    private lateinit var tv_following: TextView

    var pickedPhoto: Uri?= null
    var allPhotos = ArrayList<Uri>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View){
        loadUserInfo()  // show all Info about user
        rv_profile = view.findViewById(R.id.rv_profile)
        rv_profile.layoutManager = GridLayoutManager(activity, 2)

        val iv_logout = view.findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {
            signOutAPP()
        }

        iv_profile = view.findViewById(R.id.iv_profile)
        tv_email = view.findViewById(R.id.tv_email)
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }

        loadUserInfo()  // show all Info about user
        loadMyPosts()
        loadMyFollowers()
        loadMyFollowing()
    }


    private fun signOutAPP(){
        Utils.dialogSingle(requireContext(), getString(R.string.str_log_out_app), object : DialogListener{
            override fun onCallback(isChosen: Boolean) {
                if (isChosen){
                    AuthManager.signOut()
                    callSignInActivity(requireActivity())
                }
            }

        })
    }

    private fun loadMyPosts(){
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid, object: DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                tv_posts.text = posts.size.toString()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    /**
     * Pick photo using FishBun library
     */
    private fun pickFishBunPhoto(){
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
            pickedPhoto = allPhotos[0]
            uploadUserPhoto()
            allPhotos.clear()
        }
    }

    private fun uploadUserPhoto(){
        if(pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler{
            override fun onSuccess(imgUrl: String) {
                DatabaseManager.updateUserImage(imgUrl)
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(exception: Exception) {

            }

        })
    }

    private fun loadUserInfo(){
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler{
            override fun onSuccess(user: User?) {
                showUserInfo(user!!)
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun showUserInfo(user: User){
        tv_fullname.text = user.fullname
        tv_email.text = user.email
        Glide.with(this).load(user.userImg)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(iv_profile)
    }

    private fun refreshAdapter(items: ArrayList<Post>){
        val adapter = ProfileAdapter(this, items)
        rv_profile.adapter = adapter
    }

    private fun loadMyFollowing(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowing(uid, object : DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                tv_following.text = users.size.toString()
            }

            override fun onError(e: Exception) {
            }

        })
    }

    private fun loadMyFollowers(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowers(uid, object : DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                tv_followers.text = users.size.toString()
            }

            override fun onError(e: Exception) {
            }

        })
    }

}