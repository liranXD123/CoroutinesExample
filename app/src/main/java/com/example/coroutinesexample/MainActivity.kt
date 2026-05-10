package com.example.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object{
        private val TAG = MainActivity::class.java.name
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fetchBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                fetchAndShowUser()
            }
        }
        /*GlobalScope.launch {
            println("Default : I'm working in thread ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.IO) {
            println("IO : I'm working in thread ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Main) {
            println("Main : I'm working in thread ${Thread.currentThread().name}")
        }
        GlobalScope.launch(newSingleThreadContext("MyOwnThread")) { // will get its
            val own = null
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
*/
    }
    private suspend fun fetchAndShowUser(){
        val user1 = fetchUserOne()


        val user2 = fetchUserTwo()
        Log.d(TAG, "User two fetched")
        showUser(user1.await(), user2.await())
    }
    private suspend fun fetchUserOne()= GlobalScope.async(Dispatchers.IO) {
        Thread.sleep(4000)
        User("Moshe", "niggersXD123@gmail.com")
    }

    private suspend fun fetchUserTwo()= GlobalScope.async(Dispatchers.IO) {
        Thread.sleep(4000)
        User("Misha", "ballsXD@gmail.com")
    }

    private fun showUser(user1: User, user2:User){
        binding.userOneTextview.text = user1.toString()
        binding.userTwoTextview.text = user2.toString()
    }
}

data class User(val name:String, val email:String)