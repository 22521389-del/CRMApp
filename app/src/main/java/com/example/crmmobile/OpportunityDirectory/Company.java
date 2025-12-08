package com.example.crmmobile.OpportunityDirectory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "companies")
public class Company {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter - Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name; // rất quan trọng để Dropdown hiển thị đúng name
    }
}
