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
public class ServerSettingTable extends DatabaseTableClass {

    public short ServerSettingID;
    public String ServerIP;
    public int ServerPort;
    public boolean IsActive;

    public String ConnectionString;
    public short UserOnlinePort;
    public short PictureUploaderPort;
    public short ProfilePicWidth;
    public short ProfilePicHeight;
    public short ChatingThumbWidth;
    public short ChatingThumbHeight;
    public short ChatListThumbWidth;
    public short ChatListThumbHeight;
    

}
