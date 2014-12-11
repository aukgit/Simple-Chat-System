/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsolePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Alim
 */
public class Console {

    
    public static void writeLine(Object o) {
        System.err.println(o);
    }

    public static void write(Object o) {
        System.err.print(o);
    }

    public static String readLine() {
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            return bufferRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String readInt() {
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static char readKey() {
        String str = readLine();
        if(str != null && str.length() > 0){
            return str.charAt(0);
        }
        return '\0';
    }
}
