package com.example.home_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_1.databinding.ActivityChildBinding
import com.example.home_1.databinding.ActivityChildDunyoBinding
import com.example.home_1.db.MyDbHelper

class Child_dunyoActivity : AppCompatActivity() {
    lateinit var binding: ActivityChildDunyoBinding
    lateinit var myDbHelper: MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildDunyoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)
        val id = intent.getIntExtra("aydi", 0)
        val contact = myDbHelper.getContactById(id)

        binding.bir.text = contact.name
        binding.ikki.text = contact.phoneNumber
    }
}