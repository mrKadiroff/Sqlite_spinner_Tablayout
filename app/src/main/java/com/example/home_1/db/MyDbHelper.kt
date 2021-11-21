package com.example.home_1.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.home_1.models.Contact
import com.example.home_1.utils.Constant

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table ${Constant.TABLE_NAME} (${Constant.ID} integer not null primary key autoincrement unique, ${Constant.NAME} text not null, ${Constant.PHONE_NUMBER} text not null, ${Constant.CATEGORY} text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists ${Constant.TABLE_NAME}")
        onCreate(db)
    }

    override fun addContact(contact: Contact) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.NAME, contact.name)
        contentValues.put(Constant.PHONE_NUMBER, contact.phoneNumber)
        contentValues.put(Constant.CATEGORY, contact.kategoriya)
        database.insert(Constant.TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun deleteContact(contact: Contact) {
        val database = this.writableDatabase
        database.delete(Constant.TABLE_NAME, "${Constant.ID} = ?", arrayOf("${contact.id}"))
        database.close()
    }

    override fun updateContact(contact: Contact) :Int {
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Constant.ID, contact.id)
        contentValues.put(Constant.NAME, contact.name)
        contentValues.put(Constant.PHONE_NUMBER, contact.phoneNumber)
        contentValues.put(Constant.CATEGORY, contact.kategoriya)
        return database.update(
            Constant.TABLE_NAME,
            contentValues,
            "${Constant.ID} = ?",
            arrayOf(contact.id.toString())
        )
    }

    override fun getContactById(id: Int): Contact {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.TABLE_NAME,
            arrayOf(Constant.ID, Constant.NAME, Constant.PHONE_NUMBER, Constant.CATEGORY),
            "${Constant.ID} = ?",
            arrayOf("$id"),
            null, null, null
        )
        cursor?.moveToFirst()
        return Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
    }

    override fun getAllContacts(): ArrayList<Contact> {
        val list = ArrayList<Contact>()
        val query = "select * from ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                val kategoriya = cursor.getString(3)
                val contact = Contact(id, name, phone, kategoriya)
                list.add(contact)
            } while (cursor.moveToNext())
        }
        return list
    }
}