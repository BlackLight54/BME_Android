package hu.bme.aut.android.contacts.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.contacts.databinding.ItemContactBinding
import hu.bme.aut.android.contacts.model.Contact

class ContactsAdapter : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(itemCallback) {

    private var contactList = emptyList<Contact>()

    var itemClickListener: ContactItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.tvContactName.text = contact.name
        holder.tvPhoneNumber.text = contact.number
        holder.contact = contact
    }

    fun setContacts(contacts: List<Contact>) {
        contactList += contacts
        submitList(contactList)
    }

    inner class ContactViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivContactImage: ImageView = binding.ivContactImage
        val tvContactName: TextView = binding.tvContactName
        val tvPhoneNumber: TextView = binding.tvPhoneNumber

        var contact: Contact? = null

        init {
            itemView.setOnClickListener {
                contact?.let { itemClickListener?.onItemClick(it) }
            }
        }
    }

    interface ContactItemClickListener {
        fun onItemClick(contact: Contact)
    }

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }
}