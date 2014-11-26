/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author Alim
 */
public class SQLError {
    // <editor-fold defaultstate="collapsed" desc="Error Message">
    public void ErrorMessage(String QueryText, String methodName) {
        System.out.println("{Error : " + QueryText + " \nMethod Name: [" + methodName + "]");
        System.out.println("}");
    }
    public void ErrorMessage(Exception e, String QueryText) {
        System.out.println("{SQL Syntax: " + QueryText);
        System.err.println(e);
        System.out.println("}");
    }

    public void ErrorMessage(Exception e, String QueryText, String MethodName) {
        System.out.println("{SQL Syntax: " + QueryText + " \nMethod Name: [" + MethodName + "]");
        System.err.println(e);
        System.out.println("}");

    }

    public void ErrorMessage(String QueryText) {
        System.out.println("{SQL Syntax: " + QueryText);
        System.out.println("}");
    }

    public void ErrorMessage(Exception e) {
        System.err.println("{" + e + "}");
    }
    // </editor-fold>
}
