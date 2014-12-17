/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Attributes;

/**
 *
 * @author Alim
 */
public class MySQL extends DbAttribute {
    public MySQL(){
        setTableOpenerLeft('`');
        setTableOpenerRight('`');
        setWildCard('%');
    }

}
