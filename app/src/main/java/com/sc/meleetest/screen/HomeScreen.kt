package com.sc.meleetest.screen

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sc.meleetest.R
import com.sc.meleetest.utils.IMAGE_LOGO_DESCRIPTION
import com.sc.meleetest.utils.SEARCH_BUTTON_DESCRIPTION
import com.sc.meleetest.utils.SEARCH_TEXT_FIELD_HINT
import com.sc.meleetest.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(null)
}
@Composable
fun HomeScreen(
    viewModel: HomeViewModel?
) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val isSearchShaking = remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val focusManager = LocalFocusManager.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val shakeOffset by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(isSearchShaking.value) {
        launch {
            delay(500)
            isSearchShaking.value = false
        }
    }
    Scaffold(
        modifier = Modifier
        .fillMaxSize(),
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.lg_mercadolibre),
                contentDescription = IMAGE_LOGO_DESCRIPTION,
                modifier = Modifier.size(120.dp),
            )
            Text(
                text = "Busca productos, marcas y mas",
                maxLines = 1,
                fontSize = 20.sp,
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = searchQuery.value,
                    onValueChange = {
                        searchQuery.value = it
                    },
                    label = {
                        Text(
                            text = SEARCH_TEXT_FIELD_HINT,
                            maxLines = 1
                        )
                    },
                    isError = isSearchShaking.value && searchQuery.value.isEmpty(),
                    modifier = Modifier
                        .weight(if (isPortrait) 8.75f else 9.3f)
                        .padding(end = 8.dp)
                        .offset(x = if (isSearchShaking.value) shakeOffset.dp else 0.dp),
                )
                Row(
                    modifier = Modifier
                        .weight(if (isPortrait) 1.25f else 0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            isSearchShaking.value = searchQuery.value.isEmpty()
                            viewModel?.apply {
                                searchQuery(searchQuery.value)
                            }
                        },
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                            .background(
                                colorResource(id = R.color.mercado_yellow),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                            contentDescription = SEARCH_BUTTON_DESCRIPTION,
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            }
        }

        viewModel?.let {
            val searching = viewModel.search.collectAsState()
            if(searching.value){
                showLoading()
            }

            val errorMessage = viewModel.errorMessage.collectAsState()
            LaunchedEffect(errorMessage.value) {
                launch {
                    if(errorMessage.value.isNotEmpty()){
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = errorMessage.value,
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        }
    }
}

