package com.zzk.springboot.example.di.dependson;

public class FileReader {

    public FileReader(File file) {
        file.setText("read");
    }
    
    public void readFile() {}
}
