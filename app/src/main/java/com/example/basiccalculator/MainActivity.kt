package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvInput : TextView ?= null
    private var lastNumeric = false
    private var lastDot = false
    private var tvResult:TextView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        val digits = view as Button
        tvInput?.append(digits.text)
        lastNumeric = true
        lastDot = false
    }

    fun onCLR(view: View) {
        tvInput?.text = ""
    }
    fun onDecimal(view: View){
        //If the last input was a numeric digit and the last input was not a dot, then:
        //Append a dot to the text input.
        //Set the lastNumeric flag to false (to prevent consecutive dots or digits).
        //Set the lastDot flag to true (indicating that a dot was just added).
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }
    fun onOperators(view: View){
        // last input was a no. and no operator was added then add an operator
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){   // it(charsequence) is converted to string
                val operator = view as Button
                tvInput?.append(operator.text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    private fun isOperatorAdded(value: String): Boolean {
        // if value startswith "-" then it will not be consider as operator and is ignores but if value has "+" "-" "/" "*" they are operators
        return if(value.startsWith("-")){
            false
        }else(value.contains("-") || value.contains("+") || value.contains("×") || value.contains("÷") || value.contains("%"))
    }
    fun onBackspace(view: View){
        val tvValue = tvInput?.text.toString()
        val new = tvValue.substring(0, tvValue.length - 1)
        tvInput?.text = new
    }
    fun onPercentage(view: View) {
        tvInput?.text?.let {
            if (lastNumeric) {
                val inputValue = it.toString().toDouble()
                val percentageValue = inputValue / 100.0
                tvInput?.text = percentageValue.toString()
                lastNumeric = true
                lastDot = false
            }
        }
    }
    fun onEqual(view: View){
        tvInput?.text?.let {
            if(lastNumeric){
                var tvValue = tvInput?.text.toString()  // converts the value of tvInput to string
                var begin = ""
                try {
                    if(tvValue.startsWith("-")){
                        // -100-2 -> - and 100-2
                        begin = "-"   // -
                        tvValue = tvValue.substring(1) // 100 - 2
                    }
                    when{
                        tvValue.contains("-") -> {
                            // 100 - 2
                            val splitStrings: List<String> = tvValue.split("-") // 100  2
                            var num1 = splitStrings[0] //100
                            val num2 = splitStrings[1] //2
                            if(begin.isNotEmpty()){
                                num1 = begin + num1
                            }
                            val result = num1.toDouble() - num2.toDouble()
                            tvInput?.text = removeZeroAfterDot(result.toString())
                        }
                        tvValue.contains("+") -> {
                            // 100+2
                            val splitStrings: List<String> = tvValue.split("+") // 100  2
                            var num1 = splitStrings[0] //100
                            val num2 = splitStrings[1] //2
                            if(begin.isNotEmpty()){
                                num1 = begin + num1
                            }
                            val result = num1.toDouble() + num2.toDouble() // 100+2
                            tvInput?.text = removeZeroAfterDot(result.toString())
                        }
                        tvValue.contains("÷") -> {
                            val splitStrings: List<String> = tvValue.split("÷") // 100  2
                            var num1 = splitStrings[0] //100
                            val num2 = splitStrings[1] //2
                            if(begin.isNotEmpty()){
                                num1 = begin + num1
                            }
                            val result = num1.toDouble() / num2.toDouble() // 100/2
                            tvInput?.text = removeZeroAfterDot(result.toString())
                        }
                        tvValue.contains("×") -> {
                            val splitStrings: List<String> = tvValue.split("×") // 100  2
                            var num1 = splitStrings[0] //100
                            val num2 = splitStrings[1] //2
                            if(begin.isNotEmpty()){
                                num1 = begin + num1
                            }
                            val result = num1.toDouble() * num2.toDouble() // 100/2
                            tvInput?.text = removeZeroAfterDot(result.toString())
                        }
                    }
                }catch (e: ArithmeticException){
                    e.printStackTrace()   // printStackTrace() on an exception object, it prints a trace of method calls that shows the path the program took to reach the point where the exception was thrown
                }
            }
        }
    }
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}