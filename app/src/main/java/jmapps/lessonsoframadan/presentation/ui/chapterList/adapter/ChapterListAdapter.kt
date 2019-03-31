package jmapps.lessonsoframadan.presentation.ui.chapterList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.presentation.ui.chapterList.model.ChapterListModel
import jmapps.lessonsoframadan.presentation.ui.chapterList.viewHolder.ChapterListViewHolder

class ChapterListAdapter(private val context: Context?,
                         private val chapterModel: List<ChapterListModel>,
                         private val onChapterItemClick: OnChapterItemClick) : RecyclerView.Adapter<ChapterListViewHolder>() {

    interface OnChapterItemClick {
        fun onChapterClick(chapterModel: List<ChapterListModel>, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterListViewHolder {
        return ChapterListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false))
    }

    override fun getItemCount(): Int {
        return chapterModel.size
    }

    override fun onBindViewHolder(holder: ChapterListViewHolder, position: Int) {
        val strChapterNumber = chapterModel[position].strChapterNumber
        val strChapterTitle = chapterModel[position].strChapterTitle

        holder.tvItemChapterNumber.text = strChapterNumber
        holder.tvItemChapterTitle.text = strChapterTitle

        holder.findOnItemClick(onChapterItemClick, chapterModel, position)
    }
}