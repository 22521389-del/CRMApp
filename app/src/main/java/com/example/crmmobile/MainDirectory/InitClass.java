package com.example.crmmobile.MainDirectory;

public class InitClass {
    public static int getIconNhanVien(int nhanvien){
        int result;
        if(nhanvien == 1){
            result = 0;
        }
        else if (nhanvien == 2){
            result  = 1;
        }
        else if (nhanvien == 3){
            result  = 2;
        }
        else if (nhanvien == 4){
            result  = 3;
        }
        else {
            result = 4;
        }
        return result;
    }
}
