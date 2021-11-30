package com.example.home_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.databinding.ActivityViewPagerBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
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

        when (item.itemId) {
            R.id.add -> {
                var alertDialog = AlertDialog.Builder(this)
                val dialog = alertDialog.create()
                val dialogView =
                    MyDialogBinding.inflate(LayoutInflater.from(this),null, false)
                val mSpinner = dialogView.Spinner
                val Sarlavha = dialogView.sarlavha
                val Matni = dialogView.matn
                            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.restaurantList))
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinner.adapter = adapter


                dialogView.saveText.setOnClickListener {

                                    title = "Kotlin app"
                val heading = Sarlavha.text.toString().trim()
                val description = Matni.text.toString().trim()
                val kategoriya = mSpinner.selectedItem.toString()
                val contact = Contact(heading, description, kategoriya)


                    if (heading.isNotEmpty() && description.isNotEmpty()){
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


                }

                dialogView.notText.setOnClickListener {
                    dialog.dismiss()
                }



                dialog.setView(dialogView.root)
                dialog.show()
            }
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