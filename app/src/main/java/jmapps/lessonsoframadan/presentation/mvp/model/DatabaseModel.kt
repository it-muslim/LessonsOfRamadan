package jmapps.lessonsoframadan.presentation.mvp.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.data.database.DatabaseOpenHelper
import jmapps.lessonsoframadan.presentation.mvp.presenter.DatabasePresenter
import jmapps.lessonsoframadan.presentation.mvp.view.DatabaseView
import kotlinx.android.synthetic.main.dialog_footnote.view.*


class DatabaseModel(
        private val context: Context?,
        private var sectionNumber: Int,
        private val databaseView: DatabaseView) : DatabasePresenter {

    private lateinit var strChapterNumber: String
    private lateinit var strChapterTitle: String
    private lateinit var strChapterContent: String

    private var strFootnoteId: String? = null
    private var strFootnoteContent: String? = null

    @SuppressLint("Recycle")
    override fun getContentFromDatabase(sectionNumber: Int) {

        val database: SQLiteDatabase = DatabaseOpenHelper(context).readableDatabase

        try {
            val mainCursor: Cursor = database.query(
                    "Table_of_chapters",
                    arrayOf("ChapterNumber", "ChapterTitle", "ChapterContent"),
                    "_id = ?",
                    arrayOf("$sectionNumber"),
                    null, null, null
            )

            if (!mainCursor.isClosed && mainCursor.moveToFirst()) {

                strChapterNumber = mainCursor.getString(0)
                strChapterTitle = mainCursor.getString(1)
                strChapterContent = mainCursor.getString(2)

                databaseView.chapterNumber(strChapterNumber)
                databaseView.chapterTitle(strChapterTitle)
                databaseView.chapterContent(strChapterContent)
            }

            if (mainCursor.isClosed) {
                mainCursor.close()
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка $e", Toast.LENGTH_LONG).show()
        }
    }

    override fun stringBuilder(str: String): SpannableStringBuilder {

        val database: SQLiteDatabase = DatabaseOpenHelper(context).readableDatabase
        var str = str
        val ssb = SpannableStringBuilder(Html.fromHtml(str))
        str = ssb.toString()

        var indexOne = str.indexOf("[")
        val indexTwo = intArrayOf(0)

        while (indexOne != -1) {
            indexTwo[0] = str.indexOf("]", indexOne) + 1

            var clickString = str.substring(indexOne, indexTwo[0])
            clickString = clickString.substring(1, clickString.length - 1)
            val finalClickString = clickString

            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {

                    try {
                        @SuppressLint("Recycle")
                        val cursor = database.query("Table_of_footnotes",
                                arrayOf("_id", "FootnoteContent"),
                                "_id = ?",
                                arrayOf(finalClickString), null, null, null)

                        if (cursor != null && cursor.moveToFirst()) {
                            strFootnoteId = cursor.getString(0)
                            strFootnoteContent = cursor.getString(1)
                        }
                        if (cursor != null && !cursor.isClosed) {
                            cursor.close()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "База данных недоступна", Toast.LENGTH_SHORT).show()
                    }

                    dialogFootnote(strFootnoteId, strFootnoteContent)

                }
            }, indexOne, indexTwo[0], 0)
            indexOne = str.indexOf("[", indexTwo[0])
        }
        return ssb
    }

    @SuppressLint("SetTextI18n")
    private fun dialogFootnote(strFootnoteId: String?, strFootnoteContent: String?) {
        @SuppressLint("InflateParams")
        val footnoteView = LayoutInflater.from(context).inflate(R.layout.dialog_footnote, null)
        val footnoteDialog = AlertDialog.Builder(context!!)
        footnoteDialog.setView(footnoteView)

        val footnoteIdNumber = footnoteView.tvFootnoteId
        val footnoteContent = footnoteView.tvFootnoteContent

        footnoteIdNumber.text = "Сноска [$strFootnoteId]"
        footnoteContent.text = Html.fromHtml(strFootnoteContent)

        footnoteDialog.create().show()
    }

    override fun addRemoveFavorite(editor: SharedPreferences.Editor, isChecked: Boolean) {
        val favorite = ContentValues()
        favorite.put("Favorite", isChecked)

        if (isChecked) {
            databaseView.favoriteAdded()
        } else {
            databaseView.favoriteRemoved()
        }

        editor.putBoolean("key_main_favorite_$sectionNumber", isChecked).apply()

        try {
            val database: SQLiteDatabase = DatabaseOpenHelper(context).readableDatabase
            database.update(
                    "Table_of_chapters",
                    favorite,
                    "_id = ?",
                    arrayOf("$sectionNumber")
            )
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun sharedContent() {
        val shareContent = Intent()
        shareContent.action = Intent.ACTION_SEND
        shareContent.type = "text/*"
        shareContent.putExtra(
                Intent.EXTRA_TEXT,
                Html.fromHtml("$strChapterNumber <br/> $strChapterTitle <p/> $strChapterContent")
        )
        context!!.startActivity(Intent.createChooser(shareContent, context.getString(R.string.share_to)))
    }
}