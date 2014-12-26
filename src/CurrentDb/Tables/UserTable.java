/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

import java.util.Date;

/**
 *
 * @author Alim
 */
public class UserTable extends DatabaseTableClass {

    public int UserID;
    public String Username;
    public String Email;
    public String Password;

    public Date LastLogin;
    public boolean IsBlocked;
    public boolean IsActive;
    public boolean IsAdmin;
    public boolean IsOnline;

    public void Print() {
        System.out.println("ID :" + this.UserID + " | Username: " + this.Username);
    }

}
