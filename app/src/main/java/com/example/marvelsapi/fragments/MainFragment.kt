package com.example.marvelsapi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.R
import com.example.marvelsapi.adapters.HeroListAdapter
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.data.MarvelCharacters
import com.example.marvelsapi.network.MarvelApiService
import kotlinx.android.synthetic.main.hero_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class MainFragment : Fragment() {

    val arrayHeroes: MutableList<Hero> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListViewItems()
        heroes_recycler_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var isLoading: Boolean = true

                if (dy > 0) {
                    var visibleItem = recyclerView.layoutManager!!.childCount
                    var totalItemCount = recyclerView.layoutManager!!.itemCount
                    var pastVisibleItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (isLoading) {
                        if ((visibleItem + pastVisibleItems) >= totalItemCount) {
                            isLoading = false
                            Log.d("RECYCLER", "Last Item Reached")
                            retrieveHeroes(ApiConstants.offset + 100)
                        }
                    }
                }
            }
        })
    }

    private fun setupListViewItems() {
        heroes_recycler_list.layoutManager = LinearLayoutManager(activity)
        retrieveHeroes(ApiConstants.offset)
    }

    private fun updateListView(newList: List<Hero>) {
        heroes_recycler_list.adapter = HeroListAdapter(newList)
    }

    fun retrieveHeroes(offset: Int) {
        val call = MarvelApiService().getAllHeroes(offset)
        call.enqueue(object : Callback<MarvelCharacters> {
            override fun onFailure(call: Call<MarvelCharacters>?, t: Throwable?) {
                Log.i("FAIL", t.toString())
            }

            override fun onResponse(
                call: Call<MarvelCharacters>?,
                response: Response<MarvelCharacters>?
            ) {
                Log.i("RESPONSE", "AAAAAAAAAAAA")
                response?.let {
                    val heroes = it.body()

                    if (heroes != null && heroes.data.result.isNotEmpty()) {
                        arrayHeroes.addAll(heroes.data.result)
                        updateListView(arrayHeroes)
//                        Log.i("IMAGE", arrayHeroes[2].thumbnail.path)
                        ApiConstants.offset += 100
                    }

                }
            }

        })
    }
}