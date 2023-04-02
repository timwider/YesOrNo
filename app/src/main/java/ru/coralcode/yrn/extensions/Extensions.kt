package ru.coralcode.yrn.extensions

import android.util.Log

const val LOG_TAG = "yes_or_no_tag_debug"


fun log(block: () -> String) {
    Log.d(LOG_TAG, block.invoke())
}