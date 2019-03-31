package jmapps.lessonsoframadan.presentation.ui.chapterList.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.lessonsoframadan.presentation.ui.chapterList.adapter.ChapterListAdapter
import jmapps.lessonsoframadan.presentation.ui.chapterList.model.ChapterListModel
import kotlinx.android.synthetic.main.item_chapter.view.*

class ChapterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvItemChapterNumber: TextView = itemView.tvItemChapterNumber
    val tvItemChapterTitle: TextView = itemView.tvItemChapterTitle

    fun findOnItemClick(onChapterItemClick: ChapterListAdapter.OnChapterItemClick,
                        chapterModel: List<ChapterListModel>, position: Int) {
        itemView.setOnClickListener {
            onChapterItemClick.onChapterClick(chapterModel, position)
        }
    }
}