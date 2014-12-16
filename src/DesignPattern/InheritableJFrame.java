/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DesignPattern;

import Database.Components.MsgBox;
import javax.swing.JFrame;



/**
 *
 * @author Alim
 */
public abstract class InheritableJFrame extends DatabaseRunnableComponents {
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private MsgBox messageBox;
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

    /**
     * Creates new form InheritableJFrame
     */
    public InheritableJFrame() {
        this.messageBox = new MsgBox();
        initalizeTableName();
        initComponents();
      
    }
    
    public abstract void initalizeTableName();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="Getter seeters">
   
    // <editor-fold defaultstate="collapsed" desc="Getter">
    /**
     * @return the messageBox
     */
    public MsgBox getMessageBox() {
        return messageBox;
    }

    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Setter">
    /**
     * @param messageBox the messageBox to set
     */
    public void setMessageBox(MsgBox messageBox) {
        this.messageBox = messageBox;
    }
//</editor-fold>
    //</editor-fold>
    /**
     * Also points to previous form
     * @param frame
     */
    public void loadNewForm(InheritableJFrame frame){
        
        frame.show(true);
        frame.PreviousForm = this;
        this.NextForm= frame;
    }

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
