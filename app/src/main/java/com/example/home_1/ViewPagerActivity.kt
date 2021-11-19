package com.example.home_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.home_1.databinding.ActivityMainBinding
import com.example.home_1.databinding.ActivityViewPagerBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.fragments.IjtimoiyFragment
import com.example.home_1.models.Contact
import com.example.home_1.view_adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPagerBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Contact>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        myDbHelper = MyDbHelper(this)
        list = myDbHelper.getAllContacts()


        val tabLayout=findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2=findViewById<ViewPager2>(R.id.view_pager_2)

        val adapter=ViewPagerAdapter(supportFragmentManager,lifecycle)

        viewPager2.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Asosiy"
                }
                1->{
                    tab.text="Dunyo"
                }
                2->{
                    tab.text="Ijtimoiy"
                }
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemView = item.itemId

        if (itemView == R.id.add){
            val dialog = AlertDialog.Builder(this@ViewPagerActivity)
            val myDialogBinding =
                MyDialogBinding.inflate(layoutInflater, null, false)
            dialog.setView(myDialogBinding.root)
            dialog.setPositiveButton(
                "Saqlash"
            ) { dialog, which ->
                val Sarlavha = myDialogBinding.sarlavha.text.toString()
                val Matn = myDialogBinding.matn.text.toString()
                val contact = Contact(Sarlavha, Matn)
                myDbHelper.addContact(contact)



            }

            dialog.show()
        }
        return false
    }
}