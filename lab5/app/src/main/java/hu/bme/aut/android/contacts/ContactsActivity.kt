package hu.bme.aut.android.contacts

import android.Manifest
import android.Manifest.permission.READ_CONTACTS
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.contacts.adapter.ContactsAdapter
import hu.bme.aut.android.contacts.databinding.ActivityContactsBinding
import hu.bme.aut.android.contacts.model.Contact
import hu.bme.aut.android.contacts.util.getStringByColumnName
import java.lang.System.exit

// GV2RF8
class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        handleReadContactsPermission()

    }

    private fun loadContacts() {
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

    private fun showRationaleDialog(
        @StringRes title: Int = R.string.rationale_dialog_title,
        @StringRes explanation: Int,
        onPositiveButton: () -> Unit,
        onNegativeButton: () -> Unit = this::finish
    ) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(explanation)
            .setCancelable(false)
            .setPositiveButton(R.string.proceed) { dialog, id ->
                dialog.cancel()
                onPositiveButton()
            }
            .setNegativeButton(R.string.exit) { dialog, id -> onNegativeButton() }
            .create()
        alertDialog.show()
    }

    private fun handleReadContactsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                showRationaleDialog(
                    explanation = R.string.contacts_permission_explanation,
                    onPositiveButton = this::requestContactsPermission
                )

            } else {
                // No explanation needed, we can request the permission.
                requestContactsPermission()
            }
        } else {
            loadContacts()
        }
    }

    private fun requestContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_CONTACTS),
            PERMISSIONS_REQUEST_READ_CONTACTS
        )
    }
    companion object {
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    loadContacts()
                } else {
                    // permission denied! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }
}