package com.example.crmmobile.OpportunityDirectory;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;

    public ContactRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public List<Contact> getAllContacts() {
        // TODO: Khi DB xong → return contactDao.getAllContacts();

        List<Contact> fakeList = new ArrayList<>();
        fakeList.add(new Contact(1, "John Doe"));
        fakeList.add(new Contact(2, "Jane Smith"));
        fakeList.add(new Contact(3, "Alice Johnson"));
        fakeList.add(new Contact(4, "Michael Brown"));

        return fakeList;
    }
}
