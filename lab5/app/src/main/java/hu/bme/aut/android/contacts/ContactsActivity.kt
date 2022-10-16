package hu.bme.aut.android.contacts

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.annotation.RequiresPermission
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.contacts.adapter.ContactsAdapter
import hu.bme.aut.android.contacts.databinding.ActivityContactsBinding
import hu.bme.aut.android.contacts.model.Contact
import hu.bme.aut.android.contacts.util.getStringByColumnName

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val contactsAdapter = ContactsAdapter()
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = contactsAdapter
        contactsAdapter.setContacts(getAllContacts())
    }

    private fun ContentResolver.performQuery(
        @RequiresPermission.Read uri: Uri,
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): Cursor? {
        return query(uri, projection, selection, selectionArgs, sortOrder)
    }

    private fun getAllContacts(): List<Contact> {
        contentResolver.performQuery(
            uri = ContactsContract.Contacts.CONTENT_URI,
            sortOrder = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        ).use { contactResultCursor ->
            return if (contactResultCursor == null) {
                emptyList()
            } else {
                getContacts(contactResultCursor)
            }
        }
    }

    @SuppressLint("Range")
    private fun getContacts(contactCursor: Cursor): List<Contact> {
        val contactList = mutableListOf<Contact>()

        while (contactCursor.moveToNext()) {
            val hasPhoneNumber = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
            if (hasPhoneNumber != 0) {
                val id = contactCursor.getStringByColumnName(ContactsContract.Contacts._ID)
                val name = contactCursor.getStringByColumnName(ContactsContract.Contacts.DISPLAY_NAME)

                val contactPhoneNumber = getContactPhoneNumber(id)

                contactList += Contact(name, contactPhoneNumber)
            }
        }

        return contactList
    }

    private fun getContactPhoneNumber(id: String): String {
        contentResolver.performQuery(
            uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            selectionArgs = arrayOf(id)
        ).use { phoneResultCursor ->
            return if (phoneResultCursor == null || !phoneResultCursor.moveToNext()) {
                ""
            } else {
                phoneResultCursor.getStringByColumnName(ContactsContract.CommonDataKinds.Phone.NUMBER)
            }
        }
    }
}