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
public class DatabaseRunnableComponents extends JFrame {

    private static final long serialVersionUID = 1L;

    private DatabaseQuery _db;

    //<editor-fold defaultstate="collapsed" desc="Fields">
    public MsgBox messageBox;
    public JFrame PreviousForm;
    public JFrame NextForm;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Personal Methods added by Alim Ul Karim">
    public void Println(String title, String msg) {
        System.out.println(title + " : " + msg);
    }

    public void Sysout(String title, String msg) {
        this.Println(title, msg);
    }

    public void Sysout(String msg) {
        System.out.println(msg);
    }

    // </editor-fold>
    public DatabaseRunnableComponents() {
        _db = new DatabaseQuery();
    }

    /**
     * @return the _db
     */
    public DatabaseQuery getDb() {
        return _db;
    }

    /**
     * @param _db the _db to set
     */
    public void setDb(DatabaseQuery _db) {
        this._db = _db;
    }

    public void moveToRow(int rowNumber) {
        _db.moveToRow(rowNumber);

    }

}
