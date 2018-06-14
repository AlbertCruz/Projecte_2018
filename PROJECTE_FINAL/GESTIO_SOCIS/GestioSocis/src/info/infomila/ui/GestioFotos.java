/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.ui;

import java.io.*;

/**
 *
 * @author alber
 */
public class GestioFotos {
    FileInputStream in;
    FileOutputStream out;
    File file;
    
    public GestioFotos(){
    
    }
    
    public byte[] openFile(File file){
        byte[] byteFile = new byte[1024*5000];
        try{
            in = new FileInputStream(file);
            in.read(byteFile);
        }catch(Exception ex){
        
        }
        return byteFile;
    }
    
    public String saveFile(File file, byte[] bytesFile){
        String result = null;
        try{
            out = new FileOutputStream(file);
            out.write(bytesFile);
            result = "OK";
        }catch(Exception ex){
            
        }
        return result;
    }
}
