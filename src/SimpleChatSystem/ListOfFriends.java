/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

import CurrentDb.CommonData;
import CurrentDb.Tables.UserTable;
import DesignPattern.JFrameInheritable;
import java.sql.Array;

/**
 *
 * @author Alim
 */
public class ListOfFriends extends JFrameInheritable {
//    private static ListOfFriends Form = new ListOfFriends();

    private static final long serialVersionUID = 1L;
    private boolean _isAdmin;
    private UserTable _user; // load from previous form , startup

    /**
     * Creates new form ListOfFriends
     */
  
    
    public ListOfFriends(UserTable u){
        initComponents();
        setUser(u);
        if (getUser().IsAdmin) {
            adminConfigBtn.setVisible(true);
        } else {
            adminConfigBtn.setVisible(false);
        }
        
        for (String item : CommonData.getActiveStateList()) {
            this.UserActiveState.add(item);
        }
  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        UsernameLabel = new javax.swing.JLabel();
        UsterstatusLabel = new javax.swing.JLabel();
        UserPicLabel = new javax.swing.JLabel();
        UserActiveState = new java.awt.Choice();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        list1 = new java.awt.List();
        editProfile = new javax.swing.JLabel();
        adminConfigBtn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        UsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        UsernameLabel.setText("Username");

        UsterstatusLabel.setText("Status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(UsernameLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(UsterstatusLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UsernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UsterstatusLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        UserPicLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UserPicLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsCollections/base_picture_32.png"))); // NOI18N
        UserPicLabel.setText("Pic");
        UserPicLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPane1.setViewportView(jTextPane1);

        editProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsCollections/pencil_32.png"))); // NOI18N
        editProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                editProfileMouseReleased(evt);
            }
        });

        adminConfigBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsCollections/settings_32.png"))); // NOI18N
        adminConfigBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminConfigBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                adminConfigBtnMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(UserPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adminConfigBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProfile))
                    .addComponent(UserActiveState, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserPicLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editProfile)
                    .addComponent(adminConfigBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserActiveState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminConfigBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminConfigBtnMouseReleased
        // TODO add your handling code here:
        loadNewForm(new Admin(), true);
    }//GEN-LAST:event_adminConfigBtnMouseReleased

    private void editProfileMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProfileMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_editProfileMouseReleased

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice UserActiveState;
    private javax.swing.JLabel UserPicLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel UsterstatusLabel;
    private javax.swing.JLabel adminConfigBtn;
    private javax.swing.JLabel editProfile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private java.awt.List list1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initalizeTableName() {

    }

    /**
     * @return the _isAdmin
     */
    public boolean isAdmin() {
        return _isAdmin;
    }

    /**
     * @param isAdmin the _isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this._isAdmin = isAdmin;
    }

    /**
     * @return the _user
     */
    public UserTable getUser() {
        return _user;
    }

    /**
     * @param _user the _user to set
     */
    public void setUser(UserTable _user) {
        this._user = _user;
    }
}
