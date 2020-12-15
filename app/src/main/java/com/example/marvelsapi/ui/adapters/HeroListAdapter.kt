package com.example.marvelsapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.R
import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.model.Hero


class HeroListAdapter(
    recyclerView: RecyclerView,
    private val fragment: Fragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var heroList: List<Hero?> = listOf()
    var isLoading: Boolean = false

    var loadMore: MainContract.ILoadMore? = null
        private set

    val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

    init {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount: Int = layoutManager.itemCount
                val visibleItem: Int = layoutManager.childCount

                val lastVisibleItems: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= (lastVisibleItems + visibleItem)) {
                    loadMore?.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if (heroList[position]?.id == VIEW_TYPE_LOADING) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_HERO
        }
    }

    fun setLoadMore(loadMore: MainContract.ILoadMore) {
        this.loadMore = loadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HERO) {
            HeroesListViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.progress_bar, parent, false)
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

    fun setLoaded() {
        isLoading = false
    }

    fun setHeroList(heroList: List<Hero?>) {
        this.heroList = heroList
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_HERO = 0
        const val VIEW_TYPE_LOADING = -1
    }
}
