package com.sc.meleetest.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.sc.meleetest.R

@Composable
fun showLoading(){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_grey)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        val strokeWidth = 5.dp
        CircularProgressIndicator(
            modifier = Modifier
                .semantics {
                    stateDescription = "Loading"
                }
                .drawBehind {
                    drawCircle(
                        Color.Blue,
                        radius = size.width / 2 - strokeWidth.toPx() / 2,
                        style = Stroke(strokeWidth.toPx())
                    )
                },
            color = Color.LightGray,
            strokeWidth = strokeWidth
        )
    }
}