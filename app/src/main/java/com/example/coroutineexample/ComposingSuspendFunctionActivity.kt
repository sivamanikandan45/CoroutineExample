package com.example.coroutineexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class ComposingSuspendFunctionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composing_suspend_function)

        val lazyBtn=findViewById<Button>(R.id.lazy)
        val sequentialBtn=findViewById<Button>(R.id.sequential)
        val concurrentBtn=findViewById<Button>(R.id.concurrent)

        lazyBtn.setOnClickListener {
            runBlocking {

                    val msgOne=async(start = CoroutineStart.LAZY) { getFirstMessage() }
                    val msgTwo=async(start = CoroutineStart.LAZY){ getSecondMessage() }
                    println("${msgOne.await()}")
                //println("Time taken to execute in sequential order: $timeTaken")
            }

        }

        sequentialBtn.setOnClickListener {
            runBlocking {
                val timeTaken=measureTimeMillis {
                    val msgOne=getFirstMessage()
                    val msgTwo=getSecondMessage()
                    println("$msgOne  $msgTwo")
                }
                println("Time taken to execute in sequential order: $timeTaken")
            }
        }

        concurrentBtn.setOnClickListener {
            runBlocking {
                val timeTaken=measureTimeMillis {
                    val msgOne=async { getFirstMessage() }
                    val msgTwo=async { getSecondMessage() }
                    println("${msgOne.await()} ${msgTwo.await()}")
                }
                println("Time taken to execute in sequential order: $timeTaken")
            }
        }
    }

    private suspend fun getSecondMessage(): String {
        println("Entering getSecondMessage() method")
        delay(1000)
        return "Second Message"
    }

    private suspend fun getFirstMessage(): String {
        println("Entering getFirstMessage() method")
        delay(1000)
        return "First Message"
    }
}