/*------------------------------------------------
 *------------------------------------------------
 * ||id     		:   112 0821 042
 * |name   		:   Alim Ul Karim
 * |email  		:   alim.karim.nsu@gmail.com
 * |blog   		:   http://bit.ly/auk-blog
 * |linkedin            :   http://linkd.in/alim-ul-karim
 * |First Written       :   2011
 * |Modifed             :   11 December, 2014
 *------------------------------------------------
 *------------------------------------------------
 */
/**
 * Currently not SQL injection protected , future version it will be.
 */
package Database;

//<editor-fold defaultstate="collapsed" desc="imports">
import Common.CommonCodes;
import Database.Attributes.DbAttribute;
import Database.Attributes.*;
import Database.Components.DbInitalizer;
import Database.Components.IQueryType;
import Database.Components.StringMore;
import DesignPattern.JFrameDbComponents;
import Global.AppConfig;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
//</editor-fold>

public final class DatabaseQuery extends DbInitalizer {

    StringMore strMore = new StringMore();

    // <editor-fold defaultstate="collapsed" desc="Intializers & Configarations">
    //<editor-fold defaultstate="collapsed" desc="Constants">
    //</editor-fold>
    private SimpleDateFormat simpleDateFormatter;

    //Configaration
    private String url;

    private String user = DatabaseQuery.DATABASE_USER,
            password = DatabaseQuery.DATABASE_USER_PASSWORD;

    private Connection cnn;
    private Statement stmt, tempStatement;
    private ResultSet rs;

    private int queryStartLimit;
    private int queryEndLimit;
    private int rowCountValueCached = -1;
    private boolean isCleanUpNecessaryWhenQueryDone = true;

    //<editor-fold defaultstate="collapsed" desc="Entity Required fields">
    private String tableAlias = "f";

    //New fields for Enity
    private String FieldDataTypes[] = null; //keeps all data column datatypes
    private int SelectedColumnsIndexes[] = null; //index that the columns are exist in database
    private String tempAllColumnNames[] = null; //get all column names uing columnNames() method
    private String FieldsWithLinks[] = null;
    private String Operators[] = null; // =,>=,<= etc...
    private String previoursTableName = "";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SQL strings">
    private String selectSQL, updateSQL, deleteSQL, createSQL;
    public String LastSQL; //last executed SQL Query
    private static final String SQL_COUNT = "SELECT Count(*) AS COUNT FROM ";
    private static final String SHOW_DB_COLUM_SQL = "SHOW COLUMNS FROM ";

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Table">
    /**
     * table
     */
    private String _tableName = "";
    private String openFieldsName = "*";
    private String[] _tableColumnsGlobal = null;
    //</editor-fold>

    public DbAttribute dbAttr;

    //Boleans
    /**
     * make sure if we need to look for complex queries than equals
     */
    private Boolean queryTypeInitalized = false;
    // default value to initalize lists
    private int defaultListCreatingNumber = 30;
    //<editor-fold defaultstate="collapsed" desc="Arrays">
    //Arrays
    private ArrayList<String> queryFieldNames;
    private ArrayList<String> queryValues;
    private ArrayList<String> joiningArray = null;
    private ArrayList<Integer> queryTypes;
    private ArrayList<String> updateFields;
    private ArrayList<String> updateFieldsValues;
    private ArrayList<String> createFields;
    private ArrayList<String> createFieldsValues;
    //</editor-fold>
    private String createFieldsString = "";
    private String createFieldsValuesString = "";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * By default loads for mysql
     */
    public DatabaseQuery(String tableName) {
        initialize(this.url, this.user, this.password, new MySQL());
        setTableName(tableName);
    }

    public DatabaseQuery() {
        initialize(this.url, this.user, this.password, new MySQL());
    }

    /**
     * if true then it will optimize, you also have to note that this db will
     * only work for a single table. You can also change the setting from
     * setIsCleanUpNecessaryWhenQueryDone() method
     *
     * @param optimizeQueryNoCleanUpsAfterwards : if true then it will optimize,
     * you also have to note that this db will only work for a single table.
     */
    public DatabaseQuery(boolean optimizeQueryNoCleanUpsAfterwards) {
        initialize(this.url, this.user, this.password, new MySQL());
        setIsCleanUpNecessaryWhenQueryDone(!optimizeQueryNoCleanUpsAfterwards);
    }

    /**
     * if true then it will optimize, you also have to note that this db will
     * only work for a single table. You can also change the setting from
     * setIsCleanUpNecessaryWhenQueryDone() method
     *
     * @param optimizeQueryNoCleanUpsAfterwards : if true then it will optimize,
     * you also have to note that this db will only work for a single table.
     */
    public DatabaseQuery(String tableName, boolean optimizeQueryNoCleanUpsAfterwards) {
        initialize(this.url, this.user, this.password, new MySQL());
        setIsCleanUpNecessaryWhenQueryDone(!optimizeQueryNoCleanUpsAfterwards);
        setTableName(tableName);
    }

    /**
     * By default loads for mysql
     *
     * @param url : database url or connection string, if null then load from
     * config file.
     * @param user : user name in database
     * @param password : database user password
     */
    public DatabaseQuery(String url, String user, String password) {
        initialize(url, user, password, new MySQL());
    }

    /**
     *
     * @param url : database url or connection string
     * @param user : user name in database
     * @param password : database user password
     * @param dbAttribute
     */
    public DatabaseQuery(String url, String user, String password, DbAttribute dbAttribute) {
        initialize(url, user, password, dbAttribute);
    }

