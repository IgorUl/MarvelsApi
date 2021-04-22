package ru.ulyakin.marvelapi.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class HeroesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HeroesLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: HeroesLoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HeroesLoadStateViewHolder =
        HeroesLoadStateViewHolder.create(parent, retry)
}