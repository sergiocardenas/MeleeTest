package com.sc.meleetest.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sc.meleetest.R
import com.sc.meleetest.utils.BACK_BUTTON_DESCRIPTION
import com.sc.meleetest.utils.EMPTY_SEARCH_MESSAGE
import com.sc.meleetest.utils.IMAGE_LOGO_DESCRIPTION
import com.sc.meleetest.utils.SEARCH_TITLE
import com.sc.meleetest.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(null){}
}
@Composable
fun SearchScreen(
    viewModel: SearchViewModel?,
    onBackPressed: () -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .weight(if (isPortrait) 1.25f else 0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                             onBackPressed()
                        },
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                colorResource(id = R.color.mercado_yellow),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = BACK_BUTTON_DESCRIPTION,
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(if (isPortrait) 8.75f else 9.3f)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = SEARCH_TITLE,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(start = 8.dp),
                    )
                }
            }

            if(viewModel!=null){
                viewModel.let { viewModel ->

                    val list = viewModel.searchList.collectAsState()
                    if(list.value.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    colorResource(id = R.color.background_grey)
                                ),
                        ) {
                            items(list.value) { item ->
                                SearchItem(
                                    item = item,
                                    onItemClick = viewModel::fetchDetail
                                )
                            }
                        }
                    }else{
                        EmptyResult()
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

                    val searching = viewModel.search.collectAsState()
                    if(searching.value){
                        showLoading()
                    }
                }
            }else{
                EmptyResult()
            }
        }
    }
}

@Composable
fun EmptyResult(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.lg_mercadolibre),
            contentDescription = IMAGE_LOGO_DESCRIPTION,
            modifier = Modifier.size(60.dp),
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = EMPTY_SEARCH_MESSAGE,
            maxLines = 1,
            fontSize = 20.sp,
        )
    }
}