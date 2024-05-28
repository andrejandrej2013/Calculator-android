package com.example.myapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.database.Calculation
import com.example.myapplication.database.CalculationViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CalculatorScreen(navController: NavController, viewModel: CalculationViewModel) {
    var expression by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = expression,
            onValueChange = { expression = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            try {
                val calculationResult = eval(expression.text).toString()
                result = calculationResult
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)
                viewModel.addCalculation(Calculation(expression = expression.text, result = calculationResult, createdAt = formatted))
            } catch (e: Exception) {
                result = "Error"
            }
        }) {
            Text("Calculate")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result:",
            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
        )
        Text(
            text = result,
            style = TextStyle(fontSize = 24.sp, color = Color.Black),
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun eval(expr: String): Double {
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
    }.parse()
}
