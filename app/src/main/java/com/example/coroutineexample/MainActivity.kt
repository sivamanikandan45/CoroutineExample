package com.example.coroutineexample

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch {
            delay(3000L)
            setTextValue()
        }

        val isactivebtn=findViewById<Button>(R.id.isactive)
        val cancelandjoinbtn=findViewById<Button>(R.id.cancel_and_join)
        val timeoutbtn=findViewById<Button>(R.id.timeout)
        val timeoutOrNullbtn=findViewById<Button>(R.id.timeout_or_null)
        val next=findViewById<Button>(R.id.next)

        next.setOnClickListener {
            val intent= Intent(this,ComposingSuspendFunctionActivity::class.java)
            startActivity(intent)
        }

        isactivebtn.setOnClickListener {
            runBlocking {
                try{
                    val job:Job=GlobalScope.launch {
                        if(isActive){
                            for(i in 1..100){
                                //Thread.sleep(1)
                                print("$i ")
                            }
                            println("End of coroutine ${Thread.currentThread().name}")
                        }
                    }
                    job.cancelAndJoin()
                }catch (ex:CancellationException){
                    println("Cancellation exception caught")
                }finally {
                    withContext(NonCancellable){
                        delay(500)
                        println("Using suspend function inside the finally block")
                    }
                }

                println("Currently running in ${Thread.currentThread().name}")
            }


        }

        cancelandjoinbtn.setOnClickListener {
            runBlocking {
                try{
                    val job:Job=GlobalScope.launch {
                            for(i in 1..100){
                                print("$i ")
                                delay(100L)
                            }
                            println("End of coroutine ${Thread.currentThread().name}")
                    }
                    job.cancelAndJoin()
                }catch (ex:CancellationException){
                    println("Cancellation exception caught")
                }finally {
                    withContext(NonCancellable){
                        delay(500)
                        println("Using suspend function inside the finally block")
                    }
                }

                println("Currently running in ${Thread.currentThread().name}")
            }
        }

        timeoutbtn.setOnClickListener {
            runBlocking {
                try{
                    withTimeout(1000){
                        repeat(100_000){
                            print("Hello ")
                            delay(100)
                        }
                    }
                }catch (ex:TimeoutCancellationException)
                {
                    println("TimeoutCancellationException caught")
                }
            }

        }

        timeoutOrNullbtn.setOnClickListener {
            runBlocking {
                    val ans:String?=withTimeoutOrNull(1000){
                        repeat(100_000){
                            print("Hello ")
                            delay(100)
                        }
                        "Finished"
                    }
                println("$ans")
            }

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