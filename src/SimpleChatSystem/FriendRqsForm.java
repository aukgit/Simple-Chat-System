/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

import ConsolePackage.Console;
import CurrentDb.TableColumns.ChatList;
import CurrentDb.TableColumns.FriendRequest;
import CurrentDb.TableColumns.User;
import CurrentDb.TableNames;
import CurrentDb.Tables.ChatListTable;
import CurrentDb.Tables.FriendRequestTable;
import CurrentDb.Tables.UserTable;
import Database.Components.IQueryType;
import DesignPattern.JFrameInheritable;
import OnlineServers.PictureUploader;
import OnlineServers.RelatedObjects.PictureSender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Alim
 */
public class FriendRqsForm extends JFrameInheritable {

    public UserTable currentUser;
    public ArrayList<UserTable> users;
    public ArrayList<FriendRequestTable> frdReqs;
    public ArrayList<Integer> usersIds;
    @SuppressWarnings("unchecked")
    DefaultListModel<String> displayModel = new DefaultListModel();

    ListOfFriends listOfFriends;

    private FriendRqsForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isNoFriendRequestExist() {
        return frdReqs == null || frdReqs.size() == 0;
    }

    /**
     * Creates new form FriendRqsForm
     */
    public FriendRqsForm(UserTable user, ListOfFriends _listOfFriends) {

        initComponents();
        this.listOfFriends = _listOfFriends;
        currentUser = user;
        loadFriendReqs();
        loadUsersBasedOnFrdReq();
        setModelToList();
        if (isNoFriendRequestExist() == false) {
            this.setTitle("Friend Requests Pending (" + frdReqs.size() + ")");
            friendRequestLabel.setText(this.getTitle());
            this.jList1.setSelectedIndex(0);
            listIndexChanged();

        }
    }

    public void acceptRequest() {
        this.getDb().setTableName(TableNames.CHATLIST);
        int row = 3;
        UserTable senderUser = getSelectedUserFromList();
        String columns[] = new String[row];
        String values[] = new String[row];

        columns[--row] = ChatList.OriginalUserID;
        values[row] = currentUser.UserID + "";

        columns[--row] = ChatList.RelatedUserID;
        values[row] = senderUser.UserID + "";

        columns[--row] = ChatList.AliasAs;
        values[row] = senderUser.Name;

        if (this.getDb().insertData(columns, values)) {
            row = 3;

            columns[--row] = ChatList.OriginalUserID;
            values[row] = senderUser.UserID + "";

            columns[--row] = ChatList.RelatedUserID;
            values[row] = currentUser.UserID + "";

            columns[--row] = ChatList.AliasAs;
            values[row] = currentUser.Name;
            if (this.getDb().insertData(columns, values)) {
                this.getMessageBox().show(this, senderUser.Name + " is now in your chatlist.", "Success");
                FriendRequestTable req = getSelectedReq();
                listOfFriends.refreshFriendList();
                req.IsAccept = true;

                isSeenRequestSetInDB(req);
                this.frdReqs.remove(req);

                return;
            }
        }
        this.getMessageBox().showError(this, "System problem can't add request.");
    }

    public void isSeenRequestSetInDB(FriendRequestTable req) {
        this.getDb().setTableName(TableNames.FRIEND_REQUEST);

        int accept = req.IsAccept ? 1 : 0;
        this.getDb().updateData(FriendRequest.IsAccept + "=" + accept + "," + FriendRequest.IsSeen + "=1", FriendRequest.FriendRequestID + "=" + req.FriendRequestID);
    }

    public void rejectRequest() {
        FriendRequestTable req = getSelectedReq();

        req.IsAccept = false;

        isSeenRequestSetInDB(req);
        this.frdReqs.remove(req);

    }

    public void loadFriendReqs() {
        this.getDb().setTableName(TableNames.FRIEND_REQUEST);
        this.getDb().setSpecialQueryFields_(false, FriendRequest.ToWhomUserID, FriendRequest.IsSeen);
        this.getDb().setSpecialQueryValues_(false, this.currentUser.UserID + "", "0");
        this.getDb().readData();
//        Console.writeLine(this.getDb().LastSQL);
        frdReqs = this.getDb().getResultsAsORM(new FriendRequestTable());
        usersIds = this.getDb().getSingleColumnValuesInt(FriendRequest.SenderUserID);
    }

