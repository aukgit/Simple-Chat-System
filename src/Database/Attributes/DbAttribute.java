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
    private String _columnSplitter;
    private String _valueSplitter;
    private String _defaultDateFormat;
    private byte _currentDatabaseConfig;
    
    public static final byte MYSQL = 0;
    public static final byte MICROSOFT_SQL_SERVER = 1;
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

    /**
     * @return the _columnSplitter
     */
    public String getColumnSplitter() {
        return _columnSplitter;
    }

    /**
     * @param _columnSplitter the _columnSplitter to set
     */
    public void setColumnSplitter(String _columnSplitter) {
        this._columnSplitter = _columnSplitter;
    }

    /**
     * @return the _valueSplitter
     */
    public String getValueSplitter() {
        return _valueSplitter;
    }

    /**
     * @param _valueSplitter the _valueSplitter to set
     */
    public void setValueSplitter(String _valueSplitter) {
        this._valueSplitter = _valueSplitter;
    }

    /**
     * @return the _defaultDateFormat
     */
    public String getDefaultDateFormat() {
        return _defaultDateFormat;
    }

    /**
     * @param _defaultDateFormat the _defaultDateFormat to set
     */
    public void setDefaultDateFormat(String _defaultDateFormat) {
        this._defaultDateFormat = _defaultDateFormat;
    }

    /**
     * @return the _currentDatabaseConfig
     */
    public byte getCurrentDatabaseConfig() {
        return _currentDatabaseConfig;
    }

    /**
     * @param _currentDatabaseConfig the _currentDatabaseConfig to set
     */
    public void setCurrentDatabaseConfig(byte _currentDatabaseConfig) {
        this._currentDatabaseConfig = _currentDatabaseConfig;
    }

   
}
