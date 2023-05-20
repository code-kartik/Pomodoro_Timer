package com.example.pomodoro
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isWorkTime = false
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        supportActionBar?.hide()
        binding.timerTextView.text = "02:00"

        binding.startButton.setOnClickListener {
            startTimer()
        }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isWorkTime) {
            startWorkTimer()
        } else {
            startBreakTimer()
        }
    }

    private fun startWorkTimer() {
        val pomodoroTimeInMillis = 2 * 60 * 1000 // 25 minutes

        countDownTimer = object : CountDownTimer(pomodoroTimeInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.timerTextView.text = "00:00"
                isWorkTime = false
                startBreakTimer()
            }
        }

        countDownTimer.start()
        isWorkTime = true
    }

    private fun startBreakTimer() {
        val breakTimeInMillis = 1 * 60 * 1000

        countDownTimer = object : CountDownTimer(breakTimeInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
                binding.timerTextView.setTextColor(Color.parseColor("#777777")) // Set the text color
            }

            override fun onFinish() {
                binding.timerTextView.text = "00:00"
                isWorkTime = true
                startWorkTimer()
            }
        }
        countDownTimer.start()
        isWorkTime = false
    }


    private fun resetTimer() {
        if (isWorkTime) {
            countDownTimer.cancel()
            isWorkTime = false
        }
        binding.timerTextView.text = "02:00"
    }
}
