package com.brewery.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConnectionUtils {

    public static void close(FileInputStream fileInputStream){
        try {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(FileOutputStream fileOutputStream){
        try {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
