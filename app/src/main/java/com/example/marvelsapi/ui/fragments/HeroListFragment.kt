package com.example.marvelsapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelsapi.MarvelApplication
import com.example.marvelsapi.R
import com.example.marvelsapi.ui.adapters.HeroListAdapter
import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.model.Model
import com.example.marvelsapi.ui.presenters.HeroListPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.hero_list_fragment.*

class HeroListFragment : Fragment(), MainContract.MainView {

    private lateinit var presenter: HeroListPresenter
    lateinit var adapter: HeroListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model: Model = (activity?.application as MarvelApplication).model
        presenter = HeroListPresenter(model, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeRecyclerView()

        if (savedInstanceState == null) {
            presenter.loadHeroes()
        } else {
            presenter.onCreate()
        }
    }

    private fun initializeRecyclerView() {
        heroes_recycler_list.layoutManager = LinearLayoutManager(context)
        adapter = HeroListAdapter(heroes_recycler_list, this)
        heroes_recycler_list.adapter = adapter
        adapter.setLoadMore(object : MainContract.ILoadMore {
            override fun onLoadMore() {
                presenter.addProgressBar()
                presenter.loadHeroes()
                adapter.setLoaded()
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
        adapter.setLoaded()
    }

    override fun showRefreshSnackbar() {
        val snackbar = Snackbar.make(rvhero_list_container, resources.getText(R.string.snackbar_notify), Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(resources.getText(R.string.snackbar_button)) { presenter.loadHeroes() }.show()
    }
}