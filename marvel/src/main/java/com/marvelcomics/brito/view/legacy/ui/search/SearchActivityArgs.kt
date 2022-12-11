package com.marvelcomics.brito.view.legacy.ui.search

import android.content.Context
import android.content.Intent
import com.marvelcomics.brito.view.legacy.ui.ActivityArgs

class SearchActivityArgs(val listIds: List<Int>) : ActivityArgs {
    override fun build(context: Context): Intent {
        return Intent(context, SearchActivity::class.java).apply {
            putExtra(EXTRA_LIST_IDS, listIds.toIntArray())
        }
    }

    companion object {
        const val EXTRA_LIST_IDS = "com.marvelcomics.brito.view.search.EXTRA_LIST_IDS"

        fun deserializeFrom(intent: Intent): SearchActivityArgs {
            return SearchActivityArgs(
                listIds = intent.getIntegerArrayListExtra(EXTRA_LIST_IDS)?.toList() ?: emptyList()
            )
        }
    }
}
