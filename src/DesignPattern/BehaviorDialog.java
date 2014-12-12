/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DesignPattern;

/**
 *
 * @author Alim
 */
public class BehaviorDialog extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;

    public BehaviorDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Add behavior");
        setLocationRelativeTo(this);
    }
}
