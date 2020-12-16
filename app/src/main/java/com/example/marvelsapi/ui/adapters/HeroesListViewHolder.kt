package com.example.marvelsapi.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class HeroesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val heroImage: ImageView = itemView.hero_image
    val heroName: TextView = itemView.hero_name
    val heroDescription: TextView = itemView.hero_description
}
