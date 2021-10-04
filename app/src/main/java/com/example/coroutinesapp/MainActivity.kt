package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adviceText: TextView
    private lateinit var newAdviceButton: Button
    private lateinit var pauseButton: Button
    private var stop: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newAdviceButton = findViewById(R.id.btnNewAdvice)
        adviceText = findViewById(R.id.tvAdvice)
        pauseButton = findViewById(R.id.btnPause)

        newAdviceButton.setOnClickListener {
            stop = false
            CoroutineScope(IO).launch{
                while(!stop) {
                    delay(500)
                    getNewAdvice()
                }
            }
        }

        pauseButton.setOnClickListener { stop = true }
    }

    private suspend fun getNewAdvice() {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<Advice?>? = apiInterface!!.getNewAdvice()

        call?.enqueue(object : Callback<Advice?> {

            override fun onResponse(call: Call<Advice?>, response: Response<Advice?>) {
                val response: Advice? = response.body()
                val advice = response!!.slip!!.advice
                adviceText.text = "\"$advice\""
            }

            override fun onFailure(call: Call<Advice?>, t: Throwable) {
                call.cancel()
            }
        })
    }
}