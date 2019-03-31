package jmapps.lessonsoframadan.presentation.ui.mainContainer

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import jmapps.lessonsoframadan.R
import jmapps.lessonsoframadan.presentation.mvp.model.DatabaseModel
import jmapps.lessonsoframadan.presentation.mvp.model.ScrollCountModel
import jmapps.lessonsoframadan.presentation.mvp.view.DatabaseView
import kotlinx.android.synthetic.main.fragment_main.view.*

class PlaceholderFragment : Fragment(), DatabaseView, CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var rootView: View
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var databasePresenter: DatabaseModel
    private lateinit var scrollCountModel: ScrollCountModel
    private var sectionNumber: Int = 0

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_main, container, false)

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)!!

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()

        databasePresenter = DatabaseModel(context, sectionNumber, this)
        databasePresenter.getContentFromDatabase(sectionNumber)

        scrollCountModel = ScrollCountModel(sectionNumber, rootView.nvMainContentScroll!!, rootView.pbScrollCount!!)
        scrollCountModel.scrollCount()

        rootView.tbBookmarkState.isChecked = preferences.getBoolean("key_main_favorite_$sectionNumber", false)
        rootView.tbBookmarkState.setOnCheckedChangeListener(this)
        rootView.btnShareContent.setOnClickListener(this)

        scrollCountModel.loadLastCount(preferences)

        return rootView
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        databasePresenter.addRemoveFavorite(editor, isChecked)
    }

    override fun onClick(v: View?) {
        databasePresenter.sharedContent()
    }

    override fun favoriteAdded() {
        Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_LONG).show()
    }

    override fun favoriteRemoved() {
        Toast.makeText(context, "Удалено из избранного", Toast.LENGTH_LONG).show()
    }

    override fun chapterNumber(number: String) {
        rootView.tvChapterNumber.text = number
    }

    override fun chapterTitle(title: String) {
        rootView.tvChapterTitle.text = title
    }

    override fun chapterContent(content: String) {
        rootView.tvMainContent.movementMethod = LinkMovementMethod.getInstance();
        rootView.tvMainContent.setText(databasePresenter.stringBuilder(content), TextView.BufferType.SPANNABLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollCountModel.saveLastCount(editor)
    }
}