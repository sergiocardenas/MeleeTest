package com.sc.meleetest.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sc.meleetest.R
import com.sc.meleetest.state.MLSearchItemState

@Preview
@Composable
fun SearchItemPreview (){
    SearchItem (
        MLSearchItemState(
            id = "1",
            title = "Motorola",
            thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
            currency_id = "USD",
            price = 10.0f,
            original_price = 10.0f,
        )
    ){

    }
}

@Composable
fun SearchItem (
    item: MLSearchItemState,
    onItemClick: (String) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .height(112.dp)
            .clickable  {
                  onItemClick(item.id)
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .height(100.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                
                ProductImage(url = item.thumbnail, size = 80.dp)
                
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top
                ) {

                    Text(
                        text = item.title,
                        maxLines = 1,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, bottom = 4.dp)
                    )

                    if(item.original_price>0){
                        Text(
                            text = item.currency_id+" "+item.original_price.toString(),
                            maxLines = 1,
                            fontSize = 12.sp,
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
                    }else{
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Text(
                        text = item.currency_id +" "+item.price.toString(),
                        maxLines = 1,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductImage(
    url: String,
    size: Dp,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .placeholder(R.drawable.lg_mercadolibre)
            .build(),
        contentDescription = null,
        error  = painterResource(id = R.drawable.ic_error),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
    )
}