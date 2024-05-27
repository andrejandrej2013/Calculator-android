package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.Screen
import kotlin.random.Random

@Composable
fun GameScreen(navController: NavController) {
    var expression by remember { mutableStateOf(generateExpression()) }
    var answer by remember { mutableStateOf(TextFieldValue("")) }
    var feedback by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Solve: $expression")
        BasicTextField(
            value = answer,
            onValueChange = { answer = it },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            if (answer.text == eval(expression).toString()) {
                feedback = "Correct!"
                expression = generateExpression()
                answer = TextFieldValue("")
            } else {
                feedback = "Try again."
            }
        }) {
            Text("Submit")
        }
        Text(feedback)
        Button(onClick = { navController.navigate(Screen.Calculator.route) }) {
            Text("Calculator")
        }
        Button(onClick = { navController.navigate(Screen.History.route) }) {
            Text("History")
        }
    }
}

fun generateExpression(): String {
    val a = Random.nextInt(1, 10)
    val b = Random.nextInt(1, 10)
    val operator = listOf("+", "-", "*", "/").random()
    return "$a$operator$b"
}
