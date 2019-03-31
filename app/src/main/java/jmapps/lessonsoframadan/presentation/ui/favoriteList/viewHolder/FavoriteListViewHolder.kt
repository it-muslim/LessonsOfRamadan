package jmapps.lessonsoframadan.presentation.ui.favoriteList.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.lessonsoframadan.presentation.ui.favoriteList.adapter.FavoriteListAdapter
import jmapps.lessonsoframadan.presentation.ui.favoriteList.model.FavoriteListModel
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvItemFavoriteNumber: TextView = itemView.tvItemFavoriteNumber
    val tvItemFavoriteTitle: TextView = itemView.tvItemFavoriteTitle

    fun findOnItemClick(onFavoriteItemClick: FavoriteListAdapter.OnFavoriteItemClick,
                        favoriteModel: List<FavoriteListModel>, position: Int) {
        itemView.setOnClickListener {
            onFavoriteItemClick.onFavoriteClick(favoriteModel, position)
        }
    }
}