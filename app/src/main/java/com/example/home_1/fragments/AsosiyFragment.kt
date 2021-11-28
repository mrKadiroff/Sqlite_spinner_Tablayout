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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_1.ChildActivity
import com.example.home_1.MainActivity
import com.example.home_1.R
import com.example.home_1.`interface`.SendMessage
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.FragmentAsosiyBinding
import com.example.home_1.databinding.FragmentDunyoBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact
import java.util.*
import kotlin.collections.ArrayList


class AsosiyFragment : Fragment() {

    //    private var _binding: FragmentAsosiyBinding? = null
//    private val binding get() = _binding!!
//    lateinit var root: View
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var rvAdapters: Asosiy_adapter
    lateinit var spinnerBasicList: ArrayList<String>
    lateinit var list: ArrayList<Contact>
    lateinit var asosiyList: ArrayList<Contact>
    lateinit var myDbHelper: MyDbHelper

    lateinit var binding: FragmentAsosiyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsosiyBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(binding.root.context)

        setHasOptionsMenu(true)

        asosiyList = ArrayList()
        list = ArrayList()

        spinnerBasicList = ArrayList()
        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")



        asosiyList = myDbHelper.getAllContacts()
        val kateg = "Asosiy"
        asosiyList.forEach {
            if (it.kategoriya == kateg) {
                list.add(it)
            }
        }

        list = myDbHelper.getAllContacts()

        rvAdapters = Asosiy_adapter(list, object : Asosiy_adapter.OnItemClickListener {
            override fun onItemContactClick(contact: Contact) {
                val intent = Intent(binding.root.context, ChildActivity::class.java)
                intent.putExtra("id", contact.id)
                startActivity(intent)
            }

            @SuppressLint("NotifyDataSetChanged")
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
                            dialogView.Spinner.setSelection(0)

                            dialogView.saveText.setOnClickListener {
                                val type = spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                val name = dialogView.sarlavha.text.toString()
                                val descriptions = dialogView.matn.text.toString()

                                if (name.isNotEmpty()) {
                                    contact.kategoriya =
                                        spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                    contact.name = dialogView.sarlavha.text.toString()
                                    contact.phoneNumber = dialogView.matn.text.toString()
                                    myDbHelper.updateContact(contact)
                                    list[position] = contact
                                    list.clear()

                                    var allBasic = myDbHelper.getAllContacts()

                                    for (contact in allBasic) {
                                        if (contact.kategoriya == spinnerBasicList[0]) {
                                            list.add(contact)
                                        }
                                    }

                                    binding.asosRv.adapter = rvAdapters
                                    rvAdapters.notifyItemInserted(list.size)
                                    rvAdapters.notifyItemChanged(position)
                                    rvAdapters.notifyItemRangeInserted(position, list.size)
                                    rvAdapters.notifyItemRangeChanged(position, list.size)
                                    dialog.dismiss()

                                    list.clear()
                                    allBasic = myDbHelper.getAllContacts()
                                    for (contact in allBasic) {
                                        if (contact.kategoriya == spinnerBasicList[0]) {
                                            list.add(contact)
                                        }

                                        rvAdapters.notifyDataSetChanged()

                                    }
                                }
                            }
                            dialog.setView(dialogView.root)
                            dialog.show()

                        }
                        R.id.delete -> {
                            myDbHelper.deleteContact(contact)
                            list.remove(contact)
                            rvAdapters.notifyItemRemoved(position)
                            rvAdapters.notifyItemRangeChanged(position, list.size)
                        }


                    }
                    true
                }
                popupMenu.show()
            }

        }, navController = NavController(binding.root.context))
        binding.asosRv.adapter = rvAdapters



        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        list.clear()
        var allBasic = myDbHelper.getAllContacts()
        for (contact in allBasic) {
            if (contact.kategoriya == spinnerBasicList[0]) {
                list.add(contact)
            }
            rvAdapters.notifyDataSetChanged()
        }
    }
}

