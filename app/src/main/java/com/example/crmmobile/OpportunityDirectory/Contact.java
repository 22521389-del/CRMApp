package com.example.crmmobile.OpportunityDirectory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String full_name;

    public Contact(int id, String full_name) {
        this.id = id;
        this.full_name = full_name;
    }

    // Getter - Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFull_name() { return full_name; }
    public void setFull_name(String full_name) { this.full_name = full_name; }

    @Override
    public String toString() {
        return full_name; // Dropdown sẽ hiển thị đúng tên
    }
}
