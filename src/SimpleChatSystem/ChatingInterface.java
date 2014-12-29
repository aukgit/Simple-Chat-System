/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

import CurrentDb.CommonData;
import CurrentDb.TableColumns.Message;
import CurrentDb.TableNames;
import CurrentDb.Tables.MessageRecentTable;
import CurrentDb.Tables.UserTable;
import Database.DatabaseQuery;
import DesignPattern.JFrameInheritable;
import Mailer.OwnGmail;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class ChatingInterface extends JFrameInheritable {
    
    private static final long serialVersionUID = 1L;
    
    String finalSQL;
    
    private UserTable recevingUser;
    private UserTable sendingUser;
    OwnGmail mailer = new OwnGmail();

//    ArrayList<Integer> receiverUserIDs;
//    ArrayList<ToWhomAliasWhatTable> alias;
    ArrayList<MessageRecentTable> messages;
    private ListOfFriends listOfFriends;
    private String countSQL;
    int previousCountofMessages = -1;
    
    public boolean equals(int recevingUserId) {
        if (recevingUser.UserID == recevingUserId) {
            return true;
        }
        return false;
    }
    
    boolean loadAllPreviousChat;
    boolean firstTimeMsgLoads = true;
    long lastMsgPaintedId;
    
    int messageDisplayIndex = -1;
    
    ThreadRunner threadObject;
    Thread thread;
    
    DatabaseQuery dbMessagesSaving = new DatabaseQuery(TableNames.MESSAGE);

    /**
     * @return the recevingUser
     */
    public UserTable getRecevingUser() {
        return recevingUser;
    }

    /**
     * @param recevingUser the recevingUser to set
     */
    public void setRecevingUser(UserTable recevingUser) {
        this.recevingUser = recevingUser;
    }

    /**
     * @return the sendingUser
     */
    public UserTable getSendingUser() {
        return sendingUser;
    }

    /**
     * @param sendingUser the sendingUser to set
     */
    public void setSendingUser(UserTable sendingUser) {
        this.sendingUser = sendingUser;
    }

    /**
     * @return the listOfFriends
     */
    public ListOfFriends getListOfFriends() {
        return listOfFriends;
    }

    /**
     * @param listOfFriends the listOfFriends to set
     */
    public void setListOfFriends(ListOfFriends listOfFriends) {
        this.listOfFriends = listOfFriends;
    }
    
    class ThreadRunner implements Runnable {
        
        DatabaseQuery db;
        public ChatingInterface _chatMsg;
        public boolean running = true;
        
        ThreadRunner(ChatingInterface chatMsg) {
            db = new DatabaseQuery(TableNames.MESSAGE);
            this._chatMsg = chatMsg;
        }
        
        public void run() {
            while (running) {
                try {
                    _chatMsg.loadAllConversations();
                    Thread.sleep(5000);
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChatingInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
    }
//

    public void stopThread() {
        threadObject.running = false;
        thread.stop();
    }
    
    public String getMessageQuerySQL() {
        if (finalSQL == null) {
            String condition1 = Message.SendFromUserID + "=" + getSendingUser().UserID;
            String condition2 = Message.ReceiverUserId + "=" + getRecevingUser().UserID;
            
            String condition3 = Message.SendFromUserID + "=" + getRecevingUser().UserID;
            String condition4 = Message.ReceiverUserId + "=" + getSendingUser().UserID;
            
            String combinedCondition1 = "(" + condition1 + " AND " + condition2 + ")";
            String combinedCondition2 = "(" + condition3 + " AND " + condition4 + ")";
            String finalCondition = combinedCondition1 + " OR " + combinedCondition2;
            
            finalSQL = "SELECT * FROM messagerecent WHERE " + finalCondition;
        }
        return finalSQL;
    }
    
    public String getMessageQuerySQLForCount() {
        if (countSQL == null) {
            String condition1 = Message.SendFromUserID + "=" + getSendingUser().UserID;
            String condition2 = Message.ReceiverUserId + "=" + getRecevingUser().UserID;
            
            String condition3 = Message.SendFromUserID + "=" + getRecevingUser().UserID;
            String condition4 = Message.ReceiverUserId + "=" + getSendingUser().UserID;
            
            String combinedCondition1 = "(" + condition1 + " AND " + condition2 + ")";
            String combinedCondition2 = "(" + condition3 + " AND " + condition4 + ")";
            String finalCondition = combinedCondition1 + " OR " + combinedCondition2;
            
            countSQL = "SELECT COUNT(*) as CountX FROM message WHERE " + finalCondition;
        }
        return countSQL;
    }
    
    public void paintMessagesOnDisplay() {
        if (messages == null || messages.size() == 0) {
            return;
        }
        if (lastMsgPaintedId == 0) {
            for (int i = messages.size() - 1; i >= 0; i--) {
                MessageRecentTable msg = messages.get(i);
                
                if (i == 0) {
                    lastMsgPaintedId = msg.MessageID;
                }
                // printing in reverse becuase those are in recent order
                if (paintForOwn(msg) == false) {
                    paintForOthers(msg);
                }
            }
            
        } else {
            for (MessageRecentTable msg : messages) {
                if (lastMsgPaintedId == msg.MessageID) {
                    lastMsgPaintedId = messages.get(0).MessageID;
                    return;
                }
                if (paintForOwn(msg) == false) {
                    paintForOthers(msg);
                }
            }
        }
        
    }
    
    public void sendMailToUser(UserTable _sendingFromUser, String msg, UserTable _receivingUser) {
        String body = "";
        body += "Hello " + _receivingUser.Name + ", <br><br>";
        body += "How you doing? Your friend is sending you some text. <br>";
        body += "<br>";
        body += "<h3>A short message from " + _sendingFromUser.Name + "</h3>";
        body += "<blockquote>" + msg + "</blockquote>";
        body += "<br><br>";
        body += "Thanks ,<br>";
        body += "<strong>" + _sendingFromUser.Name + "</strong><br>";
        body += "<a href='mailto:" + _sendingFromUser.Email + "'>" + _sendingFromUser.Email + "</strong><br>";
        mailer.sendEmail(_sendingFromUser.Email, "A personal message from " + _sendingFromUser.Name, body);
    }
    
    public void sendMessage(String msg) {
        int row = 5;
        String columns[] = new String[row];
        String values[] = new String[row];
        
        columns[--row] = Message.Message;
        values[row] = msg;
        
        columns[--row] = Message.SendFromUserID;
        values[row] = getSendingUser().UserID + "";
        
        columns[--row] = Message.ReceiverUserId;
        values[row] = getRecevingUser().UserID + "";
        
        columns[--row] = Message.IsFileExit;
        values[row] = "0";
        
        columns[--row] = Message.IsSeen;
        values[row] = "0";
        
        dbMessagesSaving.insertData(columns, values);
        
        if (getRecevingUser().IsOnline == false) {
            this.SendingEmailLabel.setText("Sending email to the user...");
            Thread thread2 = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    sendMailToUser(getSendingUser(), msg, getRecevingUser());
                    
                }
            });
            thread2.start();
        }
        getRecevingUser().loadUserFromDb(getRecevingUser().UserID);
        this.SendingTextBox.setText("");
    }
    
    public boolean paintForOwn(MessageRecentTable msg) {
        if (msg.SendFromUserID == getSendingUser().UserID) {
            String put = "You wrote: " + msg.Message + "\n";
            this.MessageDisplayTextArea.append(put);
            return true;
        }
        return false;
    }
    
    public void paintForOthers(MessageRecentTable msg) {
        if (msg.SendFromUserID != getSendingUser().UserID) {
            String getAias = getRecevingUser().Name;
            this.MessageDisplayTextArea.append("" + getAias + " wrote: " + msg.Message + "\n");
        }
    }

//    public String getAlias(int userID) {
//        for (ToWhomAliasWhatTable aAlias : alias) {
//            if (aAlias.UserID == userID) {
//                return aAlias.AliasAs;
//            }
//        }
//        return null;
//    }
//    public void loadReceivers(int sessionId) {
//        this.getDb().setTableName(TableNames.CHAT_SESSION_RELATED_USERS);
//        this.getDb().readData("ChatSessionID", sessionId + "");
////
////        receiverUserIDs = this.getDb().getSingleColumnValuesInt(null);
////        if (receiverUserIDs.size() == 1) {
////            isSingleUser = true;
////        }
//        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
//    }
    public void loadAllConversations() {
        String countSQL2 = getMessageQuerySQLForCount();
        
        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
        this.getDb().readDataFullSQL(countSQL2);
        int countVal = 0;
        try {
            this.getDb().getRs().first();
            this.getDb().getRs().absolute(1);
            countVal = this.getDb().getRs().getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ChatingInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (countVal != previousCountofMessages) {
            String sql = getMessageQuerySQL();
            
            this.getDb().readDataFullSQL(sql);
            messages = this.getDb().getResultsAsORM(new MessageRecentTable());
            previousCountofMessages = messages.size();
            this.paintMessagesOnDisplay();
        }
//            firstTimeMsgLoads = false;
//        }
    }
    
    public void customInitalize() {
        initComponents();
        getRecevingUser().loadProfilePicFromServer();
        getRecevingUser().displayUser(UsernameLabel, StatusLabel, null, ProfilePic);
        threadObject = new ThreadRunner(this);
        thread = new Thread(threadObject);
        thread.start();
    }

    /**
     * Creates new form ChatingInterface
     */
    public ChatingInterface(UserTable user, UserTable recevingUser, ListOfFriends listOfFriendForm) {
        setSendingUser(user);
        setRecevingUser(recevingUser);
//        receiverUserIDs = new ArrayList<>(10);
//        receiverUserIDs.add(toWhomWantsToChatWithUserID);
//        addUserToThisSession(this.sessionID, senderUserID);
//        createNewSession();
//        for (Integer id : receiverUserIDs) {
//            addUserToThisSession(this.sessionID, id);
//        }
        setListOfFriends(listOfFriendForm);
        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
        customInitalize();
        this.setTitle(sendingUser.Name + " is chatting with '" + recevingUser.Name + "'");
    }
    
    public void makeCurrentUserAllMessageSeen() {
        String sql = "Update " + TableNames.MESSAGE
                + " SET IsSeen = 1 "
                + " WHERE ReceiverUserId = " + getSendingUser().UserID + " AND " + Message.SendFromUserID + "=" + getRecevingUser().UserID;
        dbMessagesSaving.ExecuteUpdateQueries(sql);
    }
//
//    public int createNewSession() {
//        int row = 3;
//        this.getDb().setTableName(TableNames.CHATSESSION);
//        String columns[] = new String[row];
//        String values[] = new String[row];
//
//        columns[--row] = ChatSession.IsActive;
//        values[row] = "1";
//
//        columns[--row] = ChatSession.IsSingleUser;
//        values[row] = "1";
//
//        columns[--row] = ChatSession.Timed;
//        values[row] = this.getDb().getCurrentInDbFormat();
//
//        this.getDb().insertData(columns, values);
//
//        this.getDb().setTableName("LastChatSessionID");
//        this.getDb().readData();
//        this.sessionID = Integer.parseInt(this.getDb().getValue(1, "ID"));
//
//        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
//        return this.sessionID;
//    }

//    public void addUserToThisSession(int sessionId, int userId) {
//        int row = 2;
//        this.getDb().setTableName(TableNames.CHAT_SESSION_RELATED_USERS);
//        String columns[] = new String[row];
//        String values[] = new String[row];
//
//        columns[--row] = ChatSessionRelatedUsers.ChatSessionID;
//        values[row] = this.sessionID + "";
//
//        columns[--row] = ChatSessionRelatedUsers.UserID;
//        values[row] = userId + "";
//
//        this.getDb().insertData(columns, values);
//
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        CancelBtn = new javax.swing.JButton();
        SendingTextBox = new javax.swing.JTextField();
        UsernameLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        SendBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        MessageDisplayTextArea = new javax.swing.JTextArea();
        ProfilePic = new javax.swing.JLabel();
        SendingEmailLabel = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        CancelBtn.setText("Close");
        CancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBtnActionPerformed(evt);
            }
        });

        SendingTextBox.setToolTipText("Send message to your friend\n");
        SendingTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MessageDisplayTextAreaMouseClicked(evt);
            }
        });
        SendingTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SendingTextBoxKeyReleased(evt);
            }
        });

        UsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        UsernameLabel.setText("Friend : Username");

        StatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        StatusLabel.setText("Status");

        SendBtn.setText("Send");
        SendBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SendBtn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        SendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendBtnActionPerformed(evt);
            }
        });

        MessageDisplayTextArea.setColumns(20);
        MessageDisplayTextArea.setRows(5);
        MessageDisplayTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clikedOnTextEditors(evt);
            }
        });
        jScrollPane3.setViewportView(MessageDisplayTextArea);

        ProfilePic.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addComponent(SendingTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(SendingEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CancelBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SendBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(ProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UsernameLabel)
                            .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 159, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(UsernameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(StatusLabel))
                    .addComponent(ProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SendingTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(CancelBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SendBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SendingEmailLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        UsernameLabel.getAccessibleContext().setAccessibleName("UsernameLabel");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnActionPerformed
        getListOfFriends().disconnectChattingInterface(this);
        terminateCurrentForm(true);

    }//GEN-LAST:event_CancelBtnActionPerformed

    private void SendingTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SendingTextBoxKeyReleased
        // TODO add your handling code here:

        if (evt.getKeyCode() == CommonData.ENTER_KEY) {
            finalSendMessage();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (messages != null && messages.size() > messageDisplayIndex) {
                if (messageDisplayIndex == -1) {
                    messageDisplayIndex = 0;
                }
                String msg = messages.get(messageDisplayIndex).Message;
                this.SendingTextBox.setText(msg);
                messageDisplayIndex++;
                
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (messages != null && messages.size() > 0 && messageDisplayIndex >= 1) {
                if (messageDisplayIndex == -1) {
                    messageDisplayIndex = 0;
                }
                String msg = messages.get(messageDisplayIndex).Message;
                this.SendingTextBox.setText(msg);
                messageDisplayIndex--;
                
            }
        }
    }//GEN-LAST:event_SendingTextBoxKeyReleased
    
    public void finalSendMessage() {
        sendMessage(this.SendingTextBox.getText());
    }

    private void SendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendBtnActionPerformed
        finalSendMessage();
    }//GEN-LAST:event_SendBtnActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        makeCurrentUserAllMessageSeen();

    }//GEN-LAST:event_formWindowActivated

    private void MessageDisplayTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessageDisplayTextAreaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_MessageDisplayTextAreaMouseClicked

    private void clikedOnTextEditors(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clikedOnTextEditors
        makeCurrentUserAllMessageSeen();
    }//GEN-LAST:event_clikedOnTextEditors

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        stopThread();
        getListOfFriends().disconnectChattingInterface(this);
        terminateCurrentForm(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelBtn;
    private javax.swing.JTextArea MessageDisplayTextArea;
    private javax.swing.JLabel ProfilePic;
    private javax.swing.JButton SendBtn;
    private javax.swing.JLabel SendingEmailLabel;
    private javax.swing.JTextField SendingTextBox;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initalizeTableName() {
        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
    }
}
