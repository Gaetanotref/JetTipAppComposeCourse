package com.example.jettipapp.ui.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainContent() {

    BillForm(){billAmt ->
        //TODO
        Log.d("AMT", "MainContent: $billAmt")

    }

}