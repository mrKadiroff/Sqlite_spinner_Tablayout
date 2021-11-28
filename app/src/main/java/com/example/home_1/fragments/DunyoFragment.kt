package com.example.home_1.fragments

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
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.home_1.ChildActivity
import com.example.home_1.Child_dunyoActivity
import com.example.home_1.R
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Dunyo_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.FragmentDunyoBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact


class DunyoFragment : Fragment() {

    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var rvAdapters: Dunyo_adapter
    lateinit var spinnerBasicList: ArrayList<String>

    lateinit var mContext : Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_dunyo, container, false)
        var binding =  FragmentDunyoBinding.bind(view)
        setHasOptionsMenu(true)
        spinnerBasicList = ArrayList()
        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")

        var List = ArrayList<Contact>()
        var database = MyDbHelper(mContext)
        var ValueList = database.getAllContacts()
        val kateg = "Dunyo"
        ValueList.forEach {
            if (it.kategoriya == kateg){
                List.add(it)
            }
        }
        rvAdapters = Dunyo_adapter(List, object:Dunyo_adapter.OnItemClickListener{
            override fun onItemContactClick(contact: Contact) {
                val intent = Intent(mContext, Child_dunyoActivity::class.java)
                intent.putExtra("aydi", contact.id)
                startActivity(intent)
            }

            override fun onItemClick(contact: Contact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(mContext, imageView)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit -> {
                            val alertDialog = AlertDialog.Builder(mContext)
                            val dialog = alertDialog.create()
                            val dialogView = MyDialogBinding.inflate(
                                LayoutInflater.from(mContext),
                                null,
                                false
                            )
                            spinnerAdapter = SpinnerAdapter(spinnerBasicList)
                            dialogView.Spinner.adapter = spinnerAdapter

                            dialogView.sarlavha.setText(contact.name)
                            dialogView.matn.setText(contact.phoneNumber)
                            dialogView.Spinner.setSelection(1)

                            dialogView.saveText.setOnClickListener {
                                val type = spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                val name = dialogView.sarlavha.text.toString()
                                val descriptions = dialogView.matn.text.toString()

                                if (name.isNotEmpty()) {
                                    contact.kategoriya=spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                    contact.name=dialogView.sarlavha.text.toString()
                                    contact.phoneNumber=dialogView.matn.text.toString()
                                    database.updateContact(contact)
                                    List[position] = contact
                                    List.clear()
//
//                                    var allBasic = database.getAllContacts()
//
//                                    for (contact in allBasic){
//                                        if (contact.kategoriya == spinnerBasicList[1]) {
//                                            List.add(contact)
//                                        }
//                                    }
//
//
                                    binding.dunyoRv.adapter = rvAdapters
                                    rvAdapters.notifyItemInserted(List.size)
                                    rvAdapters.notifyItemChanged(position)
                                    rvAdapters.notifyItemRangeInserted(position,List.size)
                                    rvAdapters.notifyItemRangeChanged(position, List.size)
                                    dialog.dismiss()


                                }
                            }
                            dialog.setView(dialogView.root)
                            dialog.show()

                        }
                        R.id.delete -> {
                            database.deleteContact(contact)
                            List.remove(contact)
                            rvAdapters.notifyItemRemoved(position)
                            rvAdapters.notifyItemRangeChanged(position, List.size)
                        }


                    }
                    true
                }
                popupMenu.show()
            }

        })
        binding.dunyoRv.adapter = rvAdapters
        val rv = binding.dunyoRv




        return view
    }

    override fun onResume() {
        super.onResume()
        var database = MyDbHelper(mContext)
        var List = ArrayList<Contact>()
        List.clear()
        var allBasic = database.getAllContacts()
        for (contact in allBasic) {
            if (contact.kategoriya == spinnerBasicList[1]) {
                List.add(contact)
            }

            rvAdapters.notifyDataSetChanged()

    }


    }
}



