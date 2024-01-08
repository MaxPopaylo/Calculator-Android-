package com.example.calculator

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstNumber: Number? = null
    private var secondNumber: Number? = null
    private var result: Number? = null

    private var isFirstNum = false
    private var isSecondNum = false

    private var action: Action = Action.NONE
    private var activeButton: Action? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }


        with(binding) {

            //NUM BUTTONS
            btnOne.setOnClickListener {
                putNum(resources.getString(R.string.one))
                setInactiveForButton()
            }
            btnTwo.setOnClickListener {
                putNum(resources.getString(R.string.two))
                setInactiveForButton()
            }
            btnThree.setOnClickListener {
                putNum(resources.getString(R.string.three))
                setInactiveForButton()
            }
            btnFour.setOnClickListener {
                putNum(resources.getString(R.string.four))
                setInactiveForButton()
            }
            btnFive.setOnClickListener {
                putNum(resources.getString(R.string.five))
                setInactiveForButton()
            }
            btnSix.setOnClickListener {
                putNum(resources.getString(R.string.six))
                setInactiveForButton()
            }
            btnSeven.setOnClickListener {
                putNum(resources.getString(R.string.seven))
                setInactiveForButton()
            }
            btnEight.setOnClickListener {
                putNum(resources.getString(R.string.eight))
                setInactiveForButton()
            }
            btnNine.setOnClickListener {
                putNum(resources.getString(R.string.nine))
                setInactiveForButton()
            }
            btnZero.setOnClickListener {
                putNum(resources.getString(R.string.zero))
                setInactiveForButton()
            }
            btnDot.setOnClickListener {
                if (!tvResult.text.toString().contains(".")) {
                    if (!isFirstNum || (!isSecondNum && action != Action.NONE)){
                        tvResult.text = "0."
                    } else {
                        tvResult.text = "${binding.tvResult.text}."
                    }
                }
                setInactiveForButton()
            }

            //SUPPLEMENTARY BUTTONS
            btnReset.setOnClickListener {
                btnReset.text = resources.getString(R.string.ac)
                tvResult.text = "0"

                resetValues()
                result = null

                setInactiveForButton()
            }
            btnPlusMinus.setOnClickListener {
                val tvText = binding.tvResult.text
                tvResult.text = if (tvText.contains("-")) tvText.substring(1) else "-$tvText"
                setInactiveForButton()
            }
            btnPercent.setOnClickListener {
                if (tvResult.text.toString() != "0") {
                    tvResult.text = (tvResult.text.toString().toDouble() / 100).toString();
                }
                setInactiveForButton()
            }

            //ACTION BUTTON
            btnDivision.setOnClickListener {
                setAction(Action.DIVISION)
            }
            btnMultiply.setOnClickListener {
                setAction(Action.MULTIPLY)
            }
            btnPlus.setOnClickListener {
                setAction(Action.ADD)
            }
            btnMinus.setOnClickListener {
                setAction(Action.SUBTRACT)
            }
            btnEqual.setOnClickListener {
                count()
            }

        }


    }

    private fun count() {
        if (action != Action.NONE) {
            secondNumber = binding.tvResult.text.toString().toDouble()

            result = when (action) {
                Action.ADD -> (firstNumber ?: 0).toDouble() + (secondNumber ?: firstNumber!!).toDouble()
                Action.SUBTRACT -> (firstNumber ?: 0).toDouble() - (secondNumber ?: firstNumber!!).toDouble()
                Action.MULTIPLY -> (firstNumber ?: 0).toDouble() * (secondNumber ?: firstNumber!!).toDouble()
                Action.DIVISION -> (firstNumber ?: 0).toDouble() / (secondNumber ?: firstNumber!!).toDouble()
                else -> {
                    0
                }
            }
            result = roundOffDecimal(result.toString().toDouble())

            if (result.toString().length > 9) {
                binding.tvResult.text = String.format("%.4e", result.toString().toDouble())
            } else {
                binding.tvResult.text = if (result.toString().contains(".0")) result.toString()
                    .dropLast(2) else result.toString()
            }

            action = Action.NONE
            resetValues()
        }
    }

    private fun nextAction(act: Action) {
        action = act
        activeButton = act
        firstNumber = binding.tvResult.text.toString().toDouble()
        isFirstNum = false
    }

    private fun setAction(act: Action) {
        if (isSecondNum) {
            count()

            action = act
            activeButton = act
            firstNumber = result

            isSecondNum = false
        } else {
            setInactiveForButton()
            nextAction(act)
        }
        setActiveForButton(act)
    }

    private fun setInactiveForButton() {
        if (activeButton != null) {

            with(binding) {
                when (activeButton) {
                    Action.DIVISION -> {
                        btnDivision.setTextColor(resources.getColor(R.color.white))
                        btnDivision.backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.btn_orange))
                    }
                    Action.MULTIPLY -> {
                        btnMultiply.setTextColor(resources.getColor(R.color.white))
                        btnMultiply.backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.btn_orange))
                    }
                    Action.ADD -> {
                        btnPlus.setTextColor(resources.getColor(R.color.white))
                        btnPlus.backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.btn_orange))
                    }
                    Action.SUBTRACT -> {
                        btnMinus.setTextColor(resources.getColor(R.color.white))
                        btnMinus.backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(R.color.btn_orange))
                    }

                    else -> {}
                }
            }

            activeButton = null;
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    private fun setActiveForButton(act: Action) {
        if (activeButton != null && activeButton != act) {
            setInactiveForButton()
        }

        when (act) {
            Action.DIVISION -> {
                binding.btnDivision.setTextColor(resources.getColor(R.color.btn_orange))
                binding.btnDivision.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.white))
            }

            else -> {}
        }

        with(binding) {

            when (act) {
                Action.DIVISION -> {
                    btnDivision.setTextColor(resources.getColor(R.color.btn_orange))
                    btnDivision.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                }
                Action.MULTIPLY -> {
                    btnMultiply.setTextColor(resources.getColor(R.color.btn_orange))
                    btnMultiply.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                }
                Action.ADD -> {
                    btnPlus.setTextColor(resources.getColor(R.color.btn_orange))
                    btnPlus.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                }
                Action.SUBTRACT -> {
                    btnMinus.setTextColor(resources.getColor(R.color.btn_orange))
                    btnMinus.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                }

                else -> {}
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun putNum(num: String) {
        val tvText = binding.tvResult.text;

        binding.tvResult.text = if ((tvText == "0" || (!isFirstNum && !isSecondNum)) && tvText != "0." ) num
        else if (tvText == "-0") "-$num"
        else "$tvText$num"

        binding.btnReset.text = resources.getString(R.string.c)

        if (!isFirstNum && firstNumber == null) {
            isFirstNum = true
        }
        if (!isSecondNum && firstNumber != null) {
            isSecondNum = true
            isFirstNum = false
        }

    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    private fun resetValues() {
        isFirstNum = false
        isSecondNum = false
        firstNumber = null
        secondNumber = null
    }

}