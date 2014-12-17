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
public class DbAttribute {

    private char _wildCard;
    private char _tableOpenerLeft;
    private char _tableOpenerRight;

    /**
     * @return the _wildCard
     */
    public char getWildCard() {
        return _wildCard;
    }

    /**
     * @param _wildCard the _wildCard to set
     */
    public void setWildCard(char _wildCard) {
        this._wildCard = _wildCard;
    }

    /**
     * @return the _tableOpenerLeft
     */
    public char getTableOpenerLeft() {
        return _tableOpenerLeft;
    }

    /**
     * @param _tableOpenerLeft the _tableOpenerLeft to set
     */
    public void setTableOpenerLeft(char _tableOpenerLeft) {
        this._tableOpenerLeft = _tableOpenerLeft;
    }

    /**
     * @return the _tableOpenerRight
     */
    public char getTableOpenerRight() {
        return _tableOpenerRight;
    }

    /**
     * @param _tableOpenerRight the _tableOpenerRight to set
     */
    public void setTableOpenerRight(char _tableOpenerRight) {
        this._tableOpenerRight = _tableOpenerRight;
    }

   
}
