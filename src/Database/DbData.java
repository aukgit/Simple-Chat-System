/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class DbData extends SQLError {

    private String[] _columns;
    private ArrayList<String[]> _data;
    private ResultSet _rs;
    private boolean isInitialized = false;

    /**
     *
     * @param countRows
     */
    public DbData() {

    }

    public String[] getColumns() {
        return _columns;
    }

    /**
     *
     * @param givenResultSet
     * @param rs
     * @param columns
     */
    public void intialize(ResultSet givenResultSet, String[] columns) {
        _columns = columns;
        _rs = givenResultSet;
        if (givenResultSet == null || columns == null || columns.length == 0) {
            ErrorMessage("Record Set empty.", "reIntialize");
            return;
        }

        try {
            int currentRow = 0, currentCol, colCount = _columns.length;
            _rs.last(); // goto last
            int size = _rs.getRow();

            _data = new ArrayList<>(size);
            _rs.beforeFirst(); // move to first again.

            while (_rs.next()) {
                String[] rowValues = new String[colCount];
                currentCol = 0;
                for (String column : _columns) {
                    rowValues[currentCol++] = _rs.getString(column);
                }
                _data.set(currentRow++, rowValues);

            }
            isInitialized = true;

        } catch (SQLException ex) {
            ErrorMessage(ex, "No Record Exist.", "reIntialize");
        }
    }

    public void setResultSet(ResultSet rs) {
        _rs = rs;
    }

    public ResultSet getResultSet() {
        return _rs;
    }

    public String[] getRow(int index) throws SQLException {
        if (isInitialized) {
            return _data.get(index);
        }

        if (_rs == null || _columns == null || _columns.length == 0) {
            ErrorMessage("Record Set empty.", "reIntialize");
            return null;
        }
        _rs.beforeFirst();
        _rs.relative(index); // go to the row index

        String[] rowValues = new String[_columns.length];
        int currentCol = 0;
        for (String column : _columns) {
            rowValues[currentCol++] = _rs.getString(column);
        }

        return rowValues;

    }

    public ArrayList<String[]> getData(int index) {
        if (isInitialized) {
            return _data;
        }
        return null;
    }

}
