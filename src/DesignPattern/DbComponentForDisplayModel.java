/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DesignPattern;

import Database.Components.MsgBox;
import Database.DatabaseQuery;
import javax.swing.JFrame;

/**
 *
 * @author Alim
 */
public interface DbComponentForDisplayModel {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    DatabaseQuery _db;
    
    MsgBox MessageBox;
    JFrame PreviousForm;
    JFrame NextForm;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Personal Methods added by Alim Ul Karim">
    /**
     *
     * @param title
     * @param msg
     */
    public void Println(String title, String msg);

    public void Sysout(String title, String msg);

    public void Sysout(String msg);

    // </editor-fold>
    /**
     * @return the _db
     */
    public DatabaseQuery getDb();

    /**
     * @param _db the _db to set
     */
    public void setDb(DatabaseQuery _db);

    public void moveToRow(int rowNumber);
}
