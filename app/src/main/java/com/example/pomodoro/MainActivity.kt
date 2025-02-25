package com.example.pomodoro
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textoCronometro: TextView
    private lateinit var botaoIniciar: Button
    private var cronometro: CountDownTimer? = null

    // mudar o tempo
    private val tempoFixoMillis: Long = 25 * 60 * 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textoCronometro = findViewById(R.id.textoCronometro)
        botaoIniciar = findViewById(R.id.botaoIniciar)

        botaoIniciar.setOnClickListener {
            iniciarCronometro(tempoFixoMillis)
        }
    }

    private fun iniciarCronometro(tempoMillis: Long) {
        cronometro?.cancel()
        cronometro = object : CountDownTimer(tempoMillis, 1000) {
            override fun onTick(millisRestante: Long) {
                val minutos = millisRestante / 60000
                val segundos = (millisRestante % 60000) / 1000
                textoCronometro.text = String.format("%02d:%02d", minutos, segundos)
            }
            override fun onFinish() {
                textoCronometro.text = "00:00"
                vibrarCelular()
            }
        }.start()
    }

    private fun vibrarCelular() {
        val vibrador = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val efeitoVibracao = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrador.vibrate(efeitoVibracao)
        } else {
            vibrador.vibrate(1000)
        }
    }

}
