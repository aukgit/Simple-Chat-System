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
@SuppressWarnings("serial")
public class UserTable extends ImageLoadRelatedCode {

    public int UserID;

    public String Username;
    public String Email;
    public String Password;

    public Date LastLogin;
    public boolean IsBlocked;
    public boolean IsActive;
    public boolean IsAdmin;
    public boolean IsOnline;
    public String CurrentStatus;
    public int CurrentActiveState;

    public void print() {
        System.out.println("ID :" + this.UserID + " | Username: " + this.Username);
    }

}
