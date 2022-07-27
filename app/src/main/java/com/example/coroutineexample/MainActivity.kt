package com.example.coroutineexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch() {
            delay(3000L)
            setTextValue()
        }

    }

    private suspend fun setTextValue() {
        withContext(Main){
            val textView:TextView=findViewById(R.id.result)
            textView.text="Loaded Successfully"
            textView.setTextColor(Color.BLACK)
        }

    }
}