package jmapps.lessonsoframadan.presentation.mvp.model

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.lessonsoframadan.data.database.DatabaseOpenHelper
import jmapps.lessonsoframadan.presentation.ui.chapterList.model.ChapterListModel
import jmapps.lessonsoframadan.presentation.ui.favoriteList.model.FavoriteListModel

class DatabaseChapters(private val context: Context?) {

    private lateinit var sqLiteDatabase: SQLiteDatabase

    val getChapterList: List<ChapterListModel>
        @SuppressLint("Recycle") get() {

            sqLiteDatabase = DatabaseOpenHelper(context).readableDatabase

            val cursor: Cursor = sqLiteDatabase.query(
                    "Table_of_chapters",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )

            val chapterList = ArrayList<ChapterListModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val chapter = ChapterListModel()
                    chapter.strPositionId = cursor.getString(cursor.getColumnIndex("_id"))
                    chapter.strChapterNumber = cursor.getString(cursor.getColumnIndex("ChapterNumber"))
                    chapter.strChapterTitle = cursor.getString(cursor.getColumnIndex("ChapterTitle"))
                    chapterList.add(chapter)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return chapterList
        }

    val getFavoriteList: List<FavoriteListModel>
        @SuppressLint("Recycle") get() {

            sqLiteDatabase = DatabaseOpenHelper(context).readableDatabase

            val cursor: Cursor = sqLiteDatabase.query(
                    "Table_of_chapters",
                    null,
                    "Favorite = 1",
                    null,
                    null,
                    null,
                    null,
                    null
            )

            val favoriteList = ArrayList<FavoriteListModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val favorite = FavoriteListModel()
                    favorite.strPositionId = cursor.getString(cursor.getColumnIndex("_id"))
                    favorite.strFavoriteNumber = cursor.getString(cursor.getColumnIndex("ChapterNumber"))
                    favorite.strFavoriteTitle = cursor.getString(cursor.getColumnIndex("ChapterTitle"))
                    favoriteList.add(favorite)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return favoriteList
        }
}