package ru.ulyakin.marvelapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.fragment_hero_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.ulyakin.marvelapi.ui.viewmodel.HeroesListViewModel
import ru.ulyakin.marvelapi.ui.Injection
import ru.ulyakin.marvelapi.ui.adapter.HeroesListAdapter
import ru.ulyakin.marvelapi.ui.adapter.HeroesLoadStateAdapter
import ru.ulyakin.marvelsapi.R

class HeroListFragment : Fragment() {

    private lateinit var viewModel: HeroesListViewModel
    private val pagingAdapter = HeroesListAdapter()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hero_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(
            HeroesListViewModel::class.java
        )
    }

    private fun loadHeroes() {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getHero().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        loadHeroes()
        initStateListener()
        retry_button_main.setOnClickListener { pagingAdapter.retry() }
    }

    private fun initAdapter() {
        rvheroes_list.adapter = pagingAdapter.withLoadStateFooter(
            footer = HeroesLoadStateAdapter { pagingAdapter.retry() }
        )
    }

    private fun initStateListener() {
        pagingAdapter.addLoadStateListener { loadState ->
            rvheroes_list.isVisible = loadState.source.refresh is LoadState.NotLoading
            progress_bar_main.isVisible = loadState.source.refresh is LoadState.Loading
            retry_button_main.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}