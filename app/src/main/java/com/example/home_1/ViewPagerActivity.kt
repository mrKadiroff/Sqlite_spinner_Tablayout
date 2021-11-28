package com.example.home_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.home_1.`interface`.SendMessage
import com.example.home_1.adapters.Ijtimoiy_adapter

import com.example.home_1.databinding.ActivityMainBinding
import com.example.home_1.databinding.ActivityViewPagerBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.fragments.AsosiyFragment
import com.example.home_1.fragments.DunyoFragment
import com.example.home_1.fragments.IjtimoiyFragment
import com.example.home_1.models.Contact
import com.example.home_1.view_adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity : AppCompatActivity() {
    lateinit var ijtimoiyAdapter: Ijtimoiy_adapter
    lateinit var binding: ActivityViewPagerBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Contact>
    lateinit var names: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        myDbHelper = MyDbHelper(this)
        list = myDbHelper.getAllContacts()
//        ijtimoiyAdapter = Ijtimoiy_adapter(list)

        val tabLayout=findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2=findViewById<ViewPager2>(R.id.view_pager_2)

        val adapter=ViewPagerAdapter(supportFragmentManager,lifecycle)

        viewPager2.adapter=adapter






//
//        val names: ArrayList<String> = ArrayList<String>()
//        names.add("Android")
//        names.add("Java")
//        names.add("Php")
//        names.add("C++")

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

          val mBuilder = AlertDialog.Builder(this)
            val layoutInflater:LayoutInflater = LayoutInflater.from(applicationContext)
            val mView: View = layoutInflater.inflate(R.layout.my_dialog, null)
            mBuilder.setTitle("Spinner in custom dialog")
            val mSpinner = mView.findViewById<Spinner>(R.id.Spinner)
            val Sarlavha = mView.findViewById<EditText>(R.id.sarlavha)
            val Matni = mView.findViewById<EditText>(R.id.matn)
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.restaurantList))
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinner.adapter = adapter
            mBuilder.setPositiveButton(
                "Save"
            ) { dialog, which ->

                title = "Kotlin app"
                val heading = Sarlavha.text.toString()
                val description = Matni.text.toString()
                val kategoriya = mSpinner.selectedItem.toString()
                val contact = Contact(heading, description, kategoriya)
                myDbHelper.addContact(contact)


                dialog.dismiss()
                val viewPager2=findViewById<ViewPager2>(R.id.view_pager_2)
                val mPagerAdapter =viewPager2.adapter
                val currentPosition = viewPager2.currentItem
                mPagerAdapter?.notifyDataSetChanged()
                viewPager2.adapter = null
                viewPager2.adapter =mPagerAdapter
                viewPager2.setCurrentItem(currentPosition)

            }
            mBuilder.setNegativeButton(
                "Dismiss"
            ) {dialog, which ->
                dialog.dismiss()
            }
            mBuilder.setView(mView)
            val dialog : AlertDialog = mBuilder.create()
            dialog.show()


        }
        return false






    }

    override fun onResume() {
        super.onResume()
        val viewPager2=findViewById<ViewPager2>(R.id.view_pager_2)
        val mPagerAdapter =viewPager2.adapter
        val currentPosition = viewPager2.currentItem
        mPagerAdapter?.notifyDataSetChanged()
        viewPager2.adapter = null
        viewPager2.adapter =mPagerAdapter
        viewPager2.setCurrentItem(currentPosition)
    }








}