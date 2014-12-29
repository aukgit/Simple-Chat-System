/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

import CurrentDb.CommonData;
import CurrentDb.TableColumns.ChatSession;
import CurrentDb.TableColumns.ChatSessionRelatedUsers;
import CurrentDb.TableColumns.ToWhomAliasWhat;
import CurrentDb.TableNames;
import CurrentDb.Tables.MessageRecentTable;
import CurrentDb.Tables.ToWhomAliasWhatTable;
import CurrentDb.Tables.UserTable;
import Database.Components.IQueryType;
import Database.DatabaseQuery;
import DesignPattern.JFrameInheritable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class ChatingInterface extends JFrameInheritable {

    private static final long serialVersionUID = 1L;

    UserTable recevingUser;
    UserTable sendingUser;

//    ArrayList<Integer> receiverUserIDs;
//    ArrayList<ToWhomAliasWhatTable> alias;
    ArrayList<MessageRecentTable> messages;

    boolean loadAllPreviousChat;

    long lastMsgPaintedId;

//    class ThreadRunner implements Runnable {
//
//        DatabaseQuery db;
//        public ChatingInterface _chatMsg;
//
//        ThreadRunner(ChatingInterface chatMsg) {
//            db = new DatabaseQuery(TableNames.MESSAGE);
//            this._chatMsg = chatMsg;
//        }
//
//        public void run() {
//            while (true) {
//                try {
//                    Thread.sleep(1500);
//                    _chatMsg.loadLastTwentyConversations();
//                    _chatMsg.paintMessagesOnDisplay();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ChatingInterface.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }
//
//    }
//
//    public void paintMessagesOnDisplay() {
//        if (messages == null || messages.size() == 0) {
//            return;
//        }
//        if (lastMsgPaintedId == 0) {
//            for (int i = messages.size() - 1; i >= 0; i--) {
//                MessageRecentTable msg = messages.get(i);
//
//                if (lastMsgPaintedId == 0) {
//                    lastMsgPaintedId = msg.MessageID;
//                }
//                // printing in reverse becuase those are in recent order
//                if (paintForOwn(msg) == false) {
//                    paintForOthers(msg);
//                }
//
//            }
//
//        } else {
//            for (MessageRecentTable msg : messages) {
//                if (lastMsgPaintedId == msg.MessageID) {
//                    break;
//                }
//                if (paintForOwn(msg) == false) {
//                    paintForOthers(msg);
//                }
//            }
//        }
//
//    }
//
//    public boolean paintForOwn(MessageRecentTable msg) {
//        if (msg.SendFromUserID == this.senderUserID) {
//            this.MessageDisplayTextArea.append("You wrote: " + msg.Message);
//            return true;
//        }
//        return false;
//    }
//
//    public void paintForOthers(MessageRecentTable msg) {
//        if (msg.SendFromUserID != this.senderUserID) {
//            String getAias = getAlias(msg.SendFromUserID);
//            this.MessageDisplayTextArea.append(getAias + " wrote: " + msg.Message);
//        }
//    }
//
//    public String getAlias(int userID) {
//        for (ToWhomAliasWhatTable aAlias : alias) {
//            if (aAlias.UserID == userID) {
//                return aAlias.AliasAs;
//            }
//        }
//        return null;
//    }
//
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
//
//    public void loadAlias() {
//        this.getDb().setTableName(TableNames.TO_WHOM_ALIAS_WHAT);
//
//        String inQuery = this.getDb().getStringJoined(receiverUserIDs, ";", null, null);
//        this.getDb().setSpecialTypes_(false, IQueryType.IN_QUERY);
//        this.getDb().readData(ToWhomAliasWhat.UserID, inQuery);
//        alias = this.getDb().getResultsAsORM(new ToWhomAliasWhatTable());
//
//        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
//
//    }
//
//    public void loadAllConversations() {
//        loadConversations(sessionID, -1);
//    }
//
//    public void loadLastTwentyConversations() {
//        loadConversations(this.sessionID, 20);
//    }
//
//    public void loadConversations(int sessionId, int limit) {
//        if (limit > 0) {
//            this.getDb().setTableName(TableNames.MESSAGES_RECENT);
//            this.getDb().setLimitsOnQuery(0, limit);
//        }
//
//        this.getDb().readData("ChatSessionID", sessionId + "");
//        messages = this.getDb().getResultsAsORM(new MessageRecentTable());
//    }

    public void customInitalize() {
        initComponents();
    }

    /**
     * Creates new form ChatingInterface
     */
    public ChatingInterface(UserTable user, UserTable recevingUser) {
        customInitalize();
        this.sendingUser = user;
        recevingUser = recevingUser;
//        receiverUserIDs = new ArrayList<>(10);
//        receiverUserIDs.add(toWhomWantsToChatWithUserID);
//        addUserToThisSession(this.sessionID, senderUserID);
//        createNewSession();
//        for (Integer id : receiverUserIDs) {
//            addUserToThisSession(this.sessionID, id);
//        }
        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
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
        jLabel1 = new javax.swing.JLabel();
        SendBtn = new javax.swing.JButton();
        AttachImageBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        MessageDisplayTextArea = new javax.swing.JTextArea();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        CancelBtn.setText("Close");
        CancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBtnActionPerformed(evt);
            }
        });

        SendingTextBox.setToolTipText("Send message to your friend\n");
        SendingTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SendingTextBoxKeyReleased(evt);
            }
        });

        UsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        UsernameLabel.setText("Friend : Username");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Status");

        SendBtn.setText("Send");
        SendBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SendBtn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        SendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendBtnActionPerformed(evt);
            }
        });

        AttachImageBtn.setIcon(new javax.swing.ImageIcon("E:\\Working\\GitHub\\Simple-Chat-System\\Emotions\\attachment.png")); // NOI18N
        AttachImageBtn.setToolTipText("Attach image or file");
        AttachImageBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AttachImageBtn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        AttachImageBtn.setIconTextGap(0);
        AttachImageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttachImageBtnActionPerformed(evt);
            }
        });

        MessageDisplayTextArea.setColumns(20);
        MessageDisplayTextArea.setRows(5);
        jScrollPane3.setViewportView(MessageDisplayTextArea);

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
                        .addComponent(AttachImageBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 404, Short.MAX_VALUE)
                        .addComponent(CancelBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SendBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UsernameLabel)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UsernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SendingTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AttachImageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(CancelBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SendBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        UsernameLabel.getAccessibleContext().setAccessibleName("UsernameLabel");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CancelBtnActionPerformed

    private void SendingTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SendingTextBoxKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == CommonData.ENTER_KEY) {

        }
    }//GEN-LAST:event_SendingTextBoxKeyReleased

    private void SendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SendBtnActionPerformed

    private void AttachImageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttachImageBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AttachImageBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AttachImageBtn;
    private javax.swing.JButton CancelBtn;
    private javax.swing.JTextArea MessageDisplayTextArea;
    private javax.swing.JButton SendBtn;
    private javax.swing.JTextField SendingTextBox;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initalizeTableName() {
        this.getDb().setTableName(TableNames.MESSAGES_RECENT);
    }
}
