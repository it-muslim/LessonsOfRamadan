package jmapps.lessonsoframadan.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.lessonsoframadan.R

class Settings : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootSettings = inflater.inflate(R.layout.bottom_sheet_settings, container, false)

        return rootSettings
    }
}