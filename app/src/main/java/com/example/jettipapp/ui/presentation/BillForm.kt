package com.example.jettipapp.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.jettipapp.ui.components.InputField
import com.example.jettipapp.ui.utilis.calculateTotalPerPerson
import com.example.jettipapp.ui.utilis.calculateTotalTip
import com.example.jettipapp.widgets.RoundIconButtons

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    splitNum: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    var sliderPositionState by remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState * 100).toInt()


    TopHeader(totalPerPerson = totalPerPersonState.value)
    Spacer(modifier = Modifier.height(20.dp))

    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary.copy(0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(
                        totalBillState.value.trim()
                    )
                    keyboardController?.hide()
                }
            )
            if (validState) {
                Row(
                    modifier = Modifier
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split", modifier = Modifier.align(
                            alignment = CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButtons(
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                if (splitNum.value > 1) splitNum.value-- else splitNum.value = 1
                                totalPerPersonState.value = calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitNum.value,
                                    tipPercentage = tipPercentage
                                )
                            })
                        Text(
                            text = "${splitNum.value}",
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButtons(imageVector = Icons.Default.Add, onClick = {
                            splitNum.value++
                            totalPerPersonState.value = calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitNum.value,
                                tipPercentage = tipPercentage
                            )
                        })
                    }


                }
                //Tip Row
                Row(
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Tip",
                        modifier = Modifier.align(alignment = CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(150.dp))
                    Text(
                        text = "$ ${tipAmountState.value}",
                        modifier = Modifier.align(alignment = CenterVertically)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$tipPercentage%")
                    Spacer(modifier = Modifier.height(20.dp))
                    //Slider
                    Slider(
                        value = if (totalBillState.value.isNotEmpty()) sliderPositionState else 0f,
                        onValueChange = { newVal ->
                            sliderPositionState = newVal
                            tipAmountState.value = calculateTotalTip(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage
                            )
                            totalPerPersonState.value = calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitNum.value,
                                tipPercentage = tipPercentage
                            )

                        },
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    )
                }
            } else {
                Box(modifier = Modifier) {

                }
            }
        }
    }

}


