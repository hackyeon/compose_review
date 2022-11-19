package com.hackyeon.compose_review

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private var _data: MutableState<String> = mutableStateOf("Hello")
    val data: State<String> = _data

    fun setData() {
        _data.value = "World"
    }


    private val _value = mutableStateOf("Hello World")
    val value: State<String> = _value


}