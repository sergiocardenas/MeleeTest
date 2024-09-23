package com.sc.meleetest.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sc.meleetest.R
import com.sc.meleetest.utils.BACK_BUTTON_DESCRIPTION
import com.sc.meleetest.utils.DETAIL_TITLE
import com.sc.meleetest.viewmodel.DetailViewModel

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(null)
}
@Composable
fun DetailScreen(
    viewModel: DetailViewModel?,
    onBackPressed: () -> Unit = {}
) {
    val item = viewModel?.detailItem?.collectAsState() ?: remember { mutableStateOf(null) }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
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
                    text = DETAIL_TITLE,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 8.dp),
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                var textCondition = ""
                item.value?.apply {
                    textCondition = condition
                    if(sold_quantity>0){
                        textCondition+= " | $sold_quantity sold"
                    }
                }
                Text(
                    text = textCondition,
                    maxLines = 1,
                    fontSize = 14.sp,
                    color= Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 8.dp,
                            bottom = 4.dp
                        )
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = item.value?.title ?: "",
                    maxLines = 2,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                ProductImage(url = item.value?.thumbnail ?: "", size = 160.dp)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .padding(16.dp)
                ) {

                    var priceText = ""
                    item.value?.apply {
                        priceText = "$currency_id $price"
                    }
                    Text(
                        text = priceText,
                        maxLines = 1,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    if(item.value!=null && item.value!!.original_price>0){
                        item.value?.apply {
                            Text(
                                text = "$currency_id $original_price",
                                maxLines = 1,
                                fontSize = 14.sp,
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                color= Color.Gray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        end = 8.dp,
                                        bottom = 4.dp
                                    )
                            )
                        }
                    }else{
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
            Text(
                text = "Tags",
                maxLines = 1,
                fontSize = 14.sp,
                color= Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            if(item.value!=null && item.value!!.tags.isNotEmpty()){
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp)
                ){
                    item.value?.apply {
                        items(tags) { tag ->
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 4.dp, horizontal = 4.dp)
                                    .background(
                                        colorResource(id = R.color.mercado_yellow),
                                        shape = CircleShape
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = tag,
                                    maxLines = 1,
                                    fontSize = 14.sp,
                                    color= Color.Black,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}