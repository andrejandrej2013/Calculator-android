package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun GameScreen(navController: NavController) {
    var expression by remember { mutableStateOf(generateExpression()) }
    var answer by remember { mutableStateOf(TextFieldValue("")) }
    var feedback by remember { mutableStateOf("") }
    var correctAnswersInARow by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = feedback,
                color = if (feedback == "Correct!") Color.Green else Color.Red,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Solve: $expression",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            BasicTextField(
                value = answer,
                onValueChange = { answer = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            )
            Button(
                onClick = {
                    val correctAnswer = evalAsString(expression)
                    if (answer.text == correctAnswer) {
                        feedback = "Correct!"
                        correctAnswersInARow++
                    } else {
                        feedback = "Wrong! The correct answer is $correctAnswer"
                        correctAnswersInARow = 0
                    }
                    expression = generateExpression()
                    answer = TextFieldValue("")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    expression = generateExpression()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Change Expression")
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$correctAnswersInARow",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

fun generateExpression(): String {
    val a = Random.nextInt(1, 10)
    val b = Random.nextInt(1, 10)
    val operator = listOf("+", "-", "*", "/").random()
    return "$a$operator$b"
}

fun evalAsString(expr: String): String {
    return object : Any() {
        var pos = -1
        var ch = 0

        fun nextChar() {
            ch = if (++pos < expr.length) expr[pos].toInt() else -1
        }

        fun eat(charToEat: Int): Boolean {
            while (ch == ' '.toInt()) nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < expr.length) throw RuntimeException("Unexpected: " + ch.toChar())
            return x
        }

        fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                if (eat('+'.toInt())) x += parseTerm() // addition
                else if (eat('-'.toInt())) x -= parseTerm() // subtraction
                else return x
            }
        }

        fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                if (eat('*'.toInt())) x *= parseFactor() // multiplication
                else if (eat('/'.toInt())) x /= parseFactor() // division
                else return x
            }
        }

        fun parseFactor(): Double {
            if (eat('+'.toInt())) return parseFactor() // unary plus
            if (eat('-'.toInt())) return -parseFactor() // unary minus

            var x: Double
            val startPos = pos
            if (eat('('.toInt())) { // parentheses
                x = parseExpression()
                eat(')'.toInt())
            } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) { // numbers
                while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                x = expr.substring(startPos, pos).toDouble()
            } else {
                throw RuntimeException("Unexpected: " + ch.toChar())
            }

            return x
        }
    }.parse().let {
        if (it == it.toInt().toDouble()) it.toInt().toString() else it.toString()
    }
}
