package com.example.marvelsapi.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.R
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.data.Hero


class HeroListAdapter(
    recyclerView: RecyclerView,
    private var heroList: List<Hero?>,
    private val fragment: Fragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //    //todo поменять при проблемах
    var isLoading: Boolean = false

    var loadMore: MainContract.ILoadMore? = null
        private set

    val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

    init {
        Log.i("INIT ADAPTER", "++++++++")
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount: Int = layoutManager.itemCount
                //todo закоментить и вынести в переменную со значением 5
                val visibleItem: Int = layoutManager.childCount

                val lastVisibleItems: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= (lastVisibleItems + visibleItem)) {
                    loadMore?.onLoadMore()
                    isLoading = true
                    Log.d("RECYCLER", "Last Item Reached: offset = ${ApiConstants.offset}")
//                    retrieveHeroes(ApiConstants.offset + ApiConstants.limit)
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if (heroList[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_HERO
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)

        var layoutPosition: Int = holder.layoutPosition
        Log.d(TAG, "onViewAttachedToWindow: getayoutPosition = " + layoutPosition);

        layoutPosition = holder.adapterPosition;
        Log.d(TAG, "onViewAttachedToWindow: getAdapterPosition = " + layoutPosition);
    }

    fun setLoadMore(loadMore: MainContract.ILoadMore) {
        this.loadMore = loadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("ONCREATEHOLDER CALL", "+++++++++")
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
        Log.i("ONBINDVIEWHOLDER CALL", "+++++++")
        if (holder is HeroesListViewHolder) {
            val hero: Hero? = heroList[position]
            val heroImagePath: String =
                hero?.thumbnail?.path + ApiConstants.IMAGE_RATIO + hero?.thumbnail?.extension
            val heroHolder: HeroesListViewHolder = holder
                    Glide.with(fragment)
                        .load(heroImagePath)
                        .into(holder.heroImage)
            heroHolder.heroName.text = hero?.name
            Log.i("HERO", "${heroHolder.heroName} + ${hero?.name}")
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

    companion object {
        const val VIEW_TYPE_HERO = 0
        const val VIEW_TYPE_LOADING = 1
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesListViewHolder {
//        return HeroesListViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int = heroList.size
//
//    override fun onBindViewHolder(holder: HeroesListViewHolder, position: Int) {
//
//
//        val hero: Hero = heroList[position]
//
//        val heroImagePath: String =
//            hero.thumbnail.path + ApiConstants.IMAGE_RATIO + hero.thumbnail.extension
//
//
//        Glide.with(fragment)
//            .load(heroImagePath)
//            .into(holder.heroImage)
//
//        holder.heroName.text = hero.name
//        holder.heroDescription.text = hero.description
//    }
}
