package com.example.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object{
        private val TAG = MainActivity::class.java.name
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()
        val job = launch(start = CoroutineStart.LAZY){
            fetchAndShowUser()
        }
        binding.fetchBtn.setOnClickListener {
            job.start()
        }
        launch{
            try{
                delay(Long.MAX_VALUE)
            }catch (e: Exception){
                Log.d(TAG,"Coroutine cancelled")
            }
        }
    }
    private suspend fun fetchAndShowUser(){
        val user1 = fetchUserOne()


        val user2 = fetchUserTwo()
        Log.d(TAG, "User two fetched")
        showUser(user1.await(), user2.await())
    }
    private fun fetchUserOne()= async(Dispatchers.IO) {
        delay(4000)
        User("Moshe", "niggersXD123@gmail.com")
    }

    private fun fetchUserTwo()= async(Dispatchers.IO) {
        delay(4000)
        User("Misha", "ballsXD@gmail.com")
    }

    private fun showUser(user1: User, user2:User){
        binding.userOneTextview.text = user1.toString()
        binding.userTwoTextview.text = user2.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

data class User(val name:String, val email:String)