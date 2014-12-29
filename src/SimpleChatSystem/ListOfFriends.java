/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

//<editor-fold defaultstate="collapsed" desc="Imports">
import CurrentDb.CommonData;
import CurrentDb.TableColumns.ToWhomAliasWhat;
import CurrentDb.TableColumns.User;
import CurrentDb.TableColumns.UserStatus;
import CurrentDb.TableNames;
import CurrentDb.Tables.ChatListTable;
import CurrentDb.Tables.ToWhomAliasWhatTable;
import CurrentDb.Tables.UserTable;
import Database.DatabaseQuery;
import DesignPattern.JFrameInheritable;
import Global.AppConfig;
import ImageProcessing.Picture;
import OnlineServers.PictureUploader;
import OnlineServers.RelatedObjects.PictureSender;
import OnlineServers.UserOnline;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//</editor-fold>

/**
 *
 * @author Alim
 */
public class ListOfFriends extends JFrameInheritable {
//    private static ListOfFriends Form = new ListOfFriends();

    //<editor-fold defaultstate="collapsed" desc="Fields">
    ArrayList<ChatingInterface> chattingMessageBoxList = new ArrayList<ChatingInterface>(50);
    private static final long serialVersionUID = 1L;
    private boolean _isAdmin;
    private UserTable _user; // load from previous form , startup
    private ArrayList<ToWhomAliasWhatTable> allfriendsList;
    private ArrayList<ToWhomAliasWhatTable> searchResultOfFriendsList;
    private UserTable userFoundByUserName = new UserTable();
    private UserTable userFoundByEmail = new UserTable();

    private DatabaseQuery dbChatLists = new DatabaseQuery(TableNames.CHATLIST);
    private DatabaseQuery dbToWhom = new DatabaseQuery(TableNames.TO_WHOM_ALIAS_WHAT);
    private DatabaseQuery dbUsers = new DatabaseQuery(TableNames.USER);
    private DatabaseQuery dbNewMsg = new DatabaseQuery("NewMessageView");
    private ToWhomAliasWhatTable chatListOfWhom = new ToWhomAliasWhatTable();
    private Picture pictureProcessor = new Picture();
    UserOnline online = new UserOnline();
    String previousSearch = "";

    private int defaultOpenUserNumber = 4;

    @SuppressWarnings("unchecked")
    DefaultListModel<ToWhomAliasWhatTable> friendListDisplayModel = new DefaultListModel();

    private PictureUploader pictureRelatedServer = new PictureUploader();

    private PictureSender profilePictureRequestSender;
    Thread refreshFriendListThread;

    ThreadRunner threadRunnerObject;

    //</editor-fold>
    class ThreadRunner implements Runnable {

        DatabaseQuery db;
        public ListOfFriends currentForm;
        public FriendRqsForm frdReqForm;
        public boolean running = true;

        ThreadRunner(ListOfFriends frame) {
            db = new DatabaseQuery(TableNames.MESSAGE);
            this.currentForm = frame;
        }

