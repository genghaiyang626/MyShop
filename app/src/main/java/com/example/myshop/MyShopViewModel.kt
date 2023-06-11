package com.example.myshop

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyShopViewModel:ViewModel() {
    private val _title = getShopTask()
    val uiState:Int = 0

    val title: List<ShopTask>
        get() = _title

    fun changeTitleChecked(item:ShopTask,checked:Boolean){
        title.find{
            it.id == item.id
        }?.let { title->
            title.checked=checked
        }
    }
}

val data =listOf(
    R.string.cb_women,
    R.string.cb_men,
    R.string.cb_shoes,
    R.string.cb_luggages
)

private fun getShopTask() =
    List(4){
        i ->
        ShopTask(i,data.get(i))
    }

class ShopTask(
    val id:Int=-1,
    @StringRes val text: Int,
    initialChecked:Boolean=false
){
//    init {
////        initialChecked = mutableStateOf(false)
//        initialChecked =true
//    }
    var checked by mutableStateOf(initialChecked)
//    var checked = initialChecked
}

//data class DrawableStringPair(
//    @StringRes val text: Int
//)



