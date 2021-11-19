package com.example.home_1.db

import com.example.home_1.models.Contact

interface DatabaseService {

    fun addContact(contact: Contact)

    fun deleteContact(contact: Contact)

    fun updateContact(contact: Contact):Int

    fun getContactById(id:Int): Contact

    fun getAllContacts(): List<Contact>
}