    public void initialize(String url, String user, String password, DbAttribute dbAttribute) {
        this.createSQL = "";
        this.LastSQL = "";
        this.deleteSQL = "";
        this.updateSQL = "";
        this.selectSQL = "";
        dbAttr = dbAttribute;
        this.simpleDateFormatter = new java.text.SimpleDateFormat(dbAttr.getDefaultDateFormat());
        if (url == null) {
            url = AppConfig.getConnectionString();
        }
        this.url = url;
        if (password == null) {
            password = DATABASE_USER_PASSWORD;
        }
        this.password = password;
        if (user == null) {
            user = DATABASE_USER;
        }
        this.user = user;
        try {

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

    //<editor-fold defaultstate="collapsed" desc="Set connection string in entitymanager">
    /**
     *
     * @param em
     * @param connectionString : a name from META-INF\persistence.xml
     */
    public void setConnectinStringToEntity(EntityManager em, String connectionString) {
        em = javax.persistence.Persistence.createEntityManagerFactory(connectionString).createEntityManager();
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Clean Ups">
    private void cleanQueryArrays() {
        if (isIsCleanUpNecessaryWhenQueryDone()) {
            setQueryFieldNamesCleanUp();
            setQueryValues(null);
            setJoiningArray(null);
            setQueryTypes(null);
            setQueryTypeInitalized(false);

            _tableColumnsGlobal = null;
        }
    }

    private void cleanUpdateArrays() {
        if (isIsCleanUpNecessaryWhenQueryDone()) {
            setUpdateFields(null);
            setUpdateFieldsValues(null);
            _tableColumnsGlobal = null;
        }
    }

    private void cleanCreateArrays() {
        if (isIsCleanUpNecessaryWhenQueryDone()) {
            setCreateFields(null);
            setCreateFieldsValues(null);
            //createFieldsString = ""; //gets nulll when the query is executed.
            //createFieldsValuesString = ""; //gets nulll when the query is executed.
            _tableColumnsGlobal = null;

        }
    }

    private void cleanUpLimit() {
        setQueryEndLimit(0);
        setQueryStartLimit(0);
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
    private ArrayList<String> initializeListIfNecessary(ArrayList<String> list) {
        if (list == null) {
            list = new ArrayList<>(defaultListCreatingNumber);
        }
        return list;
    }

    private ArrayList<Integer> initializeListIntIfNecessary(ArrayList<Integer> list) {
        if (list == null) {
            list = new ArrayList<>(defaultListCreatingNumber);
        }
        return list;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adding or Appending items to the list<- from Param array">
    /**
     * Contains list initialize code.
     *
     * @param append
     * @param list
     * @param fields
     */
    public ArrayList<String> addSpecialFieldsToList(boolean append, ArrayList<String> list, String... fields) {
        list = initializeListIfNecessary(list);
        if (append == false) {
            list.clear();
        }
        //<editor-fold defaultstate="collapsed" desc="adding items to the list">
        list.addAll(Arrays.asList(fields));
        return list;
        //</editor-fold>
    }

    /**
     * Contains list initialize code.
     *
     * @param append
     * @param list
     * @param fields
     */
    public ArrayList<Integer> addSpecialFieldsToIntList(boolean append, ArrayList<Integer> list, int... fields) {
        list = initializeListIntIfNecessary(list);
        if (append == false) {
            list.clear();
        }
        //<editor-fold defaultstate="collapsed" desc="adding items to the list">
        for (int field : fields) {
            list.add(field);
        }

        return list;
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adding or Appending items to the list<- from array">
    public void appendItemsToList(ArrayList<String> list, String[] items) {
        list = initializeListIfNecessary(list);
        list.addAll(Arrays.asList(items));
    }

    /**
     *
     * @param list : pass the list
     * @param items : items that needed to be added to the list
     * @return
     */
    public ArrayList<String> addItemsToListNewly(ArrayList<String> list, String[] items) {
        list = initializeListIfNecessary(list);
        list.clear();
        if (items != null) {
            list.addAll(Arrays.asList(items));
        }
        return list;
    }

    public ArrayList<Integer> appendItemsToIntList(ArrayList<Integer> list, int[] items) {
        list = initializeListIntIfNecessary(list);
        if (items != null) {
            for (int item : items) {
                list.add(item);
            }
        }
        return list;

    }

    /**
     *
     * @param list : pass the list
     * @param items : items that needed to be added to the list
     * @return
     */
    public ArrayList<Integer> addItemsToIntListNewly(ArrayList<Integer> list, int[] items) {
        list = initializeListIntIfNecessary(list);
        list.clear();
        if (items != null) {
            for (int item : items) {
                list.add(item);
            }
        }
        return list;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Query Setters">
    /**
     *
     * @param append : should append or add newly
     * @param fields :Query Fields ie.: where ... *column* = value
     */
    public void setSpecialQueryFields_(boolean append, String... fields) {
        this.queryFieldNames = addSpecialFieldsToList(append, getQueryFieldNames(), fields);
    }

    /**
     *
     * @param append : should append or add newly
     * @param fields :Query Fields ie.: where ... *column* = value
     */
    public void setSpecialQueryFields_(boolean append, int... fields) {
        setQueryFieldNames(initializeListIfNecessary(getQueryFieldNames()));
        String col[] = getColumnsNames();
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
     * @param append : should append or add newly
     * @param values :Query Values ie.: where ... column = *value*
     */
    public void setSpecialQueryValues_(boolean append, String... values) {
        this.queryValues = addSpecialFieldsToList(append, getQueryValues(), values);
    }

    /**
     *
     * @param append : should append or add newly
     * @param values :Query Methods: 0 = Exact, 1 = ExactFromFirst, 2 = Anywhere
     * , 3 = Word based Query
     */
    public void setSpecialTypes_(boolean append, int... values) {
        this.queryTypes = addSpecialFieldsToIntList(append, getQueryTypes(), values);
        this.setQueryTypeInitalized(true);

    }

    /**
     *
     * @param append : should append or add newly
     * @param values :Query joining(AND,OR) types ie.: where ... column = value
     */
    public void setSpecialJoiningType_(boolean append, String... values) {
        this.joiningArray = addSpecialFieldsToList(append, getJoiningArray(), values);
//        setJoiningArray(new String[values.length]);
//        for (int i = 0; i < values.length; i++) {
//            getJoiningArray()[i] = values[i].toUpperCase();
//        }
    }

    /**
     * For entity added
     *
     * @param values
     */
    public void setSpecialOperators_(String... values) {
        Operators = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            Operators[i] = values[i];

        }
    }

    /**
     *
     * @param append : should append or add newly
     * @param values :Query joining(AND,OR) types ie.: where ... column = value
     */
    public void setSpecialUpdateFields_(boolean append, String... values) {
        this.updateFields = addSpecialFieldsToList(append, getUpdateFields(), values);
//        setUpdateFields(new String[values.length]);
//        for (int i = 0; i < values.length; i++) {
//            getUpdateFields()[i] = values[i];
//        }
    }

    public void setSpecialUpdateFieldsValues_(boolean append, String... values) {
        this.updateFieldsValues = addSpecialFieldsToList(append, getUpdateFieldsValues(), values);
//        setUpdateFieldsValues(new String[values.length]);
//        for (int i = 0; i < values.length; i++) {
//            getUpdateFieldsValues()[i] = values[i];
//        }
    }

    public void setSpecialCreateFields_(boolean append, String... values) {
        this.createFields = addSpecialFieldsToList(append, getCreateFields(), values);
//        setCreateFields(new String[values.length]);
//        for (int i = 0; i < values.length; i++) {
//            getCreateFields()[i] = values[i];
//        }
    }

    /**
     *
     * @param append
     * @param values
     */
    public void setSpecialCreateFieldsValues_(boolean append, String... values) {
        this.createFieldsValues = addSpecialFieldsToList(append, getCreateFieldsValues(), values);
//        setCreateFieldsValues(new String[values.length]);
//        for (int i = 0; i < values.length; i++) {
//            getCreateFieldsValues()[i] = values[i];
//        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Query Types : Exact, anywhere, between ...">
    //0
    private String exactQuery(String Field, String Search, String opt, boolean isResultForEntity) {
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        String queryReturn = "(" + Field + opt + " '" + Search + "') ";
        //System.out.println(q);
        return queryReturn;
    }
    //1

    private String exactFromBegining(String Field, String Search, boolean isResultForEntity) {
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        return "(" + Field + " LIKE '" + Search + this.dbAttr.getWildCard() + "') ";
    }
    //2 

    private String anywhereQuery(String Field, String Search, boolean isResultForEntity) {
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        return "(" + Field + " LIKE '" + this.dbAttr.getWildCard() + Search + this.dbAttr.getWildCard() + "') ";
    }
    //3

    private String wordBasedQuery(String Field, String Search, boolean isResultForEntity) {
        String q = "";
        if (Search == null || "".equals(Search.trim())) {
            return "";
        }
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        for (String value : Search.split("[ .,?!]+")) {
            if (value.equals("")) {
                continue;
            }
            if (q.equals("") == false) {
                q += " AND ";
            }
            q += Field + " LIKE '" + this.dbAttr.getWildCard() + value + this.dbAttr.getWildCard() + "' ";

        }
        return "( " + q + " )";
    }

    //4 Word based Or Search
    private String wordBasedQueryUsingOr(String Field, String Search, boolean isResultForEntity) {
        String q = "";
        int param = 0;
        if (Search.trim().equals("")) {
            return "";
        }
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        for (String s : Search.split("[ .,?!]+")) {
            if (s.equals("")) {
                continue;
            }
            if (q.equals("") == false) {
                q += " OR ";
            }
            //q += protectField(Field) + " LIKE '*" + s + "*' ";
            //s += param;
            q += (Field) + " LIKE '" + this.dbAttr.getWildCard() + s + this.dbAttr.getWildCard() + "' ";

        }
        return "( " + q + " )";
    }

    //5 Between Query
    private String betweenQuery(String Field, String Search, boolean isResultForEntity) {
        String queryReturn;
        if (Search == null || "".equals(Search.trim()) || Search.contains(";") == false) {
            return "";
        }
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        String params[] = Search.split("[;]");
        if (params.length != 2) {
            return "";
        }
        queryReturn = (Field) + " BETWEEN '" + params[0] + "' AND '" + params[1] + "'";
        return "( " + queryReturn + " )";
    }

    //6
    private String inQuery(String Field, String Search, boolean isResultForEntity) {
        String queryReturn;
        if (Search == null || "".equals(Search.trim())) {
            return "";
        }
        if (isResultForEntity == false) {
            Field = this.getDbField(Field);
        }
        String params[] = Search.split("[;]");
        String inQuery = "";
        for (String param : params) {
            if ("".equals(inQuery) == false) {
                inQuery += ",";
            }
            inQuery += protectValue(param);
        }

        queryReturn = (Field) + " IN (" + inQuery + ")";
        return "( " + queryReturn + " )";
    }

    /**
     *
     * @param Field
     * @param Search
     * @param type : IQueryType
     * @return single query based on search type given from IQueryType
     */
    private String returnSingleQuery(String Field, String Search, int type, String opt, boolean isResultForEntity) {
        //System.out.println("F : " + Field + " S :" + Search + " T:" + type);
        if (type == WORD_BASE_SEARCH) {
            return wordBasedQuery(Field, Search, isResultForEntity);
        } else if (type == WORD_BASE_SEARCH_USING_OR) {
            return wordBasedQueryUsingOr(Field, Search, isResultForEntity);
        } else if (type == ANYWHERE) {
            return anywhereQuery(Field, Search, isResultForEntity);
        } else if (type == EXACT_FROM_FRIST) {
            return exactFromBegining(Field, Search, isResultForEntity);
        } else if (type == BETWEEN) {

            return betweenQuery(Field, Search, isResultForEntity);
        } else if (type == IN_QUERY) {

            return inQuery(Field, Search, isResultForEntity);
        } else {
            return exactQuery(Field, Search, opt, isResultForEntity);
        }
    }

    /**
     * `Field Name` for mysql
     *
     * @param Field
     * @return this.dbAttr.getTableOpenerLeft() + Field +
     * this.dbAttr.getTableOpenerRight()
     */
    public String getDbField(String Field) {
        if (Field.charAt(0) == this.dbAttr.getTableOpenerLeft() && Field.charAt(Field.length() - 1) == this.dbAttr.getTableOpenerRight()) {
            return Field;
        } else {
            return this.dbAttr.getTableOpenerLeft() + Field + this.dbAttr.getTableOpenerRight();
        }
    }

    /**
     *
     * @param value
     * @return 'value'
     */
    public String protectValue(String value) {
        if (value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
            return value;
        } else {
            return "'" + value + "'";
        }
    }
// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get String joined">
    public <T> String getStringJoined(ArrayList<T> list, String joiner, String protectorLeft, String protectorRight) {
        if (list == null) {
            return null;
        }
        String reStr = "";
        if (protectorLeft == null) {
            protectorLeft = "";
        }
        if (protectorRight == null) {
            protectorRight = "";
        }
        for (T item : list) {
            if ("".equals(reStr) == false) {
                reStr += joiner;
            }
            reStr += protectorLeft + item + protectorRight;
        }

        return reStr;

    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SQL Query Executer">
    public ResultSet ExecuteReadQuery(String sql) {
        try {
            LastSQL = sql;
            rs = getStmt().executeQuery(sql);
            rowCount();
            if (isCleanUpNecessaryWhenQueryDone) {
                cleanUpLimit();
            }
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
     * @param showSQL
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
    //<editor-fold defaultstate="collapsed" desc="is result set init">
    /**
     *
     * @return true if resultset is not null
     */
    public boolean isResultSetInitialized() {
        return getRs() != null;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="C - [Create] in CRUD">
    public String completeCreateQuery() {
        formulateCreateSQL(getCreateFields(), getCreateFieldsValues());
        this.setCreateSQL("INSERT INTO " + getTableName() + "\n(" + getCreateFieldsString() + ") \n VALUES \n (" + getCreateFieldsValuesString() + ")");
        setCreateFieldsValuesString(""); // we do no need this again. if user needs to run the same query again they can use the 
        setCreateFieldsString("");
        return getCreateSQL();
    }

    //C - Create
    public String formulateCreateSQL(ArrayList<String> c, ArrayList<String> v) {
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

                str += getDbField(field);
                value += protectValue(v.get(i));
            }
            i++;
        }

        setCreateFieldsString(str);
        setCreateFieldsValuesString(value);
        cleanCreateArrays();
        return str;
    }

    public boolean createData(String columns[], String values[]) {
        try {
            setCreateFields(columns);
            setCreateFieldsValues(values);
            completeCreateQuery();
            ExecuteUpdateQueries(this.getCreateSQL());
            return true;
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "createData(String columns[], String values[])");
            //Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param columns
     * @param values
     * @return
     */
    public boolean createData(String columns, String values) {
        try {
            setCreateFields(getColumnsSplited(columns));
            setCreateFieldsValues(getValuesSplited(values));
            completeCreateQuery();
            ExecuteUpdateQueries(this.getCreateSQL());
            return true;
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "createData(String columns, String values)");
            //Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Execute SQL based on added fields by setSpecial methods.
     *
     * @return
     */
    public boolean createData() {
        try {
            completeCreateQuery();
            ExecuteUpdateQueries(this.getCreateSQL());
            return true;
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "createData()");
        }
        return false;
    }

    /**
     *
     * @param columns : does the same as createData method
     * @param values : does the same as createData method
     * @return
     */
    public boolean insertData(String columns[], String values[]) {
        try {
            createData(columns, values);
            return true;
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "insertData(String columns[], String values[])");
        }
        return false;
    }

    /**
     *
     * @param columns : does the same as createData method
     * @param values : does the same as createData method
     * @return
     */
    public boolean insertData(String columns, String values) {
        try {
            createData(columns, values);
            return true;

        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "insertData(String columns[], String values[])");
        }
        return false;
    }

    public boolean insertData() {
        try {
            createData();
            return true;
        } catch (Exception ex) {
            ErrorMessage(ex, this.getCreateSQL(), "insertData()");
        }
        return false;
    }

// </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Setting and getting Limits">
    /**
     * start and end only possible for mysql. end only possible for microsoft
     * sql server, defining start point is not possible.
     *
     * @param start : less than or equal to '0' means starts from begining
     * @param end : less than or equal to '0' means loads all from starting
     * point
     */
    public void setLimitsOnQuery(int start, int end) {
        this.setQueryStartLimit(start);
        this.setQueryEndLimit(end);
    }

    /**
     * for mysql limit start,end for mssql top
     *
     * @return based on database attribute
     */
    public String getLimitSQLBottom() {
        String returnSql = "";
        if (dbAttr.getCurrentDatabaseConfig() == DbAttribute.MYSQL) {
            if (this.getQueryStartLimit() > 0 && this.getQueryEndLimit() > 0) {
                returnSql = " LIMIT " + this.getQueryStartLimit() + "," + this.getQueryEndLimit();
            } else if (this.getQueryEndLimit() > 0) {
                returnSql = " LIMIT " + this.getQueryEndLimit();
            }
        }
        return returnSql;
    }

    /**
     * for mssql top end no start can be set
     *
     * @return
     */
    public String getLimitSQLTop() {
        String returnSql = "";
        if (dbAttr.getCurrentDatabaseConfig() == DbAttribute.MICROSOFT_SQL_SERVER) {
            if (getQueryStartLimit() > 0 && getQueryEndLimit() > 0) {
                returnSql = " TOP " + getQueryEndLimit(); //end at
            } else if (getQueryStartLimit() > 0) {

            } else if (getQueryEndLimit() > 0) {
                returnSql = " TOP " + getQueryEndLimit(); //end at
            }
        }
        return returnSql;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="R - [Read/Select] in CRUD">
    public String formulateQuery() {
        String q = "";

        if (getQueryFieldNames() == null) {
            return "";
        }
        if (getQueryFieldNames().isEmpty()) {
            return "";
        }
        String opt = "=";

        for (int i = 0; i < getQueryFieldNames().size(); i++) {
            String f = getQueryFieldNames().get(i);
            String s = getQueryValues().get(i);
            //System.out.println("ase " + f + " : " + s);
            int type = 0;
            if (f.equals("")) {
                continue;
            }
            if (Operators != null) {
                opt = Operators[i];
            }
            if (q.equals("") == false) {
                if (getJoiningArray() == null) {
                    q += " AND ";
                } else if ((getJoiningArray().size() - 1) >= i) {
                    q += " " + getJoiningArray().get(i) + " ";
                } else {
                    q += " AND ";
                }

            }
            after:
            if (getQueryTypeInitalized()) {
                if ((getQueryTypes().size() - 1) >= i) {
                    type = getQueryTypes().get(i);
                    //System.out.println("inside :" + type);
                } else {
                    type = 0;
                    //System.out.println("outside :" + type);
                }

                q += returnSingleQuery(f, s, type, opt, false);
            } else {
                q += returnSingleQuery(f, s, type, opt, false);
                //System.out.println("Query:" + q);
            }
        }

        cleanQueryArrays();
        //System.out.println("Query Last:" + q);
        return q;
    }
    //R - Read

    public String completeReadQuery() {
        String whereClause = GetSQLWhereClauseFromFields();
        String tempSql = "SELECT " + getLimitSQLTop() + getOpenFieldsName() + " FROM " + getTableName() + whereClause + getLimitSQLBottom();

        this.setSelectSQL(tempSql);
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

    /**
     * at least one row returned.
     *
     * @param Columns : CSV
     * @param valuesSearchInFields: CSV
     * @return
     */
    public boolean isExist(String[] Columns, String[] valuesSearchInFields) {
        try {
            if (Columns != null && valuesSearchInFields != null) {
                setLimitsOnQuery(0, 1);
                rs = readData(Columns, valuesSearchInFields);
                return isResultValid(1);
            }
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "isExist(String [] Columns, String [] valuesSearchInFields)");
        }
        return false;
    }

    /**
     * at least one row returned.
     *
     * @param Columns : CSV
     * @param valuesSearchInFields: Semi-colon
     * @return
     */
    public boolean isExist(String Columns, String valuesSearchInFields) {
        try {
            if (Columns != null && valuesSearchInFields != null) {
                rs = readData(Columns, valuesSearchInFields);
                return isResultValid(1);
            }
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "isExist(String Columns, String valuesSearchInFields)");
        }
        return false;
    }

    /**
     *
     * @param <T>
     * @param Columns
     * @param valuesSearchInFields
     * @param entityObject if not found any result then make it to null.
     * @return
     */
    public <T> boolean isExist(String Columns, String valuesSearchInFields, T entityObject) {
        boolean isFound = isExist(Columns, valuesSearchInFields);
        if (isFound) {
            getResultsAsObject(entityObject);
        } else {
            entityObject = null;
        }
        return isFound;
    }

    /**
     *
     * @param Columns : CSV
     * @param valuesSearchInFields: semi-colon
     * @param startLimit : less than or equal zero means no effect.
     * @param endLimit : less than or equal zero means no effect.
     * @return
     */
    public ResultSet readData(String Columns, String valuesSearchInFields, int startLimit, int endLimit) {
        setLimitsOnQuery(startLimit, endLimit);
        return readData(Columns, valuesSearchInFields);
    }

    /**
     *
     * @param Columns : CSV
     * @param valuesSearchInFields: semi-colon
     * @return
     */
    public ResultSet readData(String Columns, String valuesSearchInFields) {
        try {
            if (Columns != null && valuesSearchInFields != null) {
                String[] columns = getColumnsSplited(Columns);
                String[] values = getValuesSplited(valuesSearchInFields);
                setQueryFieldNames(columns);
                setQueryValues(values);
                completeReadQuery(); // generated Select SQL
                rs = ExecuteReadQuery(this.getSelectSQL());
            }
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String Columns, String valuesSearchInFields)");
        }
        return rs;
    }

    public ResultSet readData(String Columns[], String ValuesToSearch[]) {
        try {
            setQueryFieldNames(Columns);
            setQueryValues(ValuesToSearch);

//            this.queryFieldNames = Columns;
//            this.queryValues = ValuesToSearch;
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String Columns[], String ValuesToSearch[])");
        }
        return rs;
    }

    /**
     *
     * @param Columns : dbAttr splitter checkout
     * @param valuesSearchInFields : dbAttr splitter checkout
     * @param startLimit : less than or equal zero means no effect.
     * @param endLimit : less than or equal zero means no effect.
     * @return
     */
    public ResultSet readData(String Columns[], String valuesSearchInFields[], int startLimit, int endLimit) {
        setLimitsOnQuery(startLimit, endLimit);
        return readData(Columns, valuesSearchInFields);
    }

    public ResultSet readData(String Columns[], String ValuesToSearch[], String joingTypes[]) {
        try {
            setQueryFieldNames(Columns);
            setQueryValues(ValuesToSearch);
            setJoiningArray(joingTypes);

//            this.queryFieldNames = Columns;
//            this.queryValues = ValuesToSearch;
//            this.joiningArray = joingTypes;
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
            setQueryTypes(QueryTypesV);
            setQueryTypeInitalized(true);
//          this.queryTypes = QueryTypesV;
            completeReadQuery(); // generated Select SQL
            rs = ExecuteReadQuery(this.getSelectSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getSelectSQL(), "readData(String Columns[], String ValuesToSearch[], String joingTypes[])");
        }
        return rs;
    }

    public ResultSet readData(int Columns[], String ValuesToSearch[]) {
        try {
            setSpecialQueryFields_(false, Columns);
            setQueryValues(ValuesToSearch);
//            this.queryValues = ValuesToSearch;
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

    /**
     * You don't have to include where "SELECT " + getOpenFieldsName() + " FROM
     * " + getTableName() + "\n WHERE \n" + where
     *
     * @param where
     * @return
     */
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
    /**
     * translate all conditions to sql statement
     *
     * @return
     */
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
                value = getUpdateFieldsValues().get(i);
                str += getDbField(field) + " = " + protectValue(value);
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

    /**
     * use special field methods to initialize query for update.
     *
     * @return
     */
    public ResultSet updateData() {
        try {
            completeUpdateQuery();
            ExecuteUpdateQueries(this.getUpdateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getUpdateSQL(), "updateData()");
        }
        return getRs();
    }

    /**
     *
     * @param setFieldsString : string "column1 = value, column2 = value"
     * @return all table rows will be updated.
     */
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

    /**
     *
     * @param setFieldsString : string "column1 = value, column2 = value"
     * @param whereQuery : write only where query
     * @return
     */
    public ResultSet updateData(String setFieldsString, String whereQuery) {
        try {
            this.setUpdateSQL("UPDATE " + getTableName() + " \n SET " + setFieldsString + " WHERE " + whereQuery);
            ExecuteUpdateQueries(this.getUpdateSQL());
        } catch (Exception ex) {
            ErrorMessage(ex, this.getUpdateSQL(), "updateData(String setFieldsString, String query)");
        }
        return getRs();
    }

    /**
     *
     * @param table : setting table name to default.
     * @param setFieldsString : string "column1 = value, column2 = value"
     * @param whereQuery : write only where query
     * @return
     */
    public ResultSet updateData(String table, String setFieldsString, String whereQuery) {
        try {
            setTableName(table);
            this.setUpdateSQL("UPDATE " + getTableName() + " \n SET " + setFieldsString + " WHERE " + whereQuery);
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

    //<editor-fold defaultstate="collapsed" desc="Same input repeat for OR query">
    /**
     * this.joiningArray will be recreated so no previous entry will be there.
     * don't need to set joiningArray (will be set from this method)
     *
     * @param input : should be sql valid by validator
     * @param numberOfTimes : 1 based number , 2 means will repeat at lest once
     *
     * return input;input;input
     */
    public String setSameInputValueForOrQuery(String input, int numberOfTimes) {
        this.joiningArray = new ArrayList<>(defaultListCreatingNumber);
        for (int i = 1; i < numberOfTimes; i++) {
            input += dbAttr.getValueSplitter() + input;
            this.joiningArray.add("Or");
        }
        return input;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Search in Entity">
    // <editor-fold defaultstate="collapsed" desc="getSmart">
    public String getSmart(String s) {
        return s.replaceAll("_", " ").toUpperCase().replaceAll(_tableName.toUpperCase() + " ", "").replaceAll(_tableName.toUpperCase(), "");
    }

    public String getSmartNameForTable(String s) {
        s = s.replaceAll("_", " ").toLowerCase();
        return strMore.EachWordUpperCase(s);
    }

    public String getSmartName(String s) {
        s = getSmart(s).toLowerCase();
        s = strMore.EachWordUpperCase(s);
        return s;
    }

    public String getEntitySmartName(String s) {
        s = s.replaceAll("_", " ").toLowerCase();
        s = strMore.EachWordUpperCase(s).replaceAll(" ", "");
        return s;
    }

    public String getEntitySmartColumnName(String s) {
        s = s.replaceAll("_", " ").toLowerCase();
        s = strMore.EachWordUpperCase(s).replaceAll(" ", "");
        s = strMore.chatAtLowerCase(s, 0);
        return s;
    }

    public String getSmartTableName(String s) {
        char c2 = s.toUpperCase().charAt(0);
        char c = s.toLowerCase().charAt(0);
        return s.replaceAll("_", " ").replace(c, c2);
    }
// </editor-fold>

    public String formulateQueryEntity() {
        String q = "";

        if (getQueryFieldNames() == null) {
            return "";
        }
        if (getQueryFieldNames().isEmpty()) {
            return "";
        }
        for (int i = 0; i < getQueryFieldNames().size(); i++) {
            String regularColumn = getQueryFieldNames().get(i);
            String smartColumn = getEntitySmartColumnName(regularColumn);
//            if (FieldsWithLinks != null) {
//                if (arr.search(FieldsWithLinks, regularColumn) > -1) {
//                    //found
//                    smartColumn += "." + smartColumn;
//                }
//            }

            String f = tableAlias + "." + smartColumn;
            String s = getQueryValues().get(i);
            String opt = "=";
            if (Operators != null) {
                opt = Operators[i];
            }
            int type = 0;
            if (f.equals("")) {
                continue;
            }
            if (q.equals("") == false) {
                if (getJoiningArray() == null) {
                    q += " AND ";
                } else if ((getJoiningArray().size() - 1) >= i) {
                    q += " " + getJoiningArray().get(i) + " ";
                } else {
                    q += " AND ";
                }

            }
            if (getQueryTypeInitalized()) {
                if ((getQueryTypes().size() - 1) >= i) {
                    type = getQueryTypes().get(i);
                    //System.out.println("inside :" + type);
                } else {
                    type = 0;
                    //System.out.println("outside :" + type);
                }
                q += returnSingleQuery(f, s, type, opt, true);
            } else {
                q += returnSingleQuery(f, s, type, opt, true);
                //System.out.println("Query:" + q);
            }
        }

        cleanQueryArrays();
        //System.out.println("Query Last:" + q);
        return q;
    }

    public String completeReadQueryForEntity() {
        String whereClause = formulateQueryEntity();
        String tempTable = getEntitySmartName(_tableName);
        //String tempTable = "FoodCategory";
        if (whereClause.equals("") == false) {
            whereClause = " WHERE " + whereClause;
        }
        String tempSQL = "SELECT " + getLimitSQLTop() + tableAlias + " FROM " + tempTable + " " + tableAlias + " " + whereClause + getLimitSQLBottom();

        //this.setSelectSQL("SELECT " + getOpenFieldsName() + " FROM " + getTableName() + q);
        this.setSelectSQL(tempSQL); //only for entity manager
        //this.setSelectSQL("SELECT x,f FROM  FoodCategory x, Food f ON f.foodCategoryId = x.foodCategoryId " + tableAlias +" " + q); //only for entity manager
        return this.getSelectSQL();

    }

    /**
     * by default searching for exact from first
     *
     * @param <T>
     * @param columns:CSV
     * @param values:semi-colon
     * @param em
     * @param list
     * @param queryQ
     */
    public <T> void searchInEntity(String columns, String values, EntityManager em, List<T> list, Query queryQ) {
        String[] _columns = getColumnsSplited(columns);
        String[] _values = getValuesSplited(values);
        searchInEntity(_columns, _values, null, null, em, list, queryQ);
    }

    /**
     * by default searching for exact from first
     *
     * @param <T>
     * @param columns
     * @param values
     * @param queryTypes : if null then by default searching for exact from
     * first
     * @param em
     * @param list
     * @param queryQ
     */
    public <T> void searchInEntity(String columns[], String values[], int queryTypes[], String[] queryJoinType, EntityManager em, List<T> list, Query queryQ) {
        setQueryFieldNames(columns);
        setQueryValues(values);

        if (queryTypes == null) {
            queryTypes = new int[columns.length];
            for (int i = 0; i < columns.length; i++) {
                queryTypes[i] = IQueryType.EXACT_FROM_FRIST;
            }
        }
        if (queryJoinType == null) {
            queryJoinType = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                queryJoinType[i] = "OR";
            }
        }
        setQueryTypes(queryTypes);
        setJoiningArray(queryJoinType);
        String sql = completeReadQueryForEntity();
        searchInEntity(sql, em, list, queryQ);
    }

    @SuppressWarnings("unchecked")
    private <T> void searchInEntity(String sql, EntityManager em, List<T> list, Query queryQ) {
        try {

            em.getTransaction().rollback();
            em.getTransaction().begin();
            //queryQ = em.createNamedQuery(sql);
            queryQ = em.createQuery(sql);
            //queryQ.setParameter("foodCategoryIdx", search);
            //data = org.jdesktop.observablecollections.ObservableCollections.observableList(queryQ.getResultList());
            Collection data = queryQ.getResultList();
            for (Object entity : data) {
                em.refresh(entity);
                em.flush();
            }
            list.clear();
            list.addAll(data);
        } catch (Exception e) {
            ErrorMessage(e, sql, "searchInEntity(String sql, EntityManager em, List<Object> list, Query queryQ)");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get column value">
    public ArrayList<String> getSingleColumnValues(String columnName) {
        return getSingleColumnValues(getRs(), columnName);
    }

    public ArrayList<String> getSingleColumnValues(ResultSet result, String columnName) {
        if (result != null) {
            int count = rowCount(result);
            if (count > 0) {
                ArrayList<String> returnResult = new ArrayList<>(count + 30);
                try {
                    if (result.first()) {
                        returnResult.add(result.getString(columnName));
                    }
                    while (result.next()) {
                        returnResult.add(result.getString(columnName));
                    }
                    return returnResult;
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        return null;
    }

    public ArrayList<Integer> getSingleColumnValuesInt(String columnName) {
        return getSingleColumnValuesInt(getRs(), columnName);
    }

    public ArrayList<Integer> getSingleColumnValuesInt(ResultSet result, String columnName) {
        if (result != null) {
            int count = rowCount(result);
            if (count > 0) {
                ArrayList<Integer> returnResult = new ArrayList<>(count + 30);
                try {
                    if (result.first()) {
                        returnResult.add(result.getInt(columnName));
                    }
                    while (result.next()) {
                        returnResult.add(result.getInt(columnName));
                    }
                    return returnResult;
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return null;
    }

    public ArrayList<Long> getSingleColumnValuesLong(String columnName) {
        return getSingleColumnValuesLong(getRs(), columnName);
    }

    public ArrayList<Long> getSingleColumnValuesLong(ResultSet result, String columnName) {
        if (result != null) {
            int count = rowCount(result);
            if (count > 0) {
                ArrayList<Long> returnResult = new ArrayList<>(count + 30);
                try {
                    if (result.first()) {
                        returnResult.add(result.getLong(columnName));
                    }
                    while (result.next()) {
                        returnResult.add(result.getLong(columnName));
                    }
                    return returnResult;
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return null;
    }

    /**
     * It moves cursor to last to get the index if exist Heavy method
     *
     * @param index : 1 based index
     * @param columnName
     * @return on not found returns default empty("") string
     */
    public String getValidValue(int index, String columnName) {
        if (isResultSetInitialized() && isResultValid(index)) {
            try {
                int currentRow = getRs().getRow();
                if (currentRow != index) {
                    getRs().absolute(index);
                }
                String val = getRs().getString(columnName);
                if (currentRow > -1 && (currentRow != index)) {
                    getRs().absolute(currentRow);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    /**
     * Moves the cursor to that index.
     *
     * @param index : 1 based index
     * @param columnName
     * @return
     */
    public String getValue(int index, String columnName) {
        if (isResultSetInitialized()) {
            try {
                getRs().absolute(index);
                return getRs().getString(columnName);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Others + Showing Data in Console">
    public void showData() {
        try {
            String columns[] = getColumnsNames();
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
            String columns[] = getColumnsNames();
            long row = 1;
            while (getRs().next()) {
                String printline = "row(" + row++ + "): ";
                for (String f : columns) {
                    String v = getRs().getString(f);
                    if (printline.equals("row(" + (row - 1) + "): ") == false) {
                        printline += " ; ";
                    }
                    printline += f.replaceAll("_", " ").toUpperCase().replaceAll(_tableName.toUpperCase() + " ", "") + ": " + v;
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

    /**
     *
     * @return column names for this table
     */
    public String[] getColumnsNames() {
        if (_tableColumnsGlobal == null) {

            String names = "";
            //int i = 1;
            String query = SHOW_DB_COLUM_SQL + getTableName();

            try {
                ResultSet rs2 = tempStatement.executeQuery(query);
                while (rs2.next()) {
                    if (names.equals("") == false) {
                        names += ",";
                    }
                    names += rs2.getString("Field");
                }
            } catch (SQLException ex) {
                ErrorMessage(ex, query, "columnsNames");
            }
            _tableColumnsGlobal = names.split(",");
        }
        return _tableColumnsGlobal;
    }

    /**
     * also re-populate cahced table column names
     *
     * @param forceLoad : any
     * @return
     */
    public String[] getColumnsNames(boolean forceLoad) {
        String names = "";
        //int i = 1;
        String query = SHOW_DB_COLUM_SQL + getTableName();

        try {
            ResultSet rs2 = tempStatement.executeQuery(query);
            while (rs2.next()) {
                if (names.equals("") == false) {
                    names += ",";
                }
                names += rs2.getString("Field");
            }
        } catch (SQLException ex) {
            ErrorMessage(ex, query, "columnsNames");
        }
        _tableColumnsGlobal = names.split(",");
        return _tableColumnsGlobal;
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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Move to row ">
    public ResultSet moveToRow(int rowNumber) {
        if (isResultValid(rowNumber)) {
            try {
                rs.absolute(rowNumber);
                return rs;
            } catch (SQLException ex) {
                Logger.getLogger(JFrameDbComponents.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(JFrameDbComponents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get Results as Object Type like EntityFramework">
    //<editor-fold defaultstate="collapsed" desc="Single">
    /**
     *
     * @param <T>
     * @param row : 1 based index
     * @param classObject: class itself
     */
    public <T> void getResultsAsObject(T classObject, int row) {

        Field[] fieldsInClass = CommonCodes.getAllFields(classObject.getClass());
        List<String> Columns = Arrays.asList(getColumnsNames());
        moveToRow(row);
        try {
            for (Field field : fieldsInClass) {
                if (Columns.indexOf(field.getName()) > -1) {

                    // field exist in the class then populate the value
                    field.setAccessible(true);
                    String fieldName = field.getName();

                    if (field.getType().equals(Double.TYPE) || field.getType().equals(Double.class)) {
                        field.setDouble(classObject, rs.getDouble(fieldName));
                    } else if (field.getType() == Integer.TYPE || field.getType() == Integer.class) {
                        field.setInt(classObject, rs.getInt(fieldName));
                    } else if (field.getType().equals(Long.TYPE) || field.getType().equals(Long.class)) {
                        field.setLong(classObject, getRs().getLong(fieldName));
                    } else if (field.getType().equals(Date.class)) {
                        field.set(classObject, getRs().getDate(fieldName));
                    } else if (field.getType().equals(Float.TYPE) || field.getType().equals(Float.class)) {
                        field.setFloat(classObject, getRs().getFloat(fieldName));
                    } else if (field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
                        field.setBoolean(classObject, getRs().getBoolean(fieldName));
                    } else if (field.getType().equals(Short.TYPE) || field.getType().equals(Short.class)) {
                        field.setShort(classObject, getRs().getShort(fieldName));
                    } else {
                        field.set(classObject, rs.getString(fieldName));
                    }
                }
            }
        } catch (SQLException | IllegalArgumentException | IllegalAccessException ex) {
            ErrorMessage(ex, this.LastSQL, "getResultsAsObject() - may be row doesn't exist");
        }
    }

    /**
     * row default first
     *
     * @param <T>
     * @param classObject : your class type
     */
    public <T> void getResultsAsObject(T classObject) {
        getResultsAsObject(classObject, 1);
    }

    /**
     * read from db and return database's first row
     *
     * @param <T>
     * @param classObject : your class type
     */
    public <T> void getResultsFirstAsObject(T classObject) {
        setLimitsOnQuery(0, 1);
        readData();
        getResultsAsObject(classObject, 1);
    }

    /**
     * row default first
     *
     * @param <T>
     * @param columns: checkout your db attr splitter for column
     * @param values: checkout your db attr splitter for value
     * @param classObject : your class type
     */
    public <T> void getResultsFirstAsObject(String columns, String values, T classObject) {
        readData(columns, values, 0, 1);
        getResultsAsObject(classObject, 1);
    }

    /**
     * row default first
     *
     * @param <T>
     * @param columns: checkout your db attr splitter for column
     * @param values: checkout your db attr splitter for value
     * @param classObject : your class type
     */
    public <T> void getResultsFirstAsObject(String[] columns, String[] values, T classObject) {
        readData(columns, values, 0, 1);
        getResultsAsObject(classObject, 1);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="multiple">
    private ArrayList<Field> getAvailableColumnsList(Field[] listOfFields) {
        ArrayList<Field> returnList = new ArrayList<>(50);
        List<String> Columns = Arrays.asList(getColumnsNames());
        for (Field field : listOfFields) {
            if (Columns.indexOf(field.getName()) > -1) {
                returnList.add(field);
            }
        }
        return returnList;
    }

    /**
     *
     * @param <T>
     * @param result
     * @param classObject: object.getClass()
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> getResultsAsORM(ResultSet result, T classObject) {
        if (result != null) {
            int rows = rowCount(result);
            Field[] fieldsInClass = CommonCodes.getAllFields(classObject.getClass());
            ArrayList<Field> availableFieldsToWorkWith = getAvailableColumnsList(fieldsInClass);
            ArrayList<T> returnList = new ArrayList<>(rows + 40);

            for (int cursor = 0; cursor < rows; cursor++) {
                T eachObject = null;

                try {
                    result.absolute(cursor + 1);
                    eachObject = (T) classObject.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    ErrorMessage(ex, "Can't create eachObject.");
                } catch (SQLException ex) {
                    ErrorMessage(ex, "Can't move row..");
                }

                for (Field field : availableFieldsToWorkWith) {

                    // field exist in the class then populate the value
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    try {
                        if (field.getType().equals(Double.TYPE) || field.getType().equals(Double.class)) {
                            field.setDouble(eachObject, rs.getDouble(fieldName));
                        } else if (field.getType() == Integer.TYPE || field.getType() == Integer.class) {
                            field.setInt(eachObject, rs.getInt(fieldName));
                        } else if (field.getType().equals(Long.TYPE) || field.getType().equals(Long.class)) {
                            field.setLong(eachObject, getRs().getLong(fieldName));
                        } else if (field.getType().equals(Date.class)) {
                            field.set(eachObject, getRs().getDate(fieldName));
                        } else if (field.getType().equals(Float.TYPE) || field.getType().equals(Float.class)) {
                            field.setFloat(eachObject, getRs().getFloat(fieldName));
                        } else if (field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
                            field.setBoolean(eachObject, getRs().getBoolean(fieldName));
                        } else if (field.getType().equals(Short.TYPE) || field.getType().equals(Short.class)) {
                            field.setShort(eachObject, getRs().getShort(fieldName));
                        } else {
                            field.set(eachObject, rs.getString(fieldName));
                        }
                    } catch (SQLException | IllegalArgumentException | IllegalAccessException ex) {
                        ErrorMessage(ex, this.LastSQL, "getResultsAsORM(ResultSet result, T classObject) - may be row doesn't exist");
                    }

                }
                returnList.add(eachObject);

            }

            return returnList;
        }
        return null;
    }

    /**
     * No reading , have to it before executing this.
     *
     * @param <T>
     * @param classObject: new class object
     * @return
     */
    public <T> ArrayList<T> getResultsAsORM(T classObject) {
        return getResultsAsORM(getRs(), classObject);
    }

    /**
     * reads and then get all the results
     *
     * @param <T>
     * @param classObject : new class object
     * @return
     */
    public <T> ArrayList<T> readAndGetResultsAsORM(T classObject) {
        readData();
        return getResultsAsORM(getRs(), classObject);
    }

    //</editor-fold>
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="row counts of result set">
    /**
     * result set cursor may move to last returns cached row result if once ran
     * for this table.
     *
     * @return how many rows exist in the result set.
     */
    public int rowCount() {
        if (isResultSetInitialized() && rowCountValueCached == -1) {
            rowCountValueCached = rowCount(getRs());
            return rowCountValueCached;
        } else if (isResultSetInitialized() && rowCountValueCached > -1) {
            return rowCountValueCached;
        }
        return 0;
    }

    /**
     * result set cursor may move to last returns cached row result if once ran
     * for this table.
     *
     * @param force : any
     * @return how many rows exist in the result set.
     */
    public int rowCount(boolean force) {
        rowCountValueCached = rowCount(getRs());
        return rowCountValueCached;
    }

    /**
     * result set cursor may move to last
     *
     * @param _result
     * @return how many rows exist in the result set.
     */
    public int rowCount(ResultSet _result) {
        if (_result != null) {
            try {
                int currentPos = _result.getRow();
                _result.last();
                int count = _result.getRow();
                if (currentPos > 0) {
                    _result.absolute(currentPos);
                }
                return count;
            } catch (SQLException ex) {
                ErrorMessage(ex, "rowCount(ResultSet _result)");
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

    /**
     *
     * @param columns:CSV
     * @param values:CSV
     * @return
     */
    public int rowsExistInTable(String columns, String values) {
        String[] _columns = getColumnsSplited(columns);
        String[] _values = getValuesSplited(values);

        return rowsExistInTable(_columns, _values);
    }

    public int rowsExistInTable(String columns[], String values[]) {
        setQueryFieldNames(columns);
        setQueryValues(values);
        return rowsExistInTable();
    }

    public int rowsExistInTable(String Where) {
        String sql = SQL_COUNT + this.getTableName() + " WHERE " + Where;

        rs = readDataFullSQL(sql);
        moveToRow(1);
        try {
            return getRs().getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int rowsExistInTable() {
        String whereClause = GetSQLWhereClauseFromFields();

        String sql = SQL_COUNT + this.getTableName() + " " + whereClause;
        rs = readDataFullSQL(sql);
        moveToRow(1);
        try {
            return getRs().getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="check validation of result set ">
    /**
     * Zero based index.
     *
     * @param rowNumberExist : One based index : if -1 then returns only if
     * result set is not null.
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
                int existingIndex = count; // -1 represents , zero based index, count represents one based index
                if (rowNumberExist <= existingIndex) {
                    rs.absolute(rowNumberExist);
                    return true;
                }
                rs.absolute(currentPos);
            } catch (SQLException ex) {
                ErrorMessage(ex, this.getSelectSQL(), "isResultValid(int rowNumberExist)");
            }
        }
        return false;
    }

    /**
     *
     * @param _rs
     * @param rowNumberExist: One based index
     * @return
     */
    public boolean isResultValid(ResultSet _rs, int rowNumberExist) {
        if (_rs != null) {
            try {
                if (rowNumberExist < 0) {
                    return true;
                }
                int currentPos = _rs.getRow();
                _rs.last();
                int count = _rs.getRow();
                int existingIndex = count;
                if (rowNumberExist <= existingIndex) {
                    _rs.absolute(rowNumberExist);
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
    // <editor-fold defaultstate="collapsed" desc="Getters Setters Folder">
    //<editor-fold defaultstate="collapsed" desc="Getters">
    //<editor-fold defaultstate="collapsed" desc="Getters related to List">
    /**
     * @return the queryFieldNames
     */
    public ArrayList<String> getQueryFieldNames() {
        return queryFieldNames;
    }

    //<editor-fold defaultstate="collapsed" desc="Columns Split by comma">
    public List<String> getColumnsSplitedAsList(String columns) {
        if (columns == null) {
            return Arrays.asList(getColumnsSplited(columns));
        }
        return null;
    }

    public String[] getColumnsSplited(String columns) {
        if (columns != null) {
            return columns.split(dbAttr.getColumnSplitter());
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Columns Split by semi-colon">
    /**
     * Values splited by semi-colon
     *
     * @param values
     * @return
     */
    public List<String> getValuesSplitedAsList(String values) {
        if (values == null) {
            return Arrays.asList(getValuesSplited(values));
        }
        return null;
    }

    public String[] getValuesSplited(String values) {
        if (values != null) {
            return values.split(dbAttr.getValueSplitter());
        }
        return null;
    }
    //</editor-fold>

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
    public java.util.Date getCurrentDate() {
        java.util.Date date = new Date();
        return date;
    }

    /**
     * @return the simpleDateFormatter
     */
    public SimpleDateFormat getSimpleDateFormatter() {
        return simpleDateFormatter;
    }

    /**
     *
     * @param date
     * @return
     */
    public String getDateInMySQLFormat(java.util.Date date) {
        return getSimpleDateFormatter().format(date);
    }

    public String getCurrentInDbFormat() {
        return getDateInMySQLFormat(getCurrentDate());
    }

    /**
     * use this by SimpleDateFormat.format(Date date)
     *
     * @param format: yyyy-MM-dd HH:mm:ss
     * @return SimpleDateFormat
     */
    public SimpleDateFormat getDateFormatter(String format) {
        return new SimpleDateFormat(format);
    }

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
     * @return the _tableName
     */
    public String getTableName() {
        return _tableName;
    }

    /**
     * @return the queryTypeInitalized
     */
    public Boolean getQueryTypeInitalized() {
        return queryTypeInitalized;
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

    /**
     *
     * @return the where clause based on special fields. If no fields there
     * empty string "" not null.
     */
    public String GetSQLWhereClauseFromFields() {
        String q = formulateQuery();

        if (q.equals("") == false) {
            q = "\n WHERE \n" + q;
        }
        return q;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Setters">
    //<editor-fold defaultstate="collapsed" desc="List related setters">
    /**
     * @param createFieldsValues the createFieldsValues to set
     */
    public void setCreateFieldsValues(String[] createFieldsValues) {
        this.createFieldsValues = addItemsToListNewly(this.createFieldsValues, createFieldsValues);
    }

    public void setQueryFieldNamesCleanUp() {
        this.queryFieldNames = addItemsToListNewly(this.queryFieldNames, null);
    }

    /**
     * add fields newly.
     *
     * @param queryFieldNames the queryFieldNames to set
     */
    public void setQueryFieldNames(String[] queryFieldNames) {
        this.queryFieldNames = addItemsToListNewly(this.queryFieldNames, queryFieldNames);
    }

    public void setQueryFieldNames(ArrayList<String> queryFieldNames) {
        this.queryFieldNames = queryFieldNames;
    }

    /**
     * @param queryValues the queryValues to set
     */
    public void setQueryValues(String[] queryValues) {
        if (queryValues == null) {
            this.queryValues = null;
        } else {
            this.queryValues = addItemsToListNewly(this.queryValues, queryValues);
        }
    }

    /**
     * @param joiningArray the joiningArray to set
     */
    public void setJoiningArray(String[] joiningArray) {
        if (joiningArray == null) {
            this.joiningArray = null;
        } else {
            this.joiningArray = addItemsToListNewly(this.joiningArray, joiningArray);
        }
    }

    /**
     * @param queryTypes the queryTypes to set
     */
    public void setQueryTypes(int[] queryTypes) {
        if (queryTypes == null) {
            this.queryTypes = null;
            setQueryTypeInitalized(false);
        } else {
            this.queryTypes = addItemsToIntListNewly(this.queryTypes, queryTypes);
            setQueryTypeInitalized(true);
        }
    }

    /**
     * @param updateFields the updateFields to set
     */
    public void setUpdateFields(String[] updateFields) {
        this.updateFields = addItemsToListNewly(this.updateFields, updateFields);
    }

    /**
     * @param updateFieldsValues the updateFieldsValues to set
     */
    public void setUpdateFieldsValues(String[] updateFieldsValues) {
        this.updateFieldsValues = addItemsToListNewly(this.updateFieldsValues, updateFieldsValues);
    }

    /**
     * @param createFields the createFields to set
     */
    public void setCreateFields(String[] createFields) {
        this.createFields = addItemsToListNewly(this.createFields, createFields);
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
     * @param TableName the _tableName to set
     */
    public void setTableName(String TableName) {
//        _tableName = _tableName.replaceAll("_", " ").toLowerCase();
//        _tableName = strMore.EachWordUpperCase(_tableName).replaceAll(" ", "");
        this._tableName = TableName;
    }

    /**
     * @param openFieldsName the openFieldsName to set
     */
    public void setOpenFieldsName(String openFieldsName) {
        this.openFieldsName = openFieldsName;
    }

    /**
     * @param QueryTypeInitalized the queryTypeInitalized to set
     */
    public void setQueryTypeInitalized(Boolean QueryTypeInitalized) {
        this.queryTypeInitalized = QueryTypeInitalized;
    }

    /**
     * @param simpleDateFormatter the simpleDateFormatter to set
     */
    public void setSimpleDateFormatter(SimpleDateFormat simpleDateFormatter) {
        this.simpleDateFormatter = simpleDateFormatter;
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
    // <editor-fold defaultstate="collapsed" desc="How to use it comments ">
    public static void main(String args[]) {
//        UserTable _user = new UserTable();
//        DatabaseQuery q = new DatabaseQuery();
//        q.setTableName("user");
////        int rowsFound = q.rowsExistInTable();
//        q.readData();
//        q.getResultsAsObject(_user, 1);
//
//        Console.writeLine(_user.toString());
//        DatabaseQuery q = new DatabaseQuery();
//        q.setTableName(TableNames.USER);
//        ArrayList<UserTable> rows = q.readAndGetResultsAsORM(new UserTable());
//        for (UserTable row : rows) {
//
//        }
    }
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

    //<editor-fold defaultstate="collapsed" desc="Clean up getter setter">
    /**
     * @return the isCleanUpNecessaryWhenQueryDone
     */
    public boolean isIsCleanUpNecessaryWhenQueryDone() {
        return isCleanUpNecessaryWhenQueryDone;
    }

    /**
     * @param isCleanUpNecessaryWhenQueryDone the
     * isCleanUpNecessaryWhenQueryDone to set
     */
    public void setIsCleanUpNecessaryWhenQueryDone(boolean isCleanUpNecessaryWhenQueryDone) {
        this.isCleanUpNecessaryWhenQueryDone = isCleanUpNecessaryWhenQueryDone;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Query Limit getter setter">
    /**
     * @return the queryStartLimit
     */
    public int getQueryStartLimit() {
        return queryStartLimit;
    }

    /**
     * @param queryStartLimit the queryStartLimit to set
     */
    public void setQueryStartLimit(int queryStartLimit) {
        this.queryStartLimit = queryStartLimit;
    }

    /**
     * @return the queryEndLimit
     */
    public int getQueryEndLimit() {
        return queryEndLimit;
    }

    /**
     * @param queryEndLimit the queryEndLimit to set
     */
    public void setQueryEndLimit(int queryEndLimit) {
        this.queryEndLimit = queryEndLimit;
    }
//</editor-fold>

}
