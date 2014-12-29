/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

import java.util.Date;

public class ToWhomAliasWhatTable extends ImageLoadRelatedCode {

    /**
     * Search by this.. Related user id user against the current user.
     */
    public int UserID;

    /**
     * original userid, search by this or may be the current user
     */
    public int ToWhomUserID;
    public String Email;
    public String Username;

    public String AliasAs;
    public String CurrentStatus;

    public boolean IsOnline;

    public int CurrentActiveState;

}
