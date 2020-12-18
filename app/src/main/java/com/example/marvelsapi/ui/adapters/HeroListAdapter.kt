package com.example.marvelsapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.R
import com.example.marvelsapi.data.model.Hero


class HeroListAdapter(
    private val fragment: Fragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var heroList: List<Hero?> = listOf()

    override fun getItemViewType(position: Int): Int {
        return if (heroList[position]?.id == VIEW_TYPE_LOADING) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_HERO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HERO) {
            HeroesListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_hero, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_bar, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeroesListViewHolder) {
            val hero: Hero? = heroList[position]
            val heroImagePath: String =
                hero?.thumbnail?.path + ApiConstants.IMAGE_RATIO + hero?.thumbnail?.extension
            val heroHolder: HeroesListViewHolder = holder
            Glide.with(fragment)
                .load(heroImagePath)
                .placeholder(R.drawable.marvel)
                .into(holder.heroImage)
            heroHolder.heroName.text = hero?.name
            heroHolder.heroDescription.text = hero?.description
        } else if (holder is LoadingViewHolder) {
            val loadingHolder: LoadingViewHolder = holder
            loadingHolder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int = heroList.size

    fun setHeroList(heroList: List<Hero?>) {
        this.heroList = heroList
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_HERO = 0
        const val VIEW_TYPE_LOADING = -1
    }
}