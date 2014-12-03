/*------------------------------------------------
 *------------------------------------------------
 * ||id     		:   112 0821 042
 * |name   		:   Alim Ul Karim
 * |email  		:   alim.karim.nsu@gmail.com
 * |blog   		:   http://bit.ly/auk-blog
 * |linkedin            :   http://linkd.in/alim-ul-karim
 *------------------------------------------------
 *------------------------------------------------
 */
package Database;

import Database.Components.DbInitalizer;
import Database.Components.StringMore;
import DesignPattern.DatabaseRunnableComponents;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseQuery extends DbInitalizer {

    StringMore strMore = new StringMore();
    // <editor-fold defaultstate="collapsed" desc="Intializers & Configarations">
    //Configaration
    private String url = DatabaseURL;
    private String user = "root", password = "";
    private Connection cnn;
    private Statement stmt, tempStatement;
    private ResultSet rs;
    //SQLS
    private String selectSQL = "", updateSQL = "", deleteSQL = "", createSQL = "";
    public String LastSQL = ""; //last executed SQL Query
    //table
    String TableName = "";
    private String openFieldsName = "*";
    //Boleans
    private Boolean QueryTypeInitalized = false;
    // default value to initalize lists
    private int defaultListCreatingNumber = 30;
    //Arrays
    private ArrayList<String> queryFieldNames;
    private ArrayList<String> queryValues;
    private ArrayList<String> joiningArray = null;
    private ArrayList<Integer> queryTypes;
    private ArrayList<String> updateFields;
    private ArrayList<String> updateFieldsValues;
    private ArrayList<String> createFields;
    private ArrayList<String> createFieldsValues;
    private String createFieldsString = "";
    private String createFieldsValuesString = "";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public DatabaseQuery() {
        try {

            cnn = DriverManager.getConnection(url, user, password);
            stmt = cnn.createStatement();
            tempStatement = cnn.createStatement();
        } catch (Exception e) {
            System.err.println("Sorry can not connect with MySQL database URL: " + url);
            System.err.println("Error : " + e);
            System.out.println("Please make sure password and user name is correct and your MySQL server is running.");
            System.out.println("In addition, please if check mysql-connector is included in this project.");
        }
    }

    public DatabaseQuery(String url, String user, String password) {
        try {
            this.url = url;
            this.password = password;
            this.user = user;
            cnn = DriverManager.getConnection(this.url, this.user, this.password);
            stmt = cnn.createStatement();
            tempStatement = cnn.createStatement();
        } catch (Exception e) {
            System.err.println("Sorry can not connect with MySQL database URL: " + url);
            System.err.println("Error : " + e);
            System.out.println("Please make sure password and user name is correct and your MySQL server is running.");
            System.out.println("In addition, please if check mysql-connector is included in this project.");
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Clean Ups">

    private void cleanQueryArrays() {
        setQueryFieldNames(null);
        setQueryValues(null);
        setJoiningArray(null);
        setQueryTypes(null);
    }

    private void cleanUpdateArrays() {
        setUpdateFields(null);
        setUpdateFieldsValues(null);
    }

    private void cleanCreateArrays() {
        setCreateFields(null);
        setCreateFieldsValues(null);
        //createFieldsString = ""; //gets nulll when the query is executed.
        //createFieldsValuesString = ""; //gets nulll when the query is executed.
    }
// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Closing Connection">
    public void close() {
        try {
            getCnn().close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intializing Array List if null">
    private void initializeListIfNecessary(ArrayList<String> list) {
        if (list == null) {
            list = new ArrayList<>(defaultListCreatingNumber);
        }
    }
    
    private void initializeListIntIfNecessary(ArrayList<Integer> list) {
        if (list == null) {
            list = new ArrayList<>(defaultListCreatingNumber);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adding Items to the list from params of fields">
    /**
     * Contains list init code.
     *
     * @param append
     * @param list
     * @param fields
     */
    public void addSpecialFieldsToList(boolean append, ArrayList<String> list, String... fields) {
        initializeListIfNecessary(list);
        if (append == false) {
            list.clear();
        }
        //<editor-fold defaultstate="collapsed" desc="adding items to the list">
        list.addAll(Arrays.asList(fields));
        //</editor-fold>
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Query Setters">
    /**
     *
     * @param append : should append or add newly
     * @param fields :Query Fields ie.: where ... *column* = value
     */
    public void setSpecialQueryFields_(boolean append, String... fields) {
        addSpecialFieldsToList(append, getQueryFieldNames(), fields);
    }

    /**
     *
     * @param fields :Query Fields ie.: where ... *column* = value
     */
    public void setSpecialQueryFields_(boolean append, int... fields) {
        initializeListIfNecessary(getQueryFieldNames());
        String col[] = columnsNames();
        if (append == false) {
            getQueryFieldNames().clear();
        }
        for (int i = 0; i < fields.length; i++) {
            getQueryFieldNames().add(col[fields[i]]);
            //System.out.println(queryFieldNames[i]);
        }
    }

    /**
     *
     * @param values :Query Values ie.: where ... column = *value*
     */
    public void setSpecialQueryValues_(boolean append, String... values) {
        addSpecialFieldsToList(append, getQueryValues(), values);
    }

    /**
     *
     * @param values :Query Methods: 0 = Exact, 1 = ExactFromFirst, 2 = Anywhere
     * , 3 = Word based Query
     */
    public void setSpecialTypes_(boolean append, int... values) {
        setQueryTypeInitalized(true);
        setQueryTypes(new int[values.length]);
        for (int i = 0; i < values.length; i++) {
            getQueryTypes()[i] = values[i];
        }
    }

    /**
     *
     * @param values
     * @param fields :Query joining(AND,OR) types ie.: where ... column = value
     * *AND* column = value
     */
    public void setSpecialJoiningType_(boolean append, String... values) {
        setJoiningArray(new String[values.length]);
        for (int i = 0; i < values.length; i++) {
            getJoiningArray()[i] = values[i].toUpperCase();
        }
    }

    public void setSpecialUpdateFields_(boolean append, String... values) {
        setUpdateFields(new String[values.length]);
        for (int i = 0; i < values.length; i++) {
            getUpdateFields()[i] = values[i];
        }
    }

    public void setSpecialUpdateFieldsValues_(boolean append, String... values) {
        setUpdateFieldsValues(new String[values.length]);
        for (int i = 0; i < values.length; i++) {
            getUpdateFieldsValues()[i] = values[i];
        }
    }

    public void setSpecialCreateFields_(boolean append, String... values) {
        setCreateFields(new String[values.length]);
        for (int i = 0; i < values.length; i++) {
            getCreateFields()[i] = values[i];
        }
    }

    public void setSpecialCreateieldsValues_(boolean append, String... values) {
        setCreateFieldsValues(new String[values.length]);
        for (int i = 0; i < values.length; i++) {
            getCreateFieldsValues()[i] = values[i];
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Smart Query Type & Field Protector">
    //0
    private String Exact_Query(String Field, String Search) {
        String q = "(" + protectField(Field) + " = '" + Search + "') ";
        //System.out.println(q);
        return q;
    }
    //1

    private String Exact_From_Begining_Query(String Field, String Search) {
        return "(" + protectField(Field) + " LIKE '" + Search + "*') ";
    }
    //2 

    private String Anywhere_Query(String Field, String Search) {
        return "(" + protectField(Field) + " LIKE '*" + Search + "*') ";
    }
    //3

    private String Word_Based_Query(String Field, String Search) {
        String q = "";
        if (Search.trim() == "") {
            return "";
        }
        for (String s : Search.split("[ .,?!]+")) {
            if (s.equals("")) {
                continue;
            }
            if (q.equals("") == false) {
                q += " AND ";
            }
            q += protectField(Field) + " LIKE '*" + s + "*' ";

        }
        return "( " + q + " )";
    }

    private String ReturnSingleQuery(String Field, String Search, int type) {
        //System.out.println("F : " + Field + " S :" + Search + " T:" + type);
        if (type == WORD_BASE_SEARCH) {
            return Word_Based_Query(Field, Search);
        } else if (type == ANYWHERE) {
            return Anywhere_Query(Field, Search);
        } else if (type == EXACT_FROM_FRIST) {
            return Exact_From_Begining_Query(Field, Search);
        } else {
            return Exact_Query(Field, Search);
        }
    }

    public String protectField(String Field) {
        if (Field.charAt(0) == '`' && Field.charAt(Field.length() - 1) == '`') {
            return Field;
        } else {
            return "`" + Field + "`";
        }
    }

    public String protectValue(String value) {
        if (value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
            return value;
        } else {
            return "'" + value + "'";
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SQL Query Executer">
    public ResultSet ExecuteReadQuery(String sql) {
        try {
            LastSQL = sql;
            rs = getStmt().executeQuery(sql);
        } catch (Exception ex) {
            ErrorMessage(ex, LastSQL, "ExecuteReadQuery(String sql)");
            LastSQL = "";
        }
        return getRs();
    }

    /**
     *
     * @param sql :because it supports update , delete , insert
     */
    public void ExecuteUpdateQueries(String sql) {
        try {
            LastSQL = sql;
            getStmt().executeUpdate(sql);
        } catch (Exception ex) {
            ErrorMessage(ex, LastSQL, "ExecuteUpdateQueries(String sql)");
            LastSQL = "";
        }
    }

    public ResultSet ExecuteReadQuery(String sql, boolean showSQL) {
        try {
            LastSQL = sql;
            rs = getStmt().executeQuery(sql);
            if (showSQL) {
                System.out.println(sql);
            }
        } catch (Exception ex) {
            ErrorMessage(ex, LastSQL, "ExecuteReadQuery(String sql, boolean showSQL)");
            LastSQL = "";
        }
        return getRs();
    }

    /**
     *
     * @param sql :because it supports update , delete , insert
     */
    public void ExecuteUpdateQueries(String sql, boolean showSQL) {
        try {
            LastSQL = sql;
            getStmt().executeUpdate(sql);
            if (showSQL) {
                System.out.println(sql);
            }
        } catch (Exception ex) {
            ErrorMessage(ex, LastSQL, "ExecuteUpdateQueries(String sql, boolean showSQL)");
            LastSQL = "";
        }
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="C - [Create] in CRUD">
    public String completeCreateQuery() {
        formulateCreateSQL(getCreateFields(), getCreateFieldsValues());
        this.setCreateSQL("INSERT INTO " + getTableName() + "\n(" + getCreateFieldsString() + ") \n VALUES \n (" + getCreateFieldsValuesString() + ")");
        setCreateFieldsValuesString(""); // we do no need this again. if user needs to run the same query again they can use the 
        setCreateFieldsString("");
        return getCreateSQL();
    }

    //C - Create
    public String formulateCreateSQL(String[] c, String[] v) {
        String str = "", value = "";
        if (c == null) {
            return "";
        }
        int i = 0;
        for (String field : c) {
            if (field.equals("") == false) {
                if (str.equals("") == false) {
                    str += " , ";
                }

                if (value.equals("") == false) {
                    value += " , ";
                }

                str += protectField(field);
                value += protectValue(v[i]);
            }
            i++;
        }

        setCreateFieldsString(str);
        setCreateFieldsValuesString(value);
        cleanCreateArrays();
        return str;
    }

    public void createData(String columns[], String values[]) {
        try {
            setCreateFields(columns);
            setCreateFieldsValues(values);
            completeCreateQuery();
            ExecuteUpdateQueries(this.getCreateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "createData(String columns[], String values[])");
            //Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param columns
     * @param values
     */
    public void createData(String columns, String values) {
        try {
            setCreateFields(columns.split(","));
            setCreateFieldsValues(values.split(","));
            completeCreateQuery();
            ExecuteUpdateQueries(this.getCreateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "createData(String columns, String values)");
            //Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createData() {
        try {
            completeCreateQuery();
            ExecuteUpdateQueries(this.getCreateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "createData()");
        }
    }

    /**
     *
     * @param columns : does the same as createData method
     * @param values : does the same as createData method
     */
    public void insertData(String columns[], String values[]) {
        try {
            createData(columns, values);
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "insertData(String columns[], String values[])");
        }
    }

    /**
     *
     * @param columns : does the same as createData method
     * @param values : does the same as createData method
     */
    public void insertData(String columns, String values) {
        try {
            createData(columns, values);
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "insertData(String columns[], String values[])");
        }
    }

    public void insertData() {
        try {
            createData();
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "insertData()");
        }
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="R - [Read/Select] in CRUD">
    public String formulateQuery() {
        String q = "";

        if (getQueryFieldNames() == null) {
            return "";
        }
        if (getQueryFieldNames().length == 0) {
            return "";
        }
        for (int i = 0; i < getQueryFieldNames().length; i++) {
            String f = getQueryFieldNames()[i];
            String s = getQueryValues()[i];
            //System.out.println("ase " + f + " : " + s);
            int type = 0;
            if (f.equals("")) {
                continue;
            }
            if (q.equals("") == false) {
                if (getJoiningArray() == null) {
                    q += " AND ";
                } else {
                    if ((getJoiningArray().length - 1) >= i) {
                        q += " " + getJoiningArray()[i] + " ";
                    } else {
                        q += " AND ";
                    }
                }

            }
            after:
            if (getQueryTypeInitalized()) {
                if ((getQueryTypes().length - 1) >= i) {
                    type = getQueryTypes()[i];
                    //System.out.println("inside :" + type);
                } else {
                    type = 0;
                    //System.out.println("outside :" + type);
                }

                q += ReturnSingleQuery(f, s, type);
            } else {
                q += ReturnSingleQuery(f, s, type);
                //System.out.println("Query:" + q);
            }
        }

        cleanQueryArrays();
        //System.out.println("Query Last:" + q);
        return q;
    }
    //R - Read

    public String completeReadQuery() {
        String q = formulateQuery();

        if (q.equals("") == false) {
            q = "\n WHERE \n" + q;
        }
        this.setSelectSQL("SELECT " + getOpenFieldsName() + " FROM " + getTableName() + q);
        //this.setSelectSQL("SELECT f FROM " + getTableName() + " f "+ q);
        return this.getSelectSQL();

    }

    public ResultSet readData() {
        try {
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData");
        }
        return rs;
    }

    public ResultSet readData(String Columns[], String ValuesToSearch[]) {
        try {
            this.queryFieldNames = Columns;
            this.queryValues = ValuesToSearch;
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String Columns[], String ValuesToSearch[])");
        }
        return rs;
    }

    public ResultSet readData(String Columns[], String ValuesToSearch[], String joingTypes[]) {
        try {
            this.queryFieldNames = Columns;
            this.queryValues = ValuesToSearch;
            this.joiningArray = joingTypes;
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String Columns[], String ValuesToSearch[], String joingTypes[])");
        }
        return rs;
    }

    /**
     *
     * @param Columns
     * @param ValuesToSearch
     * @param joingTypes : can put null
     * @param QueryTypesV : can put null
     * @return
     */
    public ResultSet readData(String Columns[], String ValuesToSearch[], String joingTypes[], int QueryTypesV[]) {
        try {
            setQueryFieldNames(Columns);
            setQueryValues(ValuesToSearch);
            setJoiningArray(joingTypes);
            this.queryTypes = QueryTypesV;
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String Columns[], String ValuesToSearch[], String joingTypes[])");
        }
        return rs;
    }

    public ResultSet readData(int Columns[], String ValuesToSearch[]) {
        try {
            setSpecialQueryFields_(Columns);
            this.queryValues = ValuesToSearch;
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(int Columns[], String ValuesToSearch[])");
        }
        return rs;
    }

    public ResultSet readData(Boolean showSQL) {
        try {
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
            if (showSQL) {
                System.out.println(this.getSelectSQL());
            }
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(Boolean showSQL)");
        }
        return rs;
    }

    public ResultSet readDataAll() {
        try {
            this.setSelectSQL("SELECT " + getOpenFieldsName() + " FROM " + getTableName());
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readDataAll");
        }
        return rs;
    }

    public ResultSet readData(String where) {
        try {
            this.setSelectSQL("SELECT " + getOpenFieldsName() + " FROM " + getTableName() + "\n WHERE \n" + where);
            this.rs = ExecuteReadQuery(this.getSelectSQL());
            //System.out.println(qText);
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String where)");
        }
        return this.rs;
    }

    public ResultSet readDataFullSQL(String SQL) {
        try {
            this.setSelectSQL(SQL);
            this.rs = ExecuteReadQuery(this.getSelectSQL());
            //System.out.println(qText);
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readDataFullSQL(String SQL)");
        }
        return this.rs;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="U - [Update] in CRUD">
//    public boolean updateDataWithoutSQL(int rowNumber , String [] dataColumnWise){
//        
//        if(isResultValid(rowNumber)){
//            try {
//                rs.absolute(rowNumber);
//                rs.updateArray(rowNumber, null);
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//        }
//    }

    public String formulateUpdateSQL() {
        String str = "", value = "";
        if (getUpdateFields() == null) {
            return "";
        }
        int i = 0;
        for (String field : getUpdateFields()) {
            if (field.equals("") == false) {
                if (str.equals("") == false) {
                    str += " , ";
                }
                value = getUpdateFieldsValues()[i];
                str += protectField(field) + " = " + protectValue(value);
            }
            i++;
        }
        cleanUpdateArrays();
        return str;
    }

    public String completeUpdateQuery() {
        String q = formulateQuery();
        String update = formulateUpdateSQL();
        this.setUpdateSQL("UPDATE " + getTableName() + " \n SET " + update + " WHERE " + q);
        return getUpdateSQL();
    }

    public ResultSet updateData() {
        try {
            completeUpdateQuery();
            ExecuteUpdateQueries(this.getUpdateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getUpdateSQL(), "updateData()");
        }
        return getRs();
    }

    public ResultSet updateData(String setFieldsString) {
        try {
            String q = formulateQuery();
            this.setUpdateSQL("UPDATE " + getTableName() + " \n SET " + setFieldsString + " WHERE " + q);
            ExecuteUpdateQueries(this.getUpdateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getUpdateSQL(), "updateData(String setFieldsString)");
        }
        return getRs();
    }

    public ResultSet updateData(String setFieldsString, String query) {
        try {
            this.setUpdateSQL("UPDATE " + getTableName() + " \n SET " + setFieldsString + " WHERE " + query);
            ExecuteUpdateQueries(this.getUpdateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getUpdateSQL(), "updateData(String setFieldsString, String query)");
        }
        return getRs();
    }

    public ResultSet updateData(String table, String setFieldsString, String query) {
        try {
            setTableName(table);
            this.setUpdateSQL("UPDATE " + getTableName() + " \n SET " + setFieldsString + " WHERE " + query);
            ExecuteUpdateQueries(this.getUpdateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getUpdateSQL(), "updateData(String table, String setFieldsString, String query)");
        }
        return getRs();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="D - [Delete] in CRUD">

    public String completeDeleteQuery() {
        String q = formulateQuery();
        this.setDeleteSQL("DELETE " + getTableName() + " WHERE " + q);
        return getDeleteSQL();
    }

    public void deleteData() {
        try {
            completeDeleteQuery();
            ExecuteUpdateQueries(this.getDeleteSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getDeleteSQL(), "deleteData()");
        }

    }

    public void deleteData(String query) {
        try {
            this.setDeleteSQL("DELETE " + getTableName() + " WHERE " + query);
            ExecuteUpdateQueries(this.getDeleteSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getDeleteSQL(), "deleteData(String query)");
        }

    }

    public void deleteData(String table, String query) {
        try {
            setTableName(table);
            this.setDeleteSQL("DELETE " + getTableName() + " WHERE " + query);
            ExecuteUpdateQueries(this.getDeleteSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getDeleteSQL(), "deleteData(String table, String query)");
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Others + Showing Data in Console">
    public void showData() {
        try {
            String columns[] = columnsNames();
            while (getRs().next()) {
                String printline = "";
                for (String f : columns) {
                    String v = getRs().getString(f);
                    if (printline.equals("") == false) {
                        printline += " | ";
                    }
                    printline += f + ": " + v;
                }
                System.out.println(printline);
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, "No Record Exist.", "showData");
        }
    }

    public void showDataSmartFormat() {
        try {
            String columns[] = columnsNames();
            long row = 1;
            while (getRs().next()) {
                String printline = "row(" + row++ + "): ";
                for (String f : columns) {
                    String v = getRs().getString(f);
                    if (printline.equals("row(" + (row - 1) + "): ") == false) {
                        printline += " ; ";
                    }
                    printline += f.replaceAll("_", " ").toUpperCase().replaceAll(TableName.toUpperCase() + " ", "") + ": " + v;
                }
                System.out.println(printline);
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, "No Record Exist.", "showData");
        }
    }

    public String[] showDatabases() {
        String names = "";
        ResultSet rs2 = null;
        String q = "";
        try {
            q = "show databases";
            rs2 = tempStatement.executeQuery(q);
            while (rs2.next()) {
                if (names.equals("") == false) {
                    names += ",";
                }
                names += rs2.getString("Database");
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, q, "showDatabases");
        }
        return names.split(",");
    }

    public String[] columnsNames() {
        String names = "";
        //int i = 1;
        String query = "SHOW COLUMNS FROM " + getTableName();
        ResultSet rs2 = null;
        try {
            rs2 = tempStatement.executeQuery(query);
            while (rs2.next()) {
                if (names.equals("") == false) {
                    names += ",";
                }
                names += rs2.getString("Field");
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, query, "columnsNames");
        }
        return names.split(",");
    }

    public String[] columnsNamesInSmartFormat() {
        String names = "";
        String name = "";
        //int i = 1;
        String query = "SHOW COLUMNS FROM " + getTableName();
        ResultSet rs2 = null;
        try {
            rs2 = tempStatement.executeQuery(query);
            while (rs2.next()) {
                if (names.equals("") == false) {
                    names += ",";
                }
                name = getSmart(rs2.getString("Field"));
                names += name;
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, query, "columnsNames");
        }
        return names.split(",");
    }

    public String getSmart(String s) {
        return s.replaceAll("_", " ").toUpperCase().replaceAll(TableName.toUpperCase() + " ", "").replaceAll(TableName.toUpperCase(), "");
    }

    public String getSmartTableName(String s) {
        char c2 = s.toUpperCase().charAt(0);
        char c = s.toLowerCase().charAt(0);
        return s.replaceAll("_", " ").replace(c, c2);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" move to row ">
    public ResultSet moveToRow(int rowNumber) {
        if (isResultValid(rowNumber)) {
            try {
                rs.absolute(rowNumber);
                return rs;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseRunnableComponents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public ResultSet moveToRow(ResultSet _rs, int rowNumber) {
        if (isResultValid(_rs, rowNumber)) {
            try {
                _rs.absolute(rowNumber);
                return _rs;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseRunnableComponents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="row counts of result set">
    /**
     *
     * @return how many rows exist in the result set.
     */
    public int rowCount() {
        if (rs != null) {
            try {
                int currentPos = rs.getRow();
                rs.last();
                int count = rs.getRow();
                rs.absolute(currentPos);
                return count;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return 0;
    }

    /**
     *
     * @return does same as rowCount, alias
     */
    public int size() {
        return rowCount();
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="check validation of result set ">
    /**
     *
     * @param rowNumberExist : if -1 then returns only if result set is not
     * null.
     * @return
     */
    public boolean isResultValid(int rowNumberExist) {
        if (rs != null) {
            try {
                if (rowNumberExist < 0) {
                    return true;
                }
                int currentPos = rs.getRow();
                rs.last();
                int count = rs.getRow();
                int existingIndex = count - 1;
                if (rowNumberExist <= existingIndex) {
                    rs.absolute(currentPos);
                    return true;
                }
                rs.absolute(currentPos);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean isResultValid(ResultSet _rs, int rowNumberExist) {
        if (_rs != null) {
            try {
                if (rowNumberExist < 0) {
                    return true;
                }
                int currentPos = _rs.getRow();
                _rs.last();
                int count = _rs.getRow();
                int existingIndex = count - 1;
                if (rowNumberExist <= existingIndex) {
                    _rs.absolute(currentPos);
                    return true;
                }
                _rs.absolute(currentPos);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

// </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Adding or Appending items to the list">
    public void appendItemsToList(ArrayList<String> list, String[] items) {
        if (list == null) {
            list = new ArrayList<>(defaultListCreatingNumber);
        }

        for (String item : items) {
            list.add(item);
        }
    }

    /**
     *
     * @param list : pass the list
     * @param items : items that needed to be added to the list
     */
    public void addItemsToListNewly(ArrayList<String> list, String[] items) {
        if (list == null) {
            list = new ArrayList<>(defaultListCreatingNumber);
        }
        list.clear();
        for (String item : items) {
            list.add(item);
        }
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters Setters Folder">
    
    //<editor-fold defaultstate="collapsed" desc="Getters">
    
    //<editor-fold defaultstate="collapsed" desc="Getters related to List">
    
    /**
     * @return the queryFieldNames
     */
    public ArrayList<String> getQueryFieldNames() {
        return queryFieldNames;
    }
    
    /**
     * @return the queryValues
     */
    public ArrayList<String> getQueryValues() {
        return queryValues;
    }
    
    /**
     * @return the joiningArray
     */
    public ArrayList<String> getJoiningArray() {
        return joiningArray;
    }
    
    /**
     * @return the updateFields
     */
    public ArrayList<String> getUpdateFields() {
        return updateFields;
    }
    
    /**
     * @return the createFieldsValues
     */
    public ArrayList<String> getCreateFieldsValues() {
        return createFieldsValues;
    }

    /**
     * @return the createFields
     */
    public ArrayList<String> getCreateFields() {
        return createFields;
    }

    /**
     * @return the updateFieldsValues
     */
    public ArrayList<String> getUpdateFieldsValues() {
        return updateFieldsValues;
    }

    /**
     * @return the queryTypes
     */
    public ArrayList<Integer> getQueryTypes() {
        return queryTypes;
    }
    
    //</editor-fold>
        
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the cnn
     */
    public Connection getCnn() {
        return cnn;
    }

    /**
     * @return the stmt
     */
    public Statement getStmt() {
        return stmt;
    }

    /**
     * @return the rs
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * @return the updateSQL
     */
    public String getUpdateSQL() {
        return updateSQL;
    }

    /**
     * @return the deleteSQL
     */
    public String getDeleteSQL() {
        return deleteSQL;
    }

    /**
     * @return the openFieldsName
     */
    public String getOpenFieldsName() {
        return openFieldsName;
    }

    /**
     * @return the TableName
     */
    public String getTableName() {
        return TableName;
    }

    /**
     * @return the QueryTypeInitalized
     */
    public Boolean getQueryTypeInitalized() {
        return QueryTypeInitalized;
    }


    /**
     * @return the createFieldsString
     */
    public String getCreateFieldsString() {
        return createFieldsString;
    }


    /**
     * @return the createSQL
     */
    public String getCreateSQL() {
        return createSQL;
    }

    /**
     * @return the selectSQL
     */
    public String getSelectSQL() {
        return selectSQL;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Setters">
    
    
    //<editor-fold defaultstate="collapsed" desc="List related setters">
    /**
     * @param createFieldsValues the createFieldsValues to set
     */
    public void setCreateFieldsValues(String[] createFieldsValues) {
        addItemsToListNewly(this.createFieldsValues, createFieldsValues);
    }
    
    
    /**
     * @param queryFieldNames the queryFieldNames to set
     */
    public void setQueryFieldNames(String[] queryFieldNames) {
        addItemsToListNewly(this.queryFieldNames, queryFieldNames);
    }

    /**
     * @param queryValues the queryValues to set
     */
    public void setQueryValues(String[] queryValues) {
        addItemsToListNewly(this.queryValues, queryValues);
    }

    /**
     * @param joiningArray the joiningArray to set
     */
    public void setJoiningArray(String[] joiningArray) {

        addItemsToListNewly(this.joiningArray, joiningArray);
    }

    /**
     * @param queryTypes the queryTypes to set
     */
    public void setQueryTypes(int[] queryTypes) {
        this.queryTypes = queryTypes;
    }

    /**
     * @param updateFields the updateFields to set
     */
    public void setUpdateFields(String[] updateFields) {
        addItemsToListNewly(this.updateFields, updateFields);
    }

    /**
     * @param updateFieldsValues the updateFieldsValues to set
     */
    public void setUpdateFieldsValues(String[] updateFieldsValues) {
        addItemsToListNewly(this.updateFieldsValues, updateFieldsValues);
    }

    /**
     * @param createFields the createFields to set
     */
    public void setCreateFields(String[] createFields) {
        addItemsToListNewly(this.createFields, createFields);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Other Setters">
    /**
     * @param createFieldsString the createFieldsString to set
     */
    public void setCreateFieldsString(String createFieldsString) {
        this.createFieldsString = createFieldsString;
    }
    
    /**
     * @param createFieldsValuesString the createFieldsValuesString to set
     */
    public void setCreateFieldsValuesString(String createFieldsValuesString) {
        this.createFieldsValuesString = createFieldsValuesString;
    }
    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @param cnn the cnn to set
     */
    public void setCnn(Connection cnn) {
        this.cnn = cnn;
    }
    
    /**
     * @param stmt the stmt to set
     */
    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }
    
    /**
     * @param rs the rs to set
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    
    /**
     * @param selectSQL the selectSQL to set
     */
    public void setSelectSQL(String selectSQL) {
        this.selectSQL = selectSQL;
    }
    
    /**
     * @param updateSQL the updateSQL to set
     */
    public void setUpdateSQL(String updateSQL) {
        this.updateSQL = updateSQL;
    }
    
    /**
     * @param deleteSQL the deleteSQL to set
     */
    public void setDeleteSQL(String deleteSQL) {
        this.deleteSQL = deleteSQL;
    }
    
    /**
     * @param createSQL the createSQL to set
     */
    public void setCreateSQL(String createSQL) {
        this.createSQL = createSQL;
    }
    
    /**
     * @param TableName the TableName to set
     */
    public void setTableName(String TableName) {
//        TableName = TableName.replaceAll("_", " ").toLowerCase();
//        TableName = strMore.EachWordUpperCase(TableName).replaceAll(" ", "");
        this.TableName = TableName;
    }
    
    /**
     * @param openFieldsName the openFieldsName to set
     */
    public void setOpenFieldsName(String openFieldsName) {
        this.openFieldsName = openFieldsName;
    }
    
    /**
     * @param QueryTypeInitalized the QueryTypeInitalized to set
     */
    public void setQueryTypeInitalized(Boolean QueryTypeInitalized) {
        this.QueryTypeInitalized = QueryTypeInitalized;
    }
//</editor-fold>

    //</editor-fold>

    /**
     * @return the createFieldsValuesString
     */
    public String getCreateFieldsValuesString() {
        return createFieldsValuesString;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" How to use it comments ">
//
//    public static void main(String args[]) {
//
//        DatabaseQuery q = new DatabaseQuery();
//        q.setTableName("chatsession");
//        q.insertData("timed,isActive", "2014-11-26 03:55:57,1");
//
//        q.readData(true);
//        q.ExecuteReadQuery(q.getSelectSQL(), true);
//        q.showDataSmartFormat();
//        String db[] = q.showDatabases();
//        for (String singleDb : db) {
//            System.out.println(singleDb);
//        }
//    }
//    
//    public static void main(String[] argv) throws Exception {
//
//     Format formatter = new SimpleDateFormat("dd-MMM-yy");
//    Date date = (Date) formatter.parseObject("29-Jan-02");
//    System.out.println(date.getYear() + " "+ date.getMonth());
//  }
    // */
// </editor-fold>
}
