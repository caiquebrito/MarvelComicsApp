package com.marvelcomics.brito.view.legacy.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.databinding.ActivitySearchBinding
import com.marvelcomics.brito.view.hideKeyboard
import com.marvelcomics.brito.view.legacy.extensions.viewBinding

class SearchActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySearchBinding::inflate)
    private val actionsEnter = listOf(
        EditorInfo.IME_ACTION_NEXT,
        EditorInfo.IME_ACTION_GO,
        EditorInfo.IME_ACTION_DONE,
        EditorInfo.IME_ACTION_SEARCH
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textinputedittextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionsEnter.contains(actionId)) {
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }
}
