package ru.coralcode.yrn.view.help

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.coralcode.yrn.R

class HelpBottomSheetDialogFragment: BottomSheetDialogFragment(R.layout.help_bottom_sheet_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineBackButton(view.findViewById(R.id.btn_back))
        adjustForLandscape()

    }

    private fun adjustForLandscape() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}

fun BottomSheetDialogFragment.defineBackButton(backButton: View) {
    backButton.setOnClickListener { this.dismiss() }
}

