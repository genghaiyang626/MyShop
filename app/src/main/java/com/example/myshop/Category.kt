package com.example.myshop

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myshop.ui.theme.MyShopTheme

@Composable
fun Category(
    modifier: Modifier = Modifier,
    ShopViewModel: MyShopViewModel = viewModel()
) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = "商品分类：",
            modifier = modifier
                .padding(start = 10.dp, top = 10.dp)
        )

        Row(
            modifier = modifier
                .defaultMinSize(minHeight = 48.dp)
                .background(Color.Cyan),
//            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            for (item in ShopViewModel.title) {
                Box(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 5.dp)
                        .height(25.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MyCheckBox(
                            checked = item.checked,
                            onCheckedChange = {
                                    checked -> ShopViewModel.changeTitleChecked(item,checked)
                            }
                        )
                        Spacer(
                            modifier = modifier
                                .width(5.dp)
                        )
                        Text(text = stringResource(id = item.text))
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    MyShopTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Category()
        }
    }
}
