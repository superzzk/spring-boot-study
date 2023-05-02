package com.zzk.springboot.example.di.dependson;


public class FileWriter {
    
    public FileWriter(File file){
        file.setText("write");
    }
    
    public void writeFile(){}
}
