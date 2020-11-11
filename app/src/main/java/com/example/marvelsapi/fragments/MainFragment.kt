package com.example.marvelsapi.fragments

import android.os.Bundle
import android.os.Handler
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
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.data.MarvelCharacters
import com.example.marvelsapi.data.Thumbnail
import com.example.marvelsapi.network.MarvelApiService
import kotlinx.android.synthetic.main.hero_list_fragment.*
import kotlinx.android.synthetic.main.recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {


    var heroesList: MutableList<Hero?> = mutableListOf()
    lateinit var adapter: HeroListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        heroes_recycler_list.layoutManager = LinearLayoutManager(context)
        retrieveHeroes(ApiConstants.offset)
        heroes_recycler_list.layoutManager = LinearLayoutManager(context)
        adapter = HeroListAdapter(heroes_recycler_list, heroesList, this)
        heroes_recycler_list.adapter = adapter

        adapter.setLoadMore(object : MainContract.ILoadMore {
            override fun onLoadMore() {

//                heroesList.add(null)
//                adapter.notifyItemInserted(heroesList.size - 1)
                retrieveHeroes(ApiConstants.offset)

            }
        })
    }


    private fun retrieveHeroes(offset: Int) {
        if (heroesList.size != 0) {
            heroesList.add(null)
            adapter.notifyItemInserted(heroesList.size - 1)
        }
        val call: Call<MarvelCharacters> = MarvelApiService().getAllHeroes(offset)
        call.enqueue(object : Callback<MarvelCharacters> {
            override fun onFailure(call: Call<MarvelCharacters>?, t: Throwable?) {
                Log.i("FAIL", t.toString())
            }

            override fun onResponse(
                call: Call<MarvelCharacters>?,
                response: Response<MarvelCharacters>?
            ) {
                response?.let {
                    val heroes = it.body()

                    if (heroes != null && heroes.data.result.isNotEmpty()) {
                        if (heroesList.size > 0 && heroesList.last() == null) {
                            heroesList.removeAt(heroesList.size - 1)
                            adapter.notifyItemRemoved(heroesList.size)
                        }
                        heroesList.addAll(heroes.data.result)

                        adapter.notifyDataSetChanged()
                        adapter.setLoaded()

                        Log.i("RESPONSE", "$heroesList")
                        ApiConstants.offset += ApiConstants.limit
                    }

                }
            }

        })
    }
}
