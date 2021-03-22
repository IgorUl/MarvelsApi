package ru.ulyakin.marvelapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.fragment_hero_list.*
import ru.ulyakin.marvelapi.MarvelApp
import ru.ulyakin.marvelapi.ui.viewmodel.HeroesListViewModel
import ru.ulyakin.marvelapi.ui.adapter.HeroesListAdapter
import ru.ulyakin.marvelapi.ui.adapter.HeroesLoadStateAdapter
import ru.ulyakin.marvelsapi.R

class HeroListFragment : Fragment() {

    private lateinit var viewModel: HeroesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hero_list, container, false)
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
        val adapter = HeroesListAdapter()
        adapter.apply {
            initAdapterFooter(this)
            subscribeForItems(this)
            initStateListener(adapter)
            retry_button_main.setOnClickListener { adapter.retry() }
        }
    }

    private fun initAdapterFooter(adapter: HeroesListAdapter) {
        rvheroes_list.adapter = adapter.withLoadStateFooter(
            footer = HeroesLoadStateAdapter { adapter.retry() }
        )
    }

    private fun subscribeForItems(adapter: HeroesListAdapter) {
        adapter.submitData(lifecycle, viewModel.heroesList)
    }

    private fun initStateListener(adapter: HeroesListAdapter) {
        adapter.addLoadStateListener { loadState ->
            rvheroes_list.isVisible = loadState.source.refresh is LoadState.NotLoading
            progress_bar_main.isVisible = loadState.source.refresh is LoadState.Loading
            retry_button_main.isVisible = loadState.source.refresh is LoadState.Error
        }
    }
}