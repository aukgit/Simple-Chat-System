/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

/**
 *
 * @author Alim
 */
@SuppressWarnings("serial")
public class FriendRequestTable extends DatabaseTableClass {

    public long FriendRequestID;
    public int SenderUserID;
    public int ToWhomUserID;
    public boolean IsAccept;
    public String Message;
    public boolean IsSeen;

}
