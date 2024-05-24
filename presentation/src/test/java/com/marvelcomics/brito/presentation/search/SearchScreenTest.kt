package com.marvelcomics.brito.presentation.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marvelcomics.brito.domain.usecase.LoadCharacterUseCase
import com.marvelcomics.brito.domain.usecase.SaveCharacterUseCase
import com.marvelcomics.brito.presentation.search.ui.compose.OUTLINE_FIELD_TEST_TAG
import com.marvelcomics.brito.presentation.search.ui.compose.SearchScreenConstraint
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    private val loadCharacterUseCaseMock = mockk<LoadCharacterUseCase>()
    private val saveCharacterUseCaseMock = mockk<SaveCharacterUseCase>()
    private val viewModel = SearchViewModel(loadCharacterUseCaseMock, saveCharacterUseCaseMock)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun `when i search, check if character is returned`() {
        // init rule with custom scenario
        composeTestRule.setContent {
            SearchScreenConstraint(
                showLoading = false,
                listCharacters = listOf(),
                searchCharacterByName = {},
                addCharacterButtonClick = {

                }
            )
        }

        // perform action to load list
        composeTestRule.onNodeWithTag(OUTLINE_FIELD_TEST_TAG).performTextInput("Hulk")

        // assert if showing character on list
    }
}