package com.example.pomodoro
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isTimerRunning = false
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()


        binding.timerTextView.setOnLongClickListener {
            if(isTimerRunning == true){
                countDownTimer.cancel()
                isTimerRunning = false
            }
            true
        }

        binding.startButton.setOnClickListener {
            startTimer()
        }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (isTimerRunning) return

        val pomodoroTimeInMillis = 25 * 60 * 1000 // 25 minutes

        countDownTimer = object : CountDownTimer(pomodoroTimeInMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.timerTextView.text = "00:00"
                isTimerRunning = false
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
        binding.timerTextView.text = "2:00"
    }
}
