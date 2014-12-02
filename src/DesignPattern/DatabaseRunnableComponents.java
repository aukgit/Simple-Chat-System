/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DesignPattern;

import Database.DatabaseQuery;
import Database.DbData;
import java.sql.ResultSet;
import javax.swing.JFrame;

/**
 *
 * @author Alim
 */
public class DatabaseRunnableComponents extends JFrame {

    private DatabaseQuery _db;
    private DbData _dbData;
    private ResultSet _rs;

    public DatabaseRunnableComponents() {
        _db = new DatabaseQuery();
        _dbData = new DbData();
        _rs = null;
    }

    /**
     * Spread out result set from _db
     */
    public void getResultSetFromDb() {
        _rs = _db.getRs();
        _dbData.setResultSet(_rs);
    }

    /**
     * feed current rs to others.
     */
    public void feedCurrentResultSetToOthers() {
        _db.setRs(_rs);
        _dbData.setResultSet(_rs);
    }

    /**
     * @return the _db
     */
    public DatabaseQuery getDb() {
        return _db;
    }

    /**
     * @param _db the _db to set
     */
    public void setDb(DatabaseQuery _db) {
        this._db = _db;
    }

    /**
     * @return the _dbData
     */
    public DbData getDbData() {
        return _dbData;
    }

    /**
     * @param _dbData the _dbData to set
     */
    public void setDbData(DbData _dbData) {
        this._dbData = _dbData;
    }

    /**
     * @return the _rs
     */
    public ResultSet getRs() {
        return _rs;
    }

    /**
     * @param _rs the _rs to set
     */
    public void setRs(ResultSet _rs) {
        this._rs = _rs;
    }

    public void moveToRow(int rowNumber) {
        _db.moveToRow(rowNumber);
        getResultSetFromDb();
    }

}
