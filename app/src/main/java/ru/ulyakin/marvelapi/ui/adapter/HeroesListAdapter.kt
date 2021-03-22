package ru.ulyakin.marvelapi.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.ulyakin.marvelapi.data.model.Hero

class HeroesListAdapter : PagingDataAdapter<Hero, HeroesListViewHolder>(HERO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesListViewHolder {
        return HeroesListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HeroesListViewHolder, position: Int) {
        val heroItem = getItem(position)
        if (heroItem != null) {
            holder.bind(heroItem)
        }
    }

    companion object {
        private val HERO_COMPARATOR = object : DiffUtil.ItemCallback<Hero>() {
            override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean =
                oldItem == newItem
        }
    }
}