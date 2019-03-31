package jmapps.lessonsoframadan.presentation.mvp.view

interface DatabaseView {

    fun chapterNumber(number: String)

    fun chapterTitle(title: String)

    fun favoriteAdded()

    fun favoriteRemoved()

    fun chapterContent(content: String) {
    }
}