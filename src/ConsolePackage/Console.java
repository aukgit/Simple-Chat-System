/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsolePackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Alim
 */
public class Console {

    public static void writeLine(Object o) {
        System.err.println(o);
    }

    public static void writeLine(char[] array) {
        System.err.println(array);
    }

    public static void writeLine(String s) {
        System.err.println(s);
    }

    public static void write(Object o) {
        System.err.print(o);
    }

    public static String readLine() {
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            return bufferRead.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Scanner can do everything.
     *
     * @return
     */
    public static Scanner getScanner() {
        return new Scanner(System.in);
    }

    public static float readFloat() {

        Scanner scanner = new Scanner(System.in);
        return scanner.nextFloat();

    }

    public static double readDouble() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }


    public static int readInt() {

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();

    }

    public static char readKey() {
        String str = readLine();
        if (str != null && str.length() > 0) {
            return str.charAt(0);
        }
        return '\0';
    }
}
