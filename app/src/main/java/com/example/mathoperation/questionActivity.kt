package com.example.mathoperation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mathoperation.databinding.ActivityQuestionBinding
import kotlin.random.Random
import android.os.CountDownTimer
import android.widget.Toast


class questionActivity : AppCompatActivity() {
    lateinit var questionBinding: ActivityQuestionBinding
    private var correctAnswer: Int = 0
    private var score: Int = 0
    private var lives: Int = 3
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        questionBinding = ActivityQuestionBinding.inflate(layoutInflater)
        val view = questionBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        generateNewQuestion()

        questionBinding.Next.setOnClickListener {
            checkAnswer()
            questionBinding.answer.text.clear()
        }
    }
    private fun updateLivesUI() {
        questionBinding.life.text = "$lives"
    }
    private fun generateRandomValue(): Int {
        return Random.nextInt(1, 100)
    }
    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer=object:CountDownTimer(30000, 1000) { // 60000 milliseconds = 1 minute
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                questionBinding.counterclockwise.text = String.format("%02d:%02d", minutes, seconds)
                updateLivesUI()
            }

            override fun onFinish() {
                questionBinding.question.text = "Time up"
                lives--
                updateLivesUI()
                if (lives > 0) {
                    generateNewQuestion()
                } else {
                    handleGameOver()
                }
            }
        }.start()
    }
    private fun generateNewQuestion() {
        val operation = intent.getStringExtra("operation")
        val randomValue1 = generateRandomValue()
        val randomValue2 = generateRandomValue()
        val questionText: String

        when (operation) {
            "+" -> {
                questionText = "What is the addition of $randomValue1 and $randomValue2?"
                correctAnswer = randomValue1 + randomValue2
            }
            "-" -> {
                questionText = "What is the subtraction of $randomValue2 from $randomValue1?"
                correctAnswer = randomValue1 - randomValue2
            }
            "*" -> {
                questionText = "What is the multiplication of $randomValue1 and $randomValue2?"
                correctAnswer = randomValue1 * randomValue2
            }
            else -> {
                questionText = "Unknown operation."
                correctAnswer = 0
            }
        }
        questionBinding.question.text = questionText
        questionBinding.Next.isEnabled = true
        questionBinding.answer.text.clear()
        startTimer()
    }

    private fun checkAnswer() {
        val userAnswer = questionBinding.answer.text.toString()
        if (userAnswer.isNotEmpty()) {
            val userAnswerInt = userAnswer.toInt()
            if (userAnswerInt == correctAnswer) {
                score += 10
                questionBinding.scorecard.text = "$score"
                Toast.makeText(this, "Correct answer!", Toast.LENGTH_SHORT).show()
                generateNewQuestion()
            } else {
                lives--
                updateLivesUI()
                generateNewQuestion()
                if (lives > 0) {
                    Toast.makeText(this, "Wrong answer! Try again.", Toast.LENGTH_SHORT).show()
                } else {
                    handleGameOver()
                }
            }
        } else {
            Toast.makeText(this, "Please enter an answer.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleGameOver() {
        Toast.makeText(this, "Game over! You ran out of lives.", Toast.LENGTH_SHORT).show()
        var intent=Intent(this@questionActivity,ScoreActivity::class.java)
        intent.putExtra("Score","$score")
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}