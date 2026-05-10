package com.example.coroutinesexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fetchBtn.setOnClickListener {
            fetchAndShowUser()
        }
        GlobalScope.launch {
            println("Default : I'm working in thread ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.IO) {
            println("IO : I'm working in thread ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Main) {
            println("Main : I'm working in thread $ {Thread.currentThread().name}")
        }
        GlobalScope.launch(newSingleThreadContext("MyOwnThread")) { // will get its
            val own = null
            println("newSingleThreadContext: I'm working in thread $ {Thread.currentThread().name}")
        }

    }
    private fun fetchAndShowUser(){
        val user = fetchUser()
        showUser(user)
    }
    private fun fetchUser(): User {
        Thread.sleep(3000)
        return User("Moshe", "niggersXD123@gmail.com")
    }

    private fun showUser(user: User){
        binding.userOneTextview.text = user.toString()
    }
}

data class User(val name:String, val email:String)