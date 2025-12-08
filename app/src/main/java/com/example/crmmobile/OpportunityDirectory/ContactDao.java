package com.example.crmmobile.OpportunityDirectory;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT id, full_name FROM contacts")
    List<Contact> getAllContacts();
}
