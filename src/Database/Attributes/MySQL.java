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

    private final String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String MYSQL_COLUMN_SPLITER = ",";
    private final String MYSQL_VALUE_SPLITER = "|";

    public MySQL() {
        setTableOpenerLeft('`');
        setTableOpenerRight('`');
        setWildCard('%');
        setColumnSplitter(MYSQL_COLUMN_SPLITER);
        setValueSplitter(MYSQL_VALUE_SPLITER);
        setDefaultDateFormat(MYSQL_DATE_FORMAT);
    }

}
