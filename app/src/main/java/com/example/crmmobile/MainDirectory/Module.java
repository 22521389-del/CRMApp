package com.example.crmmobile.MainDirectory;

public class Module {
    private String name;
    private int iconimg;

    public Module(String name, int iconimg){
        this.iconimg = iconimg;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconimg() {
        return iconimg;
    }

    public void setIconimg(int iconimg) {
        this.iconimg = iconimg;
    }
}
