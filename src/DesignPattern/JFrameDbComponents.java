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
public class JFrameDbComponents extends JFrame implements IDbDisplayComponents {

    private static final long serialVersionUID = 1L;

 //<editor-fold defaultstate="collapsed" desc="Fields">
    private DatabaseQuery _db;
    
    private MsgBox _messageBox;
    private JFrame _previousForm;
    private JFrame _nextForm;
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
    public JFrameDbComponents() {
        _db = new DatabaseQuery();
    }


    public void moveToRow(int rowNumber) {
        getDb().moveToRow(rowNumber);

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

    /**
     * @return the _messageBox
     */
    public MsgBox getMessageBox() {
        return _messageBox;
    }

    /**
     * @param _messageBox the _messageBox to set
     */
    public void setMessageBox(MsgBox _messageBox) {
        this._messageBox = _messageBox;
    }

    /**
     * @return the _previousForm
     */
    public JFrame getPreviousForm() {
        return _previousForm;
    }

    /**
     * @param _previousForm the _previousForm to set
     */
    public void setPreviousForm(JFrame _previousForm) {
        this._previousForm = _previousForm;
    }

    /**
     * @return the _nextForm
     */
    public JFrame getNextForm() {
        return _nextForm;
    }

    /**
     * @param _nextForm the _nextForm to set
     */
    public void setNextForm(JFrame _nextForm) {
        this._nextForm = _nextForm;
    }


}
