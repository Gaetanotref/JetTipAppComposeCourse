package com.example.jettipapp.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent() {
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val splitNum = remember {
        mutableStateOf(1)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    BillForm(
        splitNum = splitNum,
        tipAmountState = tipAmountState,
        totalPerPersonState = totalPerPersonState
    ) {}

}