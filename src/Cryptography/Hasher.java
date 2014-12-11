/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import ConsolePackage.Console;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class Hasher {

    public static String getShA1Hash(String input) {
        MessageDigest md = null;
        byte[] output = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(input.getBytes());
            output = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasher.class.getName()).log(Level.SEVERE, null, ex);
        }

        String hash = bytesToHex(output);
        return hash;
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }
    
//    public static void main(String args[]){
//        Console.writeLine("Input for SH1: ");
//        String s = Console.readLine();
//        
//        Console.writeLine("SHA1 : (original) " + s + " (sha1) " + getShA1Hash(s) );
//    }
}