    public void loadUsersBasedOnFrdReq() {
        if (isNoFriendRequestExist()) {
            return;
        }
        this.getDb().setTableName(TableNames.USER);
        String inQuery = this.getDb().getStringJoined(usersIds, ";", null, null);
        Console.writeLine(inQuery);

        this.getDb().setSpecialTypes_(false, IQueryType.IN_QUERY);
        this.getDb().readData(User.UserID, inQuery);
        users = this.getDb().getResultsAsORM(new UserTable());

        PictureUploader server = new PictureUploader();
        PictureSender picSender = new PictureSender(users, PictureSender.IAskPicture.Profile);
        try {
            picSender = server.clientSendingMethod(picSender);

        } catch (IOException ex) {
            Logger.getLogger(FriendRqsForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FriendRqsForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        users = picSender.getListOfUsers();

        users.stream().forEach((user) -> {
            for (FriendRequestTable req : frdReqs) {
                if (req.SenderUserID == user.UserID) {
                    req.SenderUser = user;
                    break;
                }
            }
        });
    }

    public void setModelToList() {
        if (isNoFriendRequestExist()) {
            return;
        }
        jList1.setModel(displayModel);
        for (FriendRequestTable req : frdReqs) {
            displayModel.addElement(req.SenderUser.Name);
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

        friendRequestLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        UsernameLabel = new javax.swing.JLabel();
        MessageLabel = new javax.swing.JLabel();
        AcceptBtn = new javax.swing.JButton();
        RejectBtn = new javax.swing.JButton();
        StatusLabel = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        ProfilePic = new javax.swing.JLabel();
        CloseBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        friendRequestLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        friendRequestLabel.setText("Friend Requests");

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        UsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        UsernameLabel.setText("Username");

        MessageLabel.setText("Message");
        MessageLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        MessageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        MessageLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        AcceptBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsCollections/112_Tick_Green_32x32_72.png"))); // NOI18N
        AcceptBtn.setText("Accept");
        AcceptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptBtnActionPerformed(evt);
            }
        });

        RejectBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsCollections/Delete_black_32x32.png"))); // NOI18N
        RejectBtn.setText("Reject");
        RejectBtn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        RejectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RejectBtnActionPerformed(evt);
            }
        });

        StatusLabel.setText("status");

        EmailLabel.setText("status");

        ProfilePic.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(friendRequestLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(UsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(EmailLabel)
                                                    .addComponent(StatusLabel))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addComponent(MessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(AcceptBtn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RejectBtn)))
                                .addGap(120, 120, 120))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CloseBtn)
                                .addGap(26, 26, 26))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(friendRequestLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(UsernameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StatusLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EmailLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(ProfilePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AcceptBtn)
                            .addComponent(RejectBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(CloseBtn)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public UserTable getSelectedUserFromList() {
        if (jList1.getSelectedIndex() > -1) {
            return frdReqs.get(jList1.getSelectedIndex()).SenderUser;
        }
        return null;
    }

    public FriendRequestTable getSelectedReq() {
        if (jList1.getSelectedIndex() > -1) {
            return frdReqs.get(jList1.getSelectedIndex());

        }
        return null;
    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        listIndexChanged();

    }//GEN-LAST:event_jList1ValueChanged

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        listOfFriends.startOrResumeThread();

        terminateCurrentForm(false);
    }//GEN-LAST:event_CloseBtnActionPerformed

    private void RejectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RejectBtnActionPerformed
        rejectRequest();
        if (frdReqs.size() == 0) {
            listOfFriends.startOrResumeThread();

            terminateCurrentForm(true);
        }
    }//GEN-LAST:event_RejectBtnActionPerformed

    private void AcceptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptBtnActionPerformed
        // TODO add your handling code here:
        acceptRequest();
        if (frdReqs.size() == 0) {
            listOfFriends.startOrResumeThread();
            terminateCurrentForm(true);
        }
    }//GEN-LAST:event_AcceptBtnActionPerformed
    public void listIndexChanged() {
        FriendRequestTable req = getSelectedReq();
        if (req != null) {
            UserTable user = req.SenderUser;
            user.displayUser(UsernameLabel, StatusLabel, EmailLabel, ProfilePic);
            this.MessageLabel.setText(req.Message);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AcceptBtn;
    private javax.swing.JButton CloseBtn;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JLabel MessageLabel;
    private javax.swing.JLabel ProfilePic;
    private javax.swing.JButton RejectBtn;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel friendRequestLabel;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initalizeTableName() {
        this.getDb().setTableName(TableNames.FRIEND_REQUEST);
    }
}
