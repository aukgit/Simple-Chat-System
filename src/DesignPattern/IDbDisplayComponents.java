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
public interface IDbDisplayComponents {

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
    public void moveToRow(int rowNumber);

    //<editor-fold defaultstate="collapsed" desc="Getter and Setters">
    /**
     * @return the _db
     */
    public DatabaseQuery getDb();

    /**
     * @param _db the _db to set
     */
    public void setDb(DatabaseQuery _db);

    /**
     * @return the _messageBox
     */
    public MsgBox getMessageBox();

    /**
     * @param _messageBox the _messageBox to set
     */
    public void setMessageBox(MsgBox _messageBox);

    /**
     * @return the _previousForm
     */
    public JFrame getPreviousForm();

    /**
     * @param _previousForm the _previousForm to set
     */
    public void setPreviousForm(JFrame _previousForm);

    /**
     * @return the _nextForm
     */
    public JFrame getNextForm();

    /**
     * @param _nextForm the _nextForm to set
     */
    public void setNextForm(JFrame _nextForm);
//</editor-fold>
}
