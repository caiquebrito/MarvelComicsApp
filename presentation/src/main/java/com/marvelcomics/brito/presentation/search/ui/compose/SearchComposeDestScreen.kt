package com.marvelcomics.brito.presentation.search.ui.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.Black
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.marvelcomics.brito.presentation.ui.models.MarvelThumbnailAspectRatio
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SearchComposeDestScreen(
    viewModel: SearchViewModel = koinViewModel(),
    listIds: IntArray,
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val context = LocalContext.current
    fun handleDestEffect(searchUiEffect: SearchUiEffect) {
        when (searchUiEffect) {
            SearchUiEffect.ShowError -> {
                Toast.makeText(context, "Show Error", Toast.LENGTH_LONG).show()
            }
            SearchUiEffect.BackToHome -> {
                resultNavigator.navigateBack(result = true)
            }
            SearchUiEffect.ShowAlreadyAddedError -> {
                Toast.makeText(context, "Character Already Added", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    viewModel.effect.collectAsEffect(::handleDestEffect)
    MarvelComicsAppTheme {
        if (state.isIdle) {
            viewModel.setListIds(listIds.toList())
        }
        SearchScreenDestConstraint(
            showLoading = state.showLoading,
            listCharacters = state.listCharacters,
            searchCharacterByName = viewModel::searchCharacterByName,
            addCharacterButtonClick = viewModel::addCharacterButtonClicked
        )
    }
}

@Composable
fun SearchScreenDestConstraint(
    showLoading: Boolean,
    listCharacters: List<CharacterEntity>?,
    searchCharacterByName: (String) -> Unit,
    addCharacterButtonClick: (CharacterEntity) -> Unit
) {
    SearchBackgroundDestComponent {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)
            val topGuideline = createGuidelineFromTop(16.dp)

            val (toolbar, bodyContent) = createRefs()
            SearchToolbarDestComponent(
                searchCharacterByName,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(toolbar) {
                        linkTo(start = startGuideline, end = endGuideline)
                        top.linkTo(topGuideline)
                    }
            )
            SearchBodyListDestComponent(
                showLoading,
                listCharacters,
                addCharacterButtonClick,
                modifier = Modifier
                    .fillMaxHeight()
                    .constrainAs(bodyContent) {
                        linkTo(
                            top = toolbar.bottom,
                            start = startGuideline,
                            bottom = parent.bottom,
                            end = endGuideline
                        )
                        height = Dimension.fillToConstraints
                    }
            )
        }
    }
}

@Composable
fun SearchBodyListDestComponent(
    showLoading: Boolean,
    listCharacters: List<CharacterEntity>?,
    addCharacterButtonClick: (CharacterEntity) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center),
                color = Color.Red,
                strokeWidth = 12.dp
            )
        } else {
            listCharacters?.let {
                LazyColumn(
                    modifier = Modifier.wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(it) { item ->
                        SearchCharacterItemDestComponent(
                            character = item,
                            addCharacterButtonClick = addCharacterButtonClick,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchCharacterItemDestComponent(
    character: CharacterEntity,
    addCharacterButtonClick: (CharacterEntity) -> Unit,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        val (leftImage, textDescription, addButton) = createRefs()
        val backgroundResource = character.thumbnailEntity?.getFullUrlThumbnailWithAspect(
            MarvelThumbnailAspectRatio.Standard.SMALL
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(backgroundResource)
                .crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_hail_hydra),
            contentDescription = "Image Placeholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .constrainAs(leftImage) {
                    start.linkTo(parent.start)
                }
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxHeight()
                .constrainAs(textDescription) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        start = leftImage.end,
                        end = addButton.start
                    )
                    width = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    letterSpacing = 0.5.sp
                ),
                text = character.name ?: "Loading Name",
                color = Color.White
            )
            Text(
                text = character.description ?: "Loading Description",
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        FloatingActionButton(
            modifier = Modifier.constrainAs(addButton) {
                linkTo(top = parent.top, bottom = parent.bottom)
                end.linkTo(parent.end)
            },
            onClick = {
                addCharacterButtonClick.invoke(character)
            },
            backgroundColor = Color.Red
        ) {
            Icon(
                Icons.Filled.Add,
                "",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun SearchToolbarDestComponent(searchCharacterByName: (String) -> Unit, modifier: Modifier) {
    Row(
        modifier = modifier
            .height(70.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_marvel_m),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .width(70.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        val focusManager = LocalFocusManager.current
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.White,
                placeholderColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Nome do Personagem") },
            placeholder = { Text(text = "Ex: Hulk") },
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                searchCharacterByName.invoke(text.text)
            }),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SearchBackgroundDestComponent(content: @Composable () -> Unit) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_background_avengers),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Surface(
            color = Black,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.3f)
        ) {}
        content.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenConstraintDestPreview() {
    MarvelComicsAppPreview {
        SearchScreenConstraint(
            isIdle = true,
            showLoading = false,
            listCharacters = emptyList(),
            searchCharacterByName = { },
            addCharacterButtonClick = { }
        )
    }
}
