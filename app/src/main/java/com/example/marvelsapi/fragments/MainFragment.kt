package com.example.marvelsapi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsapi.data.MarvelApplication
import com.example.marvelsapi.R
import com.example.marvelsapi.adapters.HeroListAdapter
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.data.Model
import com.example.marvelsapi.presenters.MainPresenter
import kotlinx.android.synthetic.main.hero_list_fragment.*

class MainFragment : Fragment(), MainContract.MainView {


    lateinit var adapter: HeroListAdapter
    private lateinit var presenter: MainPresenter
    private var heroList: MutableList<Hero?> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ONCREATE", "CALL")
        val model: Model = (activity?.application as MarvelApplication).model
        presenter = MainPresenter(model, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("ONVIEWCREATED", "CALL")
        heroes_recycler_list.layoutManager = LinearLayoutManager(context)
        Log.i("LIST++++++++++", "$heroList")
        presenter.loadHeroes()
        adapter = HeroListAdapter(heroes_recycler_list, heroList, this)
        heroes_recycler_list.adapter = adapter
        adapter.setLoadMore(object : MainContract.ILoadMore {
            override fun onLoadMore() {
                Log.i("ONLOADMORE", "CALL")

                presenter.loadHeroes()
                adapter.setLoaded()
                Log.i("LIST", presenter.getHeroesList().toString())
            }
        })

    }


    override fun notifyItemInsert(position: Int) {
        Log.i("NOTIFYITEMINSERT", "CALL")
        adapter.notifyItemInserted(position)
    }

    override fun notifyItemDelete(position: Int) {
        Log.i("NOTIFYITEMDELETE", "CALL")
        adapter.notifyItemRemoved(position)
    }

    override fun updateHeroesView() {
        adapter.setHeroList(presenter.getHeroesList())
        Log.i("UPDATEHEROESLIST", heroList.toString())
//        adapter.notifyDataSetChanged()
    }

    override fun notifyDataSetChanged() {
        Log.i("NOTIFYDATASETCHANGED", "CALL")
        adapter.notifyDataSetChanged()
    }

    override fun setLoaded() {
        adapter.setLoaded()
    }
//todo delete
    override fun getLoaded() =
        adapter.isLoading

}