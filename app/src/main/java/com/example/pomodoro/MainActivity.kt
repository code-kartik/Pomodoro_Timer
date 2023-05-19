package com.example.pomodoro
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isTimerRunning = false
    private lateinit var countDownTimer: CountDownTimer
    val time = 0.500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        supportActionBar?.hide()

        binding.startButton.setOnClickListener {
            startTimer(time)
        }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer(time: Double) {
        if (isTimerRunning) return

        val pomodoroTimeInMillis = time * 60 * 1000 // 25 minutes

        countDownTimer = object : CountDownTimer(pomodoroTimeInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                val breakTimer = 0.250
                binding.timerTextView.setTextColor(777777)
                isTimerRunning = true
                startTimer(breakTimer)
            }
        }

        countDownTimer.start()
        isTimerRunning = true
    }

    private fun resetTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel()
            isTimerRunning = false
        }
        binding.timerTextView.text = "00:30"
    }
}
