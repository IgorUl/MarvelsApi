package com.example.marvelsapi.ui.adapters

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.marvelsapi.R


class LoadingViewHolder(itemView: View) : ViewHolder(itemView) {
    var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
}