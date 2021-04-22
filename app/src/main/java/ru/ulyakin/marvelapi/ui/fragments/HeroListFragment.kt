package ru.ulyakin.marvelapi.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.fragment_hero_list.*
import ru.ulyakin.marvelapi.MarvelApp
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelapi.ui.viewmodel.HeroesListViewModel
import ru.ulyakin.marvelapi.ui.adapter.HeroesListAdapter
import ru.ulyakin.marvelapi.ui.adapter.HeroesLoadStateAdapter
import ru.ulyakin.marvelapi.ui.adapter.OnItemClickListener
import ru.ulyakin.marvelsapi.R

class HeroListFragment :
    Fragment(R.layout.fragment_hero_list) {

    private lateinit var viewModel: HeroesListViewModel
    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClicked(hero: Hero) {
            findNavController().navigate(R.id.heroDetailFragment)
            Toast.makeText(context, "Hero - ${hero.name}, id - ${hero.id}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = (activity?.application as MarvelApp).factory
        viewModel = ViewModelProvider(this, factory).get(
            HeroesListViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        initAdapter()

    private fun initAdapter() {
        HeroesListAdapter(onItemClickListener)
            .apply {
                initAdapterFooter(this)
                subscribeForItems(this)
                initStateListener(this)
                retry_button_main.setOnClickListener { this.retry() }
            }
    }

    private fun initAdapterFooter(adapter: HeroesListAdapter) {
        rvheroes_list.adapter = adapter.withLoadStateFooter(
            footer = HeroesLoadStateAdapter { adapter.retry() }
        )
    }

    private fun subscribeForItems(adapter: HeroesListAdapter) =
        adapter.submitData(lifecycle, viewModel.heroesList)

    private fun initStateListener(adapter: HeroesListAdapter) {
        adapter.addLoadStateListener { loadState ->
            rvheroes_list.isVisible = loadState.source.refresh is LoadState.NotLoading
            progress_bar_main.isVisible = loadState.source.refresh is LoadState.Loading
            retry_button_main.isVisible = loadState.source.refresh is LoadState.Error
        }
    }
}