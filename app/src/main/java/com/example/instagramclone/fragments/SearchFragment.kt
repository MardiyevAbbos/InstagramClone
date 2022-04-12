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
import com.example.instagramclone.adapter.SearchAdapter
import com.example.instagramclone.model.User

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

    private fun loadUsers(): ArrayList<User>{
        items = ArrayList()
        items.add(User("abbos", "abbosmardiev@gamil.com"))
        items.add(User("elyor", "elyormamayev@gamil.com"))
        items.add(User("shaxriyor", "shaxriyormirzamurodov@gamil.com"))
        items.add(User("abdulla", "abdullaergashev@gamil.com"))
        items.add(User("azamat", "azamatzarpullayev@gamil.com"))
        items.add(User("jonibek", "jonibektinchlikov@gamil.com"))
        items.add(User("ulug`bek", "ulugbekmaxanov@gamil.com"))
        items.add(User("qilichbek", "qilichbekpardaboyev@gamil.com"))
        items.add(User("azizjon", "azizjonsheronov@gamil.com"))
        items.add(User("abbos", "abbosmardiev@gamil.com"))
        items.add(User("elyor", "elyormamayev@gamil.com"))
        items.add(User("shaxriyor", "shaxriyormirzamurodov@gamil.com"))
        items.add(User("abdulla", "abdullaergashev@gamil.com"))
        items.add(User("azamat", "azamatzarpullayev@gamil.com"))
        items.add(User("jonibek", "jonibektinchlikov@gamil.com"))
        return items
    }

}