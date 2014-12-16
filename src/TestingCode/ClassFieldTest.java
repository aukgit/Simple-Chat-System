/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingCode;

import ConsolePackage.Console;
import java.lang.reflect.Field;

/**
 *
 * @author Alim
 */
public class ClassFieldTest {

    public static void main(String args[]) throws Exception {
        FieldTest ft = new FieldTest();
        Class ftClass = ft.getClass();

        Field f1 = ftClass.getField("pub");
        f1.set(ft, "reflecting on life");
        String str1 = (String) f1.get(ft);
        System.out.println("pub field: " + str1);

        Field f2 = ftClass.getField("parentPub");
        Console.writeLine(f2.getType().getName());
        f2.set(ft, "again");
        String str2 = (String) f2.get(ft);
        System.out.println("\nparentPub field: " + str2);

//        try {
//            System.out.println("\nThis will throw a NoSuchFieldException");
//            Field f3 = ftClass.getField("pro");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
