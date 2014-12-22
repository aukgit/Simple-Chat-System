/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Database.Components.SQLError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 *
 * @author Alim
 */
public class DbData extends SQLError {

    private String[] _columns;
    private ArrayList<String[]> _data;
    private ResultSet _rs;
    private DatabaseQuery _db;
    private boolean isInitialized = false;

    /**
     *
     * @param countRows
     */
    public DbData(DatabaseQuery db) {
        _db = db;
        _rs = db.getRs();
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

    // it moves the cursor to the index
    public String[] getRow(int index) {
        if (isInitialized) {
            return _data.get(index);
        }

        if (_rs == null || _columns == null || _columns.length == 0) {
            ErrorMessage("Record Set empty.", "reIntialize");
            return null;
        }
        try {
            _rs.absolute(index);
        } catch (SQLException sQLException) {
            ErrorMessage(sQLException, "Can't move record.", "getRow method");
        }

        String[] rowValues = new String[_columns.length];
        int currentCol = 0;
        try {
            for (String column : _columns) {
                rowValues[currentCol++] = _rs.getString(column);
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, "Can't give column value.", "getRow method");
        }

        return rowValues;

    }

    // it moves the cursor to the index
    public String[] getSingleColumnValues(String columnName) {

        if (_rs == null) {
            ErrorMessage("Record Set empty.", "reIntialize");
            return null;
        }

        try {
            _rs.absolute(1);

        } catch (SQLException sQLException) {
            ErrorMessage(sQLException, "Can't move record.", "getRow method");
            return null;
        }
        getDb().setRs(_rs);
        int rowsExist = getDb().rowCount();

        String[] rowValues = new String[rowsExist];
        try {
            for (int i = 0; i < rowsExist; i++) {
                _rs.absolute(i + 1);
                rowValues[i] = _rs.getString(columnName);
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, "Can't give column value.", "getSingleColumnValues method");
        }

        return rowValues;
    }

    /**
     * No nulls are acceptable in db row
     *
     * @param singleColumnRows
     * @param combo
     */
    public void populateComboBoxFromDBColumn(String[] singleColumnRows, JComboBox combo) {
        if (singleColumnRows == null) {
            return;
        }
        for (String singleColumnRow : singleColumnRows) {
            combo.addItem(singleColumnRow);
        }
    }

    /**
     * No nulls are acceptable in db row
     *
     * @param singleColumnRows
     * @param list
     */
    @SuppressWarnings("unchecked")
    public void populateListFromDBColumn(String[] singleColumnRows, JList list) {
        if (singleColumnRows == null) {
            return;
        }
        DefaultListModel listModel = new DefaultListModel();
        for (String singleColumnRow : singleColumnRows) {
            listModel.addElement(singleColumnRow);
        }
        list = new JList(listModel);
    }

    public String getRowValue(int index, String columnName) {
        if (isInitialized) {
            String[] rowValues = _data.get(index);
            //get the index of the column name
            int colIndex = java.util.Arrays.asList(_columns).indexOf(columnName);
            if (colIndex > -1) {
                return rowValues[colIndex];
            } else {
                return "";
            }
        }

        if (_rs == null || _columns == null || _columns.length == 0) {
            ErrorMessage("Record Set empty.", "reIntialize");
            return null;
        }
        try {
            _rs.absolute(index);
        } catch (SQLException sQLException) {
            ErrorMessage(sQLException, "Can't move record.", "getRow method");
        }

        for (String column : _columns) {
            if (column.equals(columnName)) {
                String result = null;
                try {
                    result = _rs.getString(column);
                } catch (SQLException ex) {
                    Logger.getLogger(DbData.class.getName()).log(Level.SEVERE, null, ex);
                }
                return result;
            }
        }
        return "";
    }

    public String[] getData(int index) {
        if (isInitialized) {
            return _data.get(index);
        }
        return null;
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

}
