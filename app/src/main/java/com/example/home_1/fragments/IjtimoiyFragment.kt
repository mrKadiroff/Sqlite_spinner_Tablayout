package com.example.home_1.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.home_1.ChildIjtActivity
import com.example.home_1.Child_dunyoActivity
import com.example.home_1.R
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.FragmentIjtimoiyBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact
import com.google.android.material.tabs.TabLayout


class IjtimoiyFragment : Fragment() {

    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var binding: FragmentIjtimoiyBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var ijtimoiyList: ArrayList<Contact>
    lateinit var list: ArrayList<Contact>
    lateinit var rvAdapters: Ijtimoiy_adapter
    lateinit var spinnerBasicList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIjtimoiyBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(binding.root.context)

        ijtimoiyList = ArrayList()
        list = ArrayList()

        ijtimoiyList = myDbHelper.getAllContacts()


        spinnerBasicList = ArrayList()
        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")

        val kateg = "Ijtimoiy"

        ijtimoiyList.forEach {
            if (it.kategoriya == kateg) {
                list.add(it)
            }
        }

        rvAdapters = Ijtimoiy_adapter(list, object : Ijtimoiy_adapter.OnItemClickListener {
            override fun onItemContactClick(contact: Contact) {
                val intent = Intent(binding.root.context, ChildIjtActivity::class.java)
                intent.putExtra("oydi", contact.id)
                startActivity(intent)
            }

            override fun onItemClick(contact: Contact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(binding.root.context, imageView)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit -> {
                            val alertDialog = AlertDialog.Builder(binding.root.context)
                            val dialog = alertDialog.create()
                            val dialogView = MyDialogBinding.inflate(
                                LayoutInflater.from(binding.root.context),
                                null,
                                false
                            )
                            spinnerAdapter = SpinnerAdapter(spinnerBasicList)
                            dialogView.Spinner.adapter = spinnerAdapter

                            dialogView.sarlavha.setText(contact.name)
                            dialogView.matn.setText(contact.phoneNumber)
                            dialogView.Spinner.setSelection(2)

                            dialogView.saveText.setOnClickListener {
                                val type = spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                val name = dialogView.sarlavha.text.toString()
                                val descriptions = dialogView.matn.text.toString()

                                if (name.isNotEmpty() && descriptions.isNotEmpty()) {
                                    contact.kategoriya=spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                    contact.name=dialogView.sarlavha.text.toString().trim()
                                    contact.phoneNumber=dialogView.matn.text.toString().trim()
                                    myDbHelper.updateContact(contact)
                                    list[position] = contact
                                    list.clear()

                                    var allBasic = myDbHelper.getAllContacts()

                                    for (contact in allBasic){
                                        if (contact.kategoriya == spinnerBasicList[2]) {
                                            list.add(contact)
                                        }
                                    }


                                    binding.ijtRv.adapter = rvAdapters
                                    rvAdapters.notifyItemInserted(list.size)
                                    rvAdapters.notifyItemChanged(position)
                                    rvAdapters.notifyItemRangeInserted(position,list.size)
                                    rvAdapters.notifyItemRangeChanged(position, list.size)
                                    dialog.dismiss()


                                }
                            }
                            dialogView.notText.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.setView(dialogView.root)
                            dialog.show()

                        }
                        R.id.delete -> {
                            val alertDialog = AlertDialog.Builder(binding.root.context)
                            alertDialog.setMessage("Xabarni o'chirmoqchimisiz?")
                            alertDialog.setPositiveButton(
                                "O'chirish"
                            ) {p0, p1 ->
                                myDbHelper.deleteContact(contact)
                                list.remove(contact)
                                rvAdapters.notifyItemRemoved(position)
                                rvAdapters.notifyItemRangeChanged(position, list.size)
                            }
                            alertDialog.setNegativeButton(
                                "Bekor qilish"
                            ) {p0, p1 ->}
                            alertDialog.show()
                        }


                    }
                    true
                }
                popupMenu.show()
            }
        })

        binding.ijtRv.adapter = rvAdapters



        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        list.clear()
        var allBasic = myDbHelper.getAllContacts()
        for (contact in allBasic) {
            if (contact.kategoriya == spinnerBasicList[2]) {
                list.add(contact)
            }
            rvAdapters.notifyDataSetChanged()
        }
    }
}




