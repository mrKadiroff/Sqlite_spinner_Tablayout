package com.example.home_1

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Dunyo_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.ActivityChildBinding
import com.example.home_1.databinding.ActivityChildDunyoBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact

class Child_dunyoActivity : AppCompatActivity() {
    lateinit var binding: ActivityChildDunyoBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var allList: ArrayList<Contact>
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var list: ArrayList<String>
    lateinit var rvAdapters: Dunyo_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildDunyoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tool)

        binding.tool.setTitleTextColor(Color.WHITE)

//        binding.back.setOnClickListener {
//            finish()
//        }

        myDbHelper = MyDbHelper(this)
        val id = intent.getIntExtra("aydi", 0)
        val contact = myDbHelper.getContactById(id)

        binding.bir.text = contact.name
        binding.ikki.text = contact.phoneNumber

        allList = myDbHelper.getAllContacts()

        list= ArrayList()

        list.add("Asosiy")
        list.add("Dunyo")
        list.add("Ijtimoiy")

        rvAdapters = Dunyo_adapter(allList, object : Dunyo_adapter.OnItemClickListener{
            override fun onItemContactClick(contact: Contact) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(contact: Contact, position: Int, imageView: ImageView) {
                TODO("Not yet implemented")
            }

        })


        binding.editText1.setOnClickListener {
            val alertDialog= AlertDialog.Builder(this)
            val dialog = alertDialog.create()
            val dialogView= MyDialogBinding.inflate(LayoutInflater.from(this),null,false)
            spinnerAdapter= SpinnerAdapter(list)
            dialogView.Spinner.adapter=spinnerAdapter

            dialogView.sarlavha.setText(contact.name)
            dialogView.matn.setText(contact.phoneNumber)
            dialogView.Spinner.setSelection(1)
            dialogView.itemTitleTv.text = "O'zgartirish"

            dialogView.saveText.setOnClickListener {
                val type = list[dialogView.Spinner.selectedItemPosition]
                val name = dialogView.sarlavha.text.toString().trim()
                val descriptions = dialogView.matn.text.toString().trim()

                if (name.isNotEmpty() && descriptions.isNotEmpty()){
                    contact.kategoriya=list[dialogView.Spinner.selectedItemPosition]
                    contact.name=dialogView.sarlavha.text.toString().trim()
                    contact.phoneNumber=dialogView.matn.text.toString().trim()

                    myDbHelper.updateContact(contact)
                    binding.bir.text = "${contact.name}"
                    binding.ikki.text = "${contact.phoneNumber}"

                    if (dialogView.Spinner.selectedItem == "Ijtimoiy"){
                        finish()
                    }
                    if (dialogView.Spinner.selectedItem == "Asosiy"){
                        finish()
                    }

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

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        allList.clear()
        allList.addAll(myDbHelper.getAllContacts())
        rvAdapters.notifyDataSetChanged()
    }
}