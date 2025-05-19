package com.example.tmdb.presentation.models

import androidx.compose.ui.graphics.vector.ImageVector


data class BarItem<T : Any>(
    val label:String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badge:Boolean,
    val route: T,
    val badgeCount:Int=0,
)
