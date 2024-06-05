package com.marvelcomics.brito.presentation.search

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marvelcomics.brito.domain.usecase.LoadCharacterUseCase
import com.marvelcomics.brito.domain.usecase.SaveCharacterUseCase
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.CoroutineTestRule
import com.marvelcomics.brito.presentation.fake.MarvelFakeRepository
import com.marvelcomics.brito.presentation.search.ui.compose.OUTLINE_FIELD_TEST_TAG
import com.marvelcomics.brito.presentation.search.ui.compose.SEARCH_BODY_LIST_CHARACTER_TAG
import com.marvelcomics.brito.presentation.search.ui.compose.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
    private val fakeRepository = MarvelFakeRepository()

    private val loadCharacterUseCaseFake =
        LoadCharacterUseCase(fakeRepository, coroutineTestRule.dispatcher)
    private val saveCharacterUseCaseFake = SaveCharacterUseCase(
        fakeRepository, coroutineTestRule.dispatcher
    )

    private val viewModel = SearchViewModel(loadCharacterUseCaseFake, saveCharacterUseCaseFake)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun `when i search, check if character is returned`() {
        // init rule with custom scenario
        composeTestRule.setContent {
            val state = viewModel.state.value
            SearchScreen(
                showLoading = state.showLoading,
                searchCharacterByName = viewModel::searchCharacterByName,
                addCharacterButtonClick = viewModel::addCharacterButtonClicked,
                listCharacters = listOf(
                    CharacterEntity(
                        id = 0,
                        name = "Hulk",
                        description = "Green friend",
                        thumbnailEntity = null
                    )
                )
            )
        }

        // perform action to load list
        composeTestRule
            .onNodeWithTag(OUTLINE_FIELD_TEST_TAG)
            .performTextInput("Hulk")

        composeTestRule
            .onNodeWithTag(OUTLINE_FIELD_TEST_TAG)
            .performKeyPress(
                KeyEvent(NativeKeyEvent(NativeKeyEvent.ACTION_DOWN, NativeKeyEvent.KEYCODE_ENTER))
            )

        assert(viewModel.searchCalled)

        composeTestRule.runOnIdle {}

        composeTestRule.onRoot().printToLog("ScreenTestRobolectric")

        // assert if showing character on list
        composeTestRule
            .onNodeWithTag(SEARCH_BODY_LIST_CHARACTER_TAG + 1)
            .performScrollTo()
            .assertIsDisplayed()
    }
}