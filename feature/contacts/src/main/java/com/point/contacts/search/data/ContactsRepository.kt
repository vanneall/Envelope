package com.point.contacts.search.data

import com.point.contacts.search.data.request.AddContactRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val contactsService2: ContactsService2) {

    suspend fun getContactsByName(name: String) = withContext(Dispatchers.IO) {
        if (name.isNotEmpty()) {
            contactsService2.fetchContactsByName(name = name)
        } else {
            Result.success(null)
        }
    }

    suspend fun addContact(id: String) = withContext(Dispatchers.IO) {
        contactsService2.postNewContact(AddContactRequest(id))
    }
}