        public void run() {
            while (running) {
                try {
                    currentForm.refreshFriendList();
                    frdReqForm = new FriendRqsForm(currentForm._user, currentForm);
                    if (frdReqForm.isNoFriendRequestExist()) {
                        frdReqForm.terminateCurrentForm(false);
                    } else {
                        loadNewForm(frdReqForm, true);
                        running = false;
                        currentForm.pauseThread();
                    }
                    Thread.sleep(2000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(ChatingInterface.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public void closeApplication() {
        // this will make sure WindowListener.windowClosing() et al. will be called.
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

        // this will hide and dispose the frame, so that the application quits by
        // itself if there is nothing else around. 
        setVisible(false);
        dispose();
    // if you have other similar frames around, you should dispose them, too.

        // finally, call this to really exit. 
        // i/o libraries such as WiiRemoteJ need this. 
        // also, this is what swing does for JFrame.EXIT_ON_CLOSE
        System.exit(0);
    }

    public void updateUserProfilePictue() {
        try {
            // TODO add your handling code here:
            profilePictureRequestSender = pictureRelatedServer.clientSendingMethod(profilePictureRequestSender);
            if (profilePictureRequestSender.isIsProccesedSuccessful()) {
                pictureProcessor.setImageIcon(this.UserPicLabel, profilePictureRequestSender.getProfilePic());
//                BufferedImage bufImg = pictureProcessor.getBufferedImage(profilePictureRequestSender.getProfilePic());
//
//                pictureProcessor.setImage(bufImg);
//                String path = AppConfig.getPictureUploadPath() + "hello2.jpg";
//
//                pictureProcessor.save(path);
//                pictureProcessor.setImageIcon(this.UserPicLabel, path, "No pic");
            }
            return;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PictureUploaderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Sorry! Server may not running or no pictures");
//        String path = _user.getPathForProfilePic(_user.UserID);
//        System.out.println(path);
//        pictureProcessor.setImageIcon(this.UserPicLabel, _user.getPathForProfilePic(_user.UserID), "No pic");

    }

    /**
     * Creates new form ListOfFriends
     */
    public void loadCurrentStatus() {
        DatabaseQuery db2 = new DatabaseQuery();
        db2.setTableName(TableNames.USERSTATUS);
        String whereSql = getLatestStatusWhereSQL();
        db2.readData(whereSql);
        String currentStatus = "No Status";
        if (db2.rowCount() > 0) {
            currentStatus = db2.getValue(1, UserStatus.Status);
        }
        this.UsterstatusLabel.setText(currentStatus);

    }

    @SuppressWarnings("unchecked")
    public void customInit(UserTable givenUser) {
        initComponents();
        setUser(givenUser);
        if (getUser().IsAdmin) {
            adminConfigBtn.setVisible(true);
        } else {
            adminConfigBtn.setVisible(false);
        }
        online.reReadDataFromServer();
        online.clientRequest(this._user);
        threadRunnerObject = new ThreadRunner(this);

        refreshFriendListThread = new Thread(threadRunnerObject);
        refreshFriendListThread.start();

        for (String item : CommonData.getActiveStateList()) {
            this.UserActiveState.add(item);
        }
        loadCurrentStatus();

        String dots = "";
        if (getUser().Name.length() > 13) {
            dots = "...";
        }
        this.UsernameLabel.setText(getUser().Name.substring(0, 13) + dots);
        this.UsernameLabel.setToolTipText(getUser().Name);
        profilePictureRequestSender = new PictureSender(givenUser, PictureSender.IAskPicture.Profile);
        updateUserProfilePictue();
        this.setTitle(getUser().Name + " : Friends List");
        makeUserOnline();

    }

    public void startOrResumeThread() {
        this.threadRunnerObject.running = false;
        this.threadRunnerObject = new ThreadRunner(this);
        this.refreshFriendListThread.stop();
        this.refreshFriendListThread = new Thread(threadRunnerObject);
        refreshFriendListThread.start();
    }

    public void pauseThread() {
        this.threadRunnerObject.running = false;
        this.refreshFriendListThread.stop();
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    public void refreshFriendList() {

        dbToWhom.readData(ToWhomAliasWhat.ToWhomUserID, this._user.UserID + "");
        allfriendsList = dbToWhom.getResultsAsORM(new ToWhomAliasWhatTable());

        this.friendsDisplayList.setCellRenderer(new JLabelForListCell());

        //this.friendsDisplayList.removeAll();
        //ArrayList<ChatListTable> allUsers = dbChatLists.readAndGetResultsAsORM(new ChatListTable());
        if (allfriendsList != null) {

            updateDisplayModelIntheListBox(allfriendsList);

        }
    }

    /**
     *
     * @param list
     */
    public void updateDisplayModelIntheListBox(ArrayList<ToWhomAliasWhatTable> list) {

        friendListDisplayModel.removeAllElements();
        for (ToWhomAliasWhatTable item : list) {
            friendListDisplayModel.addElement(item);
        }
        friendsDisplayList.setModel(friendListDisplayModel);

    }

    public ListOfFriends(UserTable u) {
        customInit(u);
    }

    public ListOfFriends() {
        _user = new UserTable();
        this.getDb().setLimitsOnQuery(defaultOpenUserNumber, 1);
        this.getDb().readData();

        this.getDb().getResultsAsObject(_user);
        customInit(_user);
    }

    public void makeUserOnline() {
        dbUsers.updateData("IsOnline=1", User.UserID + "=" + this._user.UserID);
    }

    public void takeOffline() {
        dbUsers.updateData("IsOnline=0", User.UserID + "=" + this._user.UserID);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        RemoveFriendBtn = new javax.swing.JMenuItem();
        MultiChatBtn = new javax.swing.JMenuItem();
        SendEmailBtn = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        UsernameLabel = new javax.swing.JLabel();
        UsterstatusLabel = new javax.swing.JLabel();
        UserPicLabel = new javax.swing.JLabel();
        UserActiveState = new java.awt.Choice();
        editProfile = new javax.swing.JLabel();
        adminConfigBtn = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        friendsDisplayList = new javax.swing.JList();
        SearchOrAddFriendTextBox = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        FriendReqShowMenuBtn = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        ExitBtn = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        jMenu1.setText("Menu");

        RemoveFriendBtn.setText("Remove Friend");
        RemoveFriendBtn.setToolTipText("");
        jMenu1.add(RemoveFriendBtn);

        MultiChatBtn.setText("MultiChat");
        jMenu1.add(MultiChatBtn);

        SendEmailBtn.setText("jMenuItem2");
        jMenu1.add(SendEmailBtn);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        UsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        UsernameLabel.setText("Username");

        UsterstatusLabel.setText("Status");
        UsterstatusLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsterstatusLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UsernameLabel)
                    .addComponent(UsterstatusLabel))
                .addContainerGap(45, Short.MAX_VALUE))
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
        UserPicLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserPicLabelMouseClicked(evt);
            }
        });

        UserActiveState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                UserActiveStateItemStateChanged(evt);
            }
        });

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

        friendsDisplayList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        friendsDisplayList.setVisibleRowCount(30);
        friendsDisplayList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendsDisplayListMouseClicked(evt);
            }
        });
        friendsDisplayList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                friendsDisplayListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(friendsDisplayList);

        SearchOrAddFriendTextBox.setText("Search or add friend...");
        SearchOrAddFriendTextBox.setToolTipText("search or add friend");
        SearchOrAddFriendTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchOrAddFriendTextBoxMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SearchOrAddFriendTextBoxMouseReleased(evt);
            }
        });
        SearchOrAddFriendTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchOrAddFriendTextBoxActionPerformed(evt);
            }
        });
        SearchOrAddFriendTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchOrAddFriendTextBoxKeyReleased(evt);
            }
        });

        jMenu2.setText("Menu");

        FriendReqShowMenuBtn.setText("Friend Requests");
        FriendReqShowMenuBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FriendReqShowMenuBtnMouseClicked(evt);
            }
        });
        FriendReqShowMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FriendReqShowMenuBtnActionPerformed(evt);
            }
        });
        jMenu2.add(FriendReqShowMenuBtn);

        jMenuItem1.setText("Offline Messages");
        jMenu2.add(jMenuItem1);

        ExitBtn.setText("Exit");
        ExitBtn.setToolTipText("");
        ExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitBtnActionPerformed(evt);
            }
        });
        jMenu2.add(ExitBtn);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(UserPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adminConfigBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProfile))
                    .addComponent(UserActiveState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(SearchOrAddFriendTextBox))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editProfile)
                    .addComponent(adminConfigBtn)
                    .addComponent(UserPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserActiveState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(SearchOrAddFriendTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void UsterstatusLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsterstatusLabelMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            UpdateStatus updateStatus;
            updateStatus = new UpdateStatus(getUser(), this);
            updateStatus.setTitle("Update your status");
            loadNewForm(updateStatus);
        }
    }//GEN-LAST:event_UsterstatusLabelMouseClicked

    private void UserActiveStateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_UserActiveStateItemStateChanged
        // TODO add your handling code here:

        int index = UserActiveState.getSelectedIndex();
        Color color = CommonData.getColorForActiveStatus(index);
        UsernameLabel.setForeground(color);

        SaveStateInUserProfile(index);
    }//GEN-LAST:event_UserActiveStateItemStateChanged

    private void UserPicLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserPicLabelMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            PictureUploaderForm pictureUploaderForm = new PictureUploaderForm(getUser(), this);
            loadNewForm(pictureUploaderForm);
        }
    }//GEN-LAST:event_UserPicLabelMouseClicked

    private void SearchOrAddFriendTextBoxMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchOrAddFriendTextBoxMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_SearchOrAddFriendTextBoxMouseReleased

    private void SearchOrAddFriendTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchOrAddFriendTextBoxMouseClicked
        if (this.SearchOrAddFriendTextBox.getText().equals("Search or add friend...")) {
            this.SearchOrAddFriendTextBox.setText("");
        }
    }//GEN-LAST:event_SearchOrAddFriendTextBoxMouseClicked

    private void SearchOrAddFriendTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchOrAddFriendTextBoxKeyReleased
        if (evt.getKeyCode() == CommonData.ENTER_KEY) {
            searchForUserOrAddUserIfFoundInDatabase(this.SearchOrAddFriendTextBox.getText());
        }
    }//GEN-LAST:event_SearchOrAddFriendTextBoxKeyReleased

    private void SearchOrAddFriendTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchOrAddFriendTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchOrAddFriendTextBoxActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        this.closeApplication();
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void FriendReqShowMenuBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FriendReqShowMenuBtnMouseClicked

    }//GEN-LAST:event_FriendReqShowMenuBtnMouseClicked

    private void FriendReqShowMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FriendReqShowMenuBtnActionPerformed
        if (showFriendRqstForm() == false) {

            this.getMessageBox().showError(this, "Sorry! you don't have any friend request.");
        }
    }//GEN-LAST:event_FriendReqShowMenuBtnActionPerformed

    private void friendsDisplayListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_friendsDisplayListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_friendsDisplayListValueChanged

    private void friendsDisplayListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendsDisplayListMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = this.friendsDisplayList.getSelectedIndex();

            showChatInterface(index);
        }
    }//GEN-LAST:event_friendsDisplayListMouseClicked
    public ToWhomAliasWhatTable getListBoxSelectedUser(int index) {
        if (index > -1) {
            return friendListDisplayModel.get(index);
        }

        return null;
    }

    public UserTable getUserFromAliasTable(ToWhomAliasWhatTable alias) {
        UserTable user2 = new UserTable();
        user2.loadUserFromDb(alias.UserID);
        if (user2.UserID > 0) {
            return user2;
        }
        return null;
    }

    public void showChatInterface(int index) {
        if (index > -1) {
            ToWhomAliasWhatTable alias = getListBoxSelectedUser(index);
            UserTable receivingUser = getUserFromAliasTable(alias);

            if (receivingUser != null) {
                ChatingInterface chatMsg = getAnyChattingInterfaceAlreayExist(receivingUser.UserID);
                if (chatMsg == null) {
                    chatMsg = new ChatingInterface(_user, receivingUser, this);
                    chattingMessageBoxList.add(chatMsg);
                }
                loadNewForm(chatMsg, true);

            }
        }
    }

    public boolean isAnyNewMessage(int recevingUserId) {
        return dbNewMsg.isExist("ReceiverUserId", recevingUserId + "");
    }

    public ChatingInterface getAnyChattingInterfaceAlreayExist(int receiveingUserId) {
        for (ChatingInterface item : chattingMessageBoxList) {
            if (item.equals(receiveingUserId)) {
                return item;
            }
        }
        return null;
    }

    public ChatingInterface getAnyChattingInterfaceAlreayExist(ChatingInterface chatMsg) {
        int recevingUserid = chatMsg.getRecevingUser().UserID;
        for (ChatingInterface item : chattingMessageBoxList) {
            if (item.equals(recevingUserid)) {
                return item;
            }
        }
        return null;
    }

    public void disconnectChattingInterface(ChatingInterface chatMsg) {
        ChatingInterface found = getAnyChattingInterfaceAlreayExist(chatMsg);
        if (found != null) {
            chattingMessageBoxList.remove(found);
        }
    }


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        takeOffline();
    }//GEN-LAST:event_formWindowClosing

    public boolean showFriendRqstForm() {
        FriendRqsForm friendRqsForm = new FriendRqsForm(getUser(), this);
        if (friendRqsForm.isNoFriendRequestExist() == false) {
            loadNewForm(friendRqsForm, true);
            return true;
        }
        return false;
    }

    public void filterList(String alias) {

    }

    public boolean isUserExist(String userName, UserTable userFound) {
        return dbUsers.isExist(User.Username, userName, userFound);
    }

    public boolean isUserExistByEmail(String email, UserTable userFound) {
        return dbUsers.isExist(User.Email, email, userFound);
    }

    /**
     *
     * @param input : username , alias (in chatlist table), or email
     * @return
     */
    public boolean searchForUserInFriendList(String input) {
        // first try to look for user name
        if (searchResultOfFriendsList == null) {
            searchResultOfFriendsList = new ArrayList<>(200);
        }
        searchResultOfFriendsList.clear();

        if (isUserExist(input, userFoundByUserName)) {
            // boolean userExistByUsername = db2.isExist(User.Username, input);
            // user found by user name now check if they are friends
            if (isCurrentUserIsFriend(userFoundByUserName.UserID, chatListOfWhom)) {
                searchResultOfFriendsList.add(chatListOfWhom);
                return true;// found by username
            }
        } else {
            boolean userExistByEmail = isUserExistByEmail(input, userFoundByEmail);
            if (userExistByEmail && isCurrentUserIsFriend(userFoundByEmail.UserID, chatListOfWhom)) {
                searchResultOfFriendsList.add(chatListOfWhom);
                return true;// found by username
            }
        }
        return addSearchTextInAliasNamesBySpace(input);
    }

    public boolean addSearchTextInAliasNamesBySpace(String input) {
        searchResultOfFriendsList.clear();
        boolean returnResult = false;
        if (input != null && "".equals(input) == false) {
            String[] list = input.split(" ");
            if (allfriendsList != null && allfriendsList.size() > 0) {
                for (String item : list) {
                    List<ToWhomAliasWhatTable> foundList = allfriendsList
                            .parallelStream()
                            .filter(f -> f.AliasAs.contains(item))
                            .collect(Collectors.toList());
                    if (foundList != null && foundList.size() > 0) {
                        for (ToWhomAliasWhatTable item2 : foundList) {
                            searchResultOfFriendsList.add(item2);
                        }
                        returnResult = true;
                    }
                }
            }
        }
        return returnResult;
    }

    /**
     *
     * @param userID
     * @param aliasFound
     * @return
     */
    public boolean isCurrentUserIsFriend(int userID, ToWhomAliasWhatTable aliasFound) {
//        int fields = 2;
//        String columns[] = new String[fields];
//        String values[] = new String[fields];
//
//        columns[--fields] = ChatList.OriginalUserID;
//        values[fields] = this.getUser().UserID + "";
//
//        columns[--fields] = ChatList.RelatedUserID;
//        values[fields] = userID;
//        return dbChatLists.isExist(userID, userID, aliasFound);

        if (allfriendsList != null && allfriendsList.size() > 0) {
            Optional<ToWhomAliasWhatTable> findItem = allfriendsList.stream()
                    .filter(f -> f.UserID == userID)
                    .findFirst();
            if (findItem.isPresent()) {
                aliasFound = findItem.get();
            } else {
                aliasFound = null;

            }
        } else {
            aliasFound = null;
        }

        return aliasFound != null;

    }

    /**
     *
     * @param input : username , alias (in chatlist table), or email
     */
    public void searchForUserOrAddUserIfFoundInDatabase(String input) {
        // if user found in users chatlist then give the searched result view 
        // or else if the user exist in the current db show a add user dialog
        if (searchForUserInFriendList(input)) {
            // true means user found in the user list by 
            // username
            // or email
            // or by any alias name
            this.updateDisplayModelIntheListBox(searchResultOfFriendsList);
        } else {
            UserTable foundedUser = null;

            // this two objects are manipulated from searchForUserInFriendList()
            // based on that we are going to work with one.
            if (userFoundByUserName.UserID > 0) {
                foundedUser = userFoundByUserName;
            } else if (userFoundByEmail.UserID > 0) {
                foundedUser = userFoundByEmail;

            }

            boolean isSameUserAsCurrent = foundedUser.equals(this.getUser());
            boolean isUserFound = foundedUser != null;

            if (isUserFound && isSameUserAsCurrent == false) {
                AddFriend addfriendForm = new AddFriend(foundedUser, this.getUser());
                if (addfriendForm.isFriendRequsetAlreadySent() == false) {
                    loadNewForm(addfriendForm, true);
                    userFoundByEmail = new UserTable();
                    userFoundByUserName = new UserTable();
                } else {
                    this.getMessageBox().show(this, "Sorry ! you have already send request to the user. Please wait for the response.", "Can't send request to yourself.");

                }
            } else if (isSameUserAsCurrent & isUserFound) {
                this.getMessageBox().show(this, "Sorry ! you can't send request to yourself.", "Can't send request to yourself.");
            } else {
                this.getMessageBox().show(this, "Sorry ! The user that you are looking for doesn't exist in the system you can also try to search for it by email.", "User doesn't exist.");
            }

        }

    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ExitBtn;
    private javax.swing.JMenuItem FriendReqShowMenuBtn;
    private javax.swing.JMenuItem MultiChatBtn;
    private javax.swing.JMenuItem RemoveFriendBtn;
    private javax.swing.JTextField SearchOrAddFriendTextBox;
    private javax.swing.JMenuItem SendEmailBtn;
    private java.awt.Choice UserActiveState;
    private javax.swing.JLabel UserPicLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel UsterstatusLabel;
    private javax.swing.JLabel adminConfigBtn;
    private javax.swing.JLabel editProfile;
    private javax.swing.JList friendsDisplayList;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initalizeTableName() {
        this.getDb().setTableName(TableNames.USER);
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

    private String getLatestStatusWhereSQL() {
        return UserStatus.UserID + "=" + getUser().UserID + " ORDER BY DATED DESC LIMIT 1";
    }

    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Startup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Startup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Startup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Startup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListOfFriends().setVisible(true);
            }
        });
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void SaveStateInUserProfile(int index) {
        int id = CommonData.ActiveStateList.get(index).ActiveStateID;
        _user.CurrentActiveState = id;

        this.getDb().updateData("CurrentActiveState=" + id, User.UserID + "=" + getUser().UserID);
    }
}
