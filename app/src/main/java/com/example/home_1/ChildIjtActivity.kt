package com.example.home_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.ActivityChildIjtBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact

class ChildIjtActivity : AppCompatActivity() {
    lateinit var binding: ActivityChildIjtBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var allList: ArrayList<Contact>
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var list: ArrayList<String>
    lateinit var rvAdapters: Ijtimoiy_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildIjtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tool)

        binding.tool.setTitleTextColor(Color.WHITE)



        myDbHelper = MyDbHelper(this)
        val id = intent.getIntExtra("oydi", 0)
        val contact = myDbHelper.getContactById(id)

        binding.pervi.text = contact.name
        binding.vtoroy.text = contact.phoneNumber

        allList = myDbHelper.getAllContacts()

        list= ArrayList()

        list.add("Asosiy")
        list.add("Dunyo")
        list.add("Ijtimoiy")

        binding.editText2.setOnClickListener {
            val alertDialog= AlertDialog.Builder(this)
            val dialog = alertDialog.create()
            val dialogView= MyDialogBinding.inflate(LayoutInflater.from(this),null,false)
            spinnerAdapter= SpinnerAdapter(list)
            dialogView.Spinner.adapter=spinnerAdapter

            dialogView.sarlavha.setText(contact.name)
            dialogView.matn.setText(contact.phoneNumber)
            dialogView.Spinner.setSelection(2)
            dialogView.itemTitleTv.text = "O'zgartirish"

            dialogView.saveText.setOnClickListener {
                if (dialogView.Spinner.selectedItem == "Dunyo"){
                    finish()
                }
                if (dialogView.Spinner.selectedItem == "Asosiy"){
                    finish()
                }
                val type = list[dialogView.Spinner.selectedItemPosition]
                val name = dialogView.sarlavha.text.toString().trim()
                val descriptions = dialogView.matn.text.toString().trim()

                if (name.isNotEmpty() && descriptions.isNotEmpty()){
                    contact.kategoriya=list[dialogView.Spinner.selectedItemPosition]
                    contact.name=dialogView.sarlavha.text.toString().trim()
                    contact.phoneNumber=dialogView.matn.text.toString().trim()

                    myDbHelper.updateContact(contact)
                    binding.pervi.text = "${contact.name}"
                    binding.vtoroy.text = "${contact.phoneNumber}"



                    dialog.dismiss()
                }


            }
            dialogView.notText.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setView(dialogView.root)
            dialog.show()


        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRestart() {
        super.onRestart()
        allList.clear()
        allList.addAll(myDbHelper.getAllContacts())
        rvAdapters.notifyDataSetChanged()
    }
}