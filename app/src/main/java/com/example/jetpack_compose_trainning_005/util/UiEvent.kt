package com.example.jetpack_compose_trainning_005.util

sealed class UiEvent{

    object PopBackStack : UiEvent()
    data class Navigate(val route:String):UiEvent()
    data class ShowSnackBar(
        val message:String,
        val action:String? =null
    ):UiEvent()
}
