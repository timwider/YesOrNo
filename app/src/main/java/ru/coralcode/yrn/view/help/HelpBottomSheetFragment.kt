package ru.coralcode.yrn.view.help

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.coralcode.yrn.R

class HelpBottomSheetFragment: BottomSheetDialogFragment(R.layout.help_bottom_sheet_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<CardView>(R.id.btn_back).setOnClickListener { dialog?.dismiss() }
    }
}