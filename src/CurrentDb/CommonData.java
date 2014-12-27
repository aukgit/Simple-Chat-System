/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb;

import CurrentDb.TableColumns.ActiveState;
import CurrentDb.Tables.ActiveStateTable;
import CurrentDb.Tables.UserTable;
import Database.DatabaseQuery;
import Database.DbData;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Alim
 */
public class CommonData {

    static String[] _activeStateStringList;
    public static ArrayList<ActiveStateTable> ActiveStateList;
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
        if (_activeStateStringList == null) {
            setTableName(TableNames.ACTIVESTATE);
            _db.readData();
            DbData dbData = new DbData(_db);

            _activeStateStringList = dbData.getSingleColumnValues(ActiveState.State);
        }
        return _activeStateStringList;
    }

    public static Color getColorForActiveStatus(UserTable user) {
        return getColorForActiveStatus(user.CurrentActiveState - 1);
    }

    public static Color getColorForActiveStatus(int index) {
        if (ActiveStateList == null) {
            setTableName(TableNames.ACTIVESTATE);
            ActiveStateList = _db.readAndGetResultsAsORM(new ActiveStateTable());
        }

        ActiveStateTable indexState;
        indexState = ActiveStateList.get(index);
        return new Color(indexState.colorRed, indexState.colorGreen, indexState.colorBlue);
    }

    /**
     * @param aActiveStateList the _activeStateList to set
     */
}
