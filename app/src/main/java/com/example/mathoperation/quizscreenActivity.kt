package com.example.mathoperation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mathoperation.databinding.ActivityMainBinding
import com.example.mathoperation.databinding.ActivityQuizscreenBinding

class quizscreenActivity : AppCompatActivity() {
    lateinit var quizscreenBinding: ActivityQuizscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        quizscreenBinding=ActivityQuizscreenBinding.inflate(layoutInflater)
        setContentView(quizscreenBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        quizscreenBinding.button.setOnClickListener {
            var intent=Intent(this@quizscreenActivity,questionActivity::class.java)
            intent.putExtra("operation", "+")
            startActivity(intent)
        }
        quizscreenBinding.button2.setOnClickListener {
            var intent=Intent(this@quizscreenActivity,questionActivity::class.java)
            intent.putExtra("operation", "-")
            startActivity(intent)
        }
        quizscreenBinding.button3.setOnClickListener {
            var intent=Intent(this@quizscreenActivity,questionActivity::class.java)
            intent.putExtra("operation", "*")
            startActivity(intent)
        }
    }
}