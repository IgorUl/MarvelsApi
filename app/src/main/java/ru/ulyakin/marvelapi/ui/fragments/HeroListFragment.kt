package ru.ulyakin.marvelapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.marvelsapi.R
import kotlinx.android.synthetic.main.fragment_hero_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ulyakin.marvelapi.ui.HeroesListViewModel
import ru.ulyakin.marvelapi.ui.Injection
import ru.ulyakin.marvelapi.ui.adapter.HeroesListAdapter

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
            HeroesListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeRecyclerView()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getHero().collect {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun initializeRecyclerView() {
        rvheroes_list.adapter = pagingAdapter
    }
}