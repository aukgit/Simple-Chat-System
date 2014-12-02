/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DesignPattern;

import Database.DatabaseQuery;
import Database.DbData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
    }
    
    public DbData RunQuery(String sql){
        
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
    
    public void moveToRow(int rowNumber){
        _rs =_db.getRs();
        
        if(_db.isResultValid(rowNumber)){
            try {
                _rs.absolute(rowNumber);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseRunnableComponents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
