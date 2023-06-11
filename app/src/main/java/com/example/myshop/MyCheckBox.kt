package com.example.myshop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.floor
import kotlin.math.max

private val checkSize = 20.dp

@Composable
fun MyCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = {},
) {
    var boxColor = Color.Black
    var borderColor = Color.Black
    val iconColor = Color.White

    val animateTest by animateFloatAsState(
        targetValue = if (checked) 0f else 1f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ),
//        finishedListener = {x -> Log.d(TAG, "MyTest: $x")}
    )

    if (checked) {
        boxColor = Color(218, 165, 32)
        borderColor = Color(218, 165, 32)
    }

    Box {
        Canvas(
            modifier = modifier
                .size(checkSize)
                .clipToBounds(),
            onDraw = {
                val strokeWidthPx = floor(2.dp.toPx())
                myDrawAnime(
                    boxColor = boxColor,
                    borderColor = borderColor,
                    radius = 2.dp.toPx(),
                    strokeWidth = strokeWidthPx,
                    value = animateTest,//animationProcess.value,
                    checked = checked
                )
            })
        IconButton(
            onClick = {
                if (onCheckedChange != null) {
                    onCheckedChange(!checked)
                }
            },
            content = {
                AnimatedVisibility(
                    visible = checked,
                    enter = expandHorizontally(
                        animationSpec = tween(
                            durationMillis = 250,
                            easing = LinearOutSlowInEasing
                        ),
                        expandFrom = Alignment.CenterHorizontally
                    ),
                    exit = shrinkHorizontally(
                        animationSpec = tween(
                            durationMillis = 150,
                            easing = LinearOutSlowInEasing
                        ),
                        shrinkTowards = Alignment.CenterHorizontally
                    ) + fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "单选",
                        tint = iconColor,
                        modifier = Modifier
                            .width(15.dp)
                            .scale(1.2f)
                    )
                }
            },
            modifier = modifier
                .size(checkSize)
        )


    }
}

private fun DrawScope.myDrawAnime(
    boxColor: Color,
    borderColor: Color,
    radius: Float,
    strokeWidth: Float,
    value: Float,
    checked: Boolean
) {
    val halfStrokeWidth = strokeWidth / 2.0f
    val stroke = Stroke(strokeWidth)
    val checkboxSize = size.width

    val borderWidth = checkboxSize - strokeWidth
    val boxWidth = checkboxSize - strokeWidth * 2

    //绘制边框
    drawRoundRect(
        borderColor,
        topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
        size = Size(borderWidth, borderWidth),
        cornerRadius = CornerRadius(radius - halfStrokeWidth),
        style = stroke
    )

    if (!checked) {
        //假定中心有一个正方形，当这个正方形边长逐渐增加时，四个矩形会被均匀挤压
        //当边长为boxWidth时候,四个矩形均缩小至不可见，
        //结论：每个矩形的长边和短边，变化速度是一致的
        //正方形每条边到画布（大正方形）的距离是相同，
        //正方形边长增加至最大，整个过程正方形每条边变化速率是一致的
        //矩形被挤压的过程中，短边x损失的长度和长边y增加的长度是相同，
        //当正方形边长为0时，矩形，x=boxWidth/2，y=boxWidth/2
        //当矩形被压平时，x=0，y=boxWidth，相同时间内，长边和短边的值的变化均为boxWidth/2

        val squareWidth = boxWidth * value

        //绘制背景 由四个矩形组成
        drawRoundRect(
            boxColor,
            topLeft = Offset(strokeWidth, strokeWidth),
            size = Size(boxWidth / 2 - squareWidth / 2, boxWidth / 2 + squareWidth / 2),
            cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
            style = Fill
        )

        drawRoundRect(
            boxColor,
            topLeft = Offset(strokeWidth + boxWidth / 2 - squareWidth / 2, strokeWidth),
            size = Size(boxWidth / 2 + squareWidth / 2, boxWidth / 2 - squareWidth / 2),
            cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
            style = Fill
        )

        drawRoundRect(
            boxColor,
            topLeft = Offset(strokeWidth, strokeWidth + boxWidth / 2 + squareWidth / 2),
            size = Size(boxWidth / 2 + squareWidth / 2, boxWidth / 2 - squareWidth / 2),
            cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
            style = Fill
        )

        drawRoundRect(
            boxColor,
            topLeft = Offset(
                strokeWidth + boxWidth / 2 + squareWidth / 2,
                strokeWidth + boxWidth / 2 - squareWidth / 2
            ),
            size = Size(boxWidth / 2 - squareWidth / 2, boxWidth / 2 + squareWidth / 2),
            cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
            style = Fill
        )

    } else {
        drawRoundRect(
            boxColor,
            topLeft = Offset(strokeWidth, strokeWidth),
            size = Size(checkboxSize - strokeWidth * 2, checkboxSize - strokeWidth * 2),
            cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
            style = Fill
        )
    }


}

@Preview(showBackground = true, widthDp = 50, heightDp = 50)
@Composable
fun CheckboxPre() {
    var checkedState by remember { mutableStateOf(false) }
    MyCheckBox(
        checked = checkedState,
        onCheckedChange = { x -> checkedState = x; },//check(x)
    )
}