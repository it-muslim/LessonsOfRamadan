package jmapps.lessonsoframadan.data.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private var DBVersion = 2
private const val DBName = "DatabaseContent"

class DatabaseOpenHelper(context: Context?) : SQLiteAssetHelper(context, DBName, null, DBVersion) {
    override fun setForcedUpgrade() {
        super.setForcedUpgrade(DBVersion)
    }
}