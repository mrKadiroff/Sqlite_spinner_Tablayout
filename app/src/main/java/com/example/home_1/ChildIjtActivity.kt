package com.example.home_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_1.databinding.ActivityChildIjtBinding
import com.example.home_1.db.MyDbHelper

class ChildIjtActivity : AppCompatActivity() {
    lateinit var binding: ActivityChildIjtBinding
    lateinit var myDbHelper: MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildIjtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)
        val id = intent.getIntExtra("oydi", 0)
        val contact = myDbHelper.getContactById(id)

        binding.pervi.text = contact.name
        binding.vtoroy.text = contact.phoneNumber
    }
}