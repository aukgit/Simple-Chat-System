/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Components;

import Global.AppConfig;

/**
 *
 * @author Alim
 */
public class SQLError {

    // <editor-fold defaultstate="collapsed" desc="Error Message">
    public void ErrorMessage(String QueryText, String methodName) {
        if (AppConfig.IS_TESTING) {
            System.out.println("{Error : " + QueryText + " \nMethod Name: [" + methodName + "]");
            System.out.println("}");
        }
    }

    public void ErrorMessage(Exception e, String QueryText) {
        if (AppConfig.IS_TESTING) {
            System.out.println("{SQL Syntax: " + QueryText);
            System.err.println(e);
            System.out.println("}");
        }
    }

    public void ErrorMessage(Exception e, String QueryText, String MethodName) {
        if (AppConfig.IS_TESTING) {
            System.out.println("{SQL Syntax: " + QueryText + " \nMethod Name: [" + MethodName + "]");
            System.err.println(e);
            System.out.println("}");
        }

    }

    public void ErrorMessage(String QueryText) {
        if (AppConfig.IS_TESTING) {
            System.out.println("{SQL Syntax: " + QueryText);
            System.out.println("}");
        }
    }

    public void ErrorMessage(Exception e) {
        if (AppConfig.IS_TESTING) {
            System.err.println("{" + e + "}");
        }
    }
    // </editor-fold>
}
