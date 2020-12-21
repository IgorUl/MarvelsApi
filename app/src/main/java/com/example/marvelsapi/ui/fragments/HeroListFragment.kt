package com.example.marvelsapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsapi.MarvelApplication
import com.example.marvelsapi.R
import com.example.marvelsapi.ui.adapters.HeroListAdapter
import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.model.Model
import com.example.marvelsapi.ui.presenters.HeroListPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_hero_list.*

class HeroListFragment : Fragment(), MainContract.MainView {

    private lateinit var presenter: HeroListPresenter
    lateinit var adapter: HeroListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hero_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model: Model = (activity?.application as MarvelApplication).model
        presenter = HeroListPresenter(model, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeRecyclerView()
        presenter.onCreate()
    }

    private fun initializeRecyclerView() {

        rvheroes_list.layoutManager = LinearLayoutManager(context)
        adapter = HeroListAdapter(this)
        rvheroes_list.adapter = adapter
        val layoutManager: LinearLayoutManager =
            rvheroes_list.layoutManager as LinearLayoutManager
        rvheroes_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount: Int = layoutManager.itemCount
                val visibleItem: Int = layoutManager.childCount

                val lastVisibleItems: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (!presenter.getLoadState() && totalItemCount <= (lastVisibleItems + visibleItem)) {
                    presenter.onListEndReached()
                }
            }
        })
    }

    override fun notifyItemInsert(position: Int) {
        adapter.notifyItemInserted(position)
    }

    override fun updateHeroesView() {
        adapter.setHeroList(presenter.getHeroesList())
    }

    override fun setLoaded() {
        presenter.setLoaded()
    }

    override fun showRefreshSnackbar() {
        val snackbar = Snackbar.make(
            hero_list_container,
            resources.getText(R.string.snackbar_notify),
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(resources.getText(R.string.snackbar_button)) { presenter.loadHeroes() }
            .show()
    }
}