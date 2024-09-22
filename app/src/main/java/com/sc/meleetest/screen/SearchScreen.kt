package com.sc.meleetest.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.sc.meleetest.R

@Composable
fun SearchScreen(
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                onSearchClick()
            },
            modifier = Modifier
                .width(150.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.mercado_yellow)
            )
        ) {
            Text(
                text = "Go to DetailFragment",
                color = colorResource(id = R.color.white)
            )
        }
    }
}