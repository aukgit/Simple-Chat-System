/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb;

import CurrentDb.TableColumns.ActiveState;
import Database.DatabaseQuery;
import Database.DbData;
import java.awt.event.KeyEvent;

/**
 *
 * @author Alim
 */
public class CommonData {

    static String[] _activeStateList;
    static DatabaseQuery _db;
    public static int ENTER_KEY = KeyEvent.VK_ENTER;
    
    /**
     * Creates db if necessary
     */
    public static void initalizeDb() {
        if (_db == null) {
            _db = new DatabaseQuery();
        }
    }

    /**
     * calls initalize to create new DatabaseQuery()
     *
     * @param tableName
     */
    public static void setTableName(String tableName) {
        initalizeDb();
        _db.setTableName(tableName);
    }

    public static String[] getActiveStateList() {
        if (_activeStateList == null) {
            setTableName(TableNames.ACTIVESTATE);
            _db.readData();
            DbData dbData = new DbData(_db);

            _activeStateList = dbData.getSingleColumnValues(ActiveState.State);
        }
        return _activeStateList;
    }
}
