package jmapps.lessonsoframadan.presentation.ui.favoriteList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.presentation.mvp.model.DatabaseChapters
import jmapps.lessonsoframadan.presentation.ui.favoriteList.adapter.FavoriteListAdapter
import jmapps.lessonsoframadan.presentation.ui.favoriteList.model.FavoriteListModel
import kotlinx.android.synthetic.main.dialog_fragment_favorite_list.view.*

class FavoriteList : DialogFragment(), FavoriteListAdapter.OnFavoriteItemClick {

    private lateinit var setCurrentPage: SetCurrentPageFavorite

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootFavoriteList = inflater.inflate(R.layout.dialog_fragment_favorite_list, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val vertical = LinearLayoutManager(context)
        rootFavoriteList.rvFavoriteList.layoutManager = vertical
        rootFavoriteList.rvFavoriteList.hasFixedSize()
        val favoriteListModel = DatabaseChapters(context).getFavoriteList
        val favoriteAdapter = FavoriteListAdapter(context, favoriteListModel, this)
        rootFavoriteList.rvFavoriteList.adapter = favoriteAdapter

        if (favoriteAdapter.itemCount != 0) {
            rootFavoriteList.tvFavoriteListIsEmpty.visibility = View.GONE
            rootFavoriteList.rvFavoriteList.visibility = View.VISIBLE
        } else {
            rootFavoriteList.tvFavoriteListIsEmpty.visibility = View.VISIBLE
            rootFavoriteList.rvFavoriteList.visibility = View.GONE
        }

        return rootFavoriteList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SetCurrentPageFavorite) {
            setCurrentPage = context
        } else {
            throw RuntimeException("$context must implement SetCurrentPageChapter")
        }
    }

    interface SetCurrentPageFavorite {
        fun setPage(currentPosition: Int)
    }

    override fun onFavoriteClick(favoriteModel: List<FavoriteListModel>, position: Int) {
        val strPositionId = favoriteModel[position].strPositionId
        setCurrentPage.setPage(strPositionId!!.toInt())
        dialog?.dismiss()
    }
}