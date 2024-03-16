package com.zzk.spring.core.di.dependson;

public class FileReader {

    public FileReader(File file) {
        file.setText("read");
    }
    
    public void readFile() {}
}
