package jmapps.lessonsoframadan.presentation.ui.favoriteList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.presentation.ui.favoriteList.model.FavoriteListModel
import jmapps.lessonsoframadan.presentation.ui.favoriteList.viewHolder.FavoriteListViewHolder

class FavoriteListAdapter(private val context: Context?,
                          private val favoriteModel: List<FavoriteListModel>,
                          private val onFavoriteItemClick: OnFavoriteItemClick) : RecyclerView.Adapter<FavoriteListViewHolder>() {

    interface OnFavoriteItemClick {
        fun onFavoriteClick(favoriteModel: List<FavoriteListModel>, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false))
    }

    override fun getItemCount(): Int {
        return favoriteModel.size
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        val strFavoriteNumber = favoriteModel[position].strFavoriteNumber
        val strFavoriteTitle = favoriteModel[position].strFavoriteTitle

        holder.tvItemFavoriteNumber.text = strFavoriteNumber
        holder.tvItemFavoriteTitle.text = strFavoriteTitle

        holder.findOnItemClick(onFavoriteItemClick, favoriteModel, position)
    }
}