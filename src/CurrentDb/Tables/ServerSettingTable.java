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
public class ServerSettingTable {
    public short ServerSettingID;
    public String ServerIP;
    public int ServerPort;
    public boolean IsActive;
    
    public String ConnectionString;
}
