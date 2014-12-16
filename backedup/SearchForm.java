/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Components;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Alim
 */
public class SearchForm extends javax.swing.JFrame implements ISoftwareInformation, Runnable {

   
    private String Table = "";
    private String columnNames[];
    private String columnNamesSmart[];
    private EntityManager em;
    private Query queryQ;
    private List<Object> list;
    private Thread t;
    private String FormTitle = "";
    private int threadInt = 1;
    private Collection data;
    private String previousSearchtext = "";
    private int previousColumn = 0;
    private int previousMethod = 0;
    private int previousOpt = 0;
    public String DataTypes[] = null;
    private boolean showQueries = true;
    private String LinkedColumnsInDatabase[] = null;
    //For Hidden Search
    private boolean hiddenSearch = true;
    private String SearchTextHiddenSearch = "";
    private String OperatorHiddenSearch = "=";
    private int ColumnHiddenSearch = 0;
    private int MethodHiddenSearch = 0;

    public SearchForm() {
        initComponents();
    }

    public SearchForm(boolean hiddenSearch) {
        initComponents();
        this.hiddenSearch = hiddenSearch;
        this.hide();
    }

    /**
     * 
     * @param search
     * @param column
     * @param method
     * @param opt
     * @return 
     */
    public int setHiddenSearch(String search, int column, int method, String opt) {
        this.hiddenSearch = true;
        SearchTextHiddenSearch = search;
        OperatorHiddenSearch = opt;
        ColumnHiddenSearch = column;
        MethodHiddenSearch = method;
        runSearchThread();//will create new thread and then run the run method
        try {
            t.join();
        } catch (Exception e) {
            System.out.println("join failed:" + e);
        }
        int count = list.size();
        return count;
    }

    /**
     * 
     * @param search
     * @param column
     * @param method
     * @return 
     */
    public int setHiddenSearch(String search, int column, int method) {
        this.hiddenSearch = true;
        SearchTextHiddenSearch = search;
        ColumnHiddenSearch = column;
        MethodHiddenSearch = method;
        OperatorHiddenSearch = "=";
        runSearchThread();//will create new thread and then run the run method
        try {
            t.join();
        } catch (Exception e) {
            System.out.println("join failed:" + e);
        }
        int count = list.size();
        return count;
    }

    /**
     * 
     * @param search
     * @param column
     * @return 
     */
    public int setHiddenSearch(String search, int column) {
        this.hiddenSearch = true;
        SearchTextHiddenSearch = search;
        ColumnHiddenSearch = column;
        OperatorHiddenSearch = "=";
        MethodHiddenSearch = 0;
        runSearchThread();//will create new thread and then run the run method
        try {
            t.join();
        } catch (Exception e) {
            System.out.println("join failed:" + e);
        }
        int count = list.size();
        return count;
    }

    private void setPrevious(String Search, int col, int method, int opt) {
        this.previousColumn = col;
        this.previousMethod = method;
        this.previousOpt = opt;
        this.previousSearchtext = Search;
    }

    private boolean getPreviousWithCurrentStatus(String Search, int col, int method, int opt) {

        if (this.previousColumn == col
                && this.previousMethod == method
                && this.previousOpt == opt
                && this.previousSearchtext.equals(Search)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getPreviousWithCurrentStatus(String Search, int col) {
        if (this.previousColumn == col && this.previousSearchtext.equals(Search)) {
            return true;
        } else {
            return false;
        }
    }

    public void run() {

        if (hiddenSearch) {
            try {
                SearchExecute(this.SearchTextHiddenSearch, this.ColumnHiddenSearch, this.MethodHiddenSearch, this.OperatorHiddenSearch);
            } catch (Exception e) {
                System.err.println("run() method hidden portion... Error:" + e);
            } finally {
                return;
            }
        }

        String search = this.SearchTextBox.getText();
        try {
            if (getPreviousWithCurrentStatus(search, ColumnsCombo.getSelectedIndex(), SearchMethodsCombo.getSelectedIndex(), OperatorsCombo.getSelectedIndex())) {
                return;
            }
            //System.out.println("Thread Name:" + t.getName());
            SearchExecute(search, ColumnsCombo.getSelectedIndex(), SearchMethodsCombo.getSelectedIndex(), OperatorsCombo.getSelectedItem().toString());

        } catch (Exception e) {
            ErrorMessage(e, "Search: " + search, "run() thread");
        }

    }

    public void LoadAll(String table, EntityManager eManager, List<Object> l, Query qx) {
        this.Table = table;
        this.em = eManager;
        this.queryQ = qx;
        this.list = l;
        dbe.setTableName(Table);
        columnNames = dbe.columnsNames();
        //arr.readShow(columnNames);
        columnNamesSmart = dbe.columnsNamesInSmartFormat();
        //arr.readShow(columnNamesSmart);
        arr.arrayToCombo(ColumnsCombo, columnNamesSmart);
        FormTitle = dbe.getSmartNameForTable(this.Table) + " Table Search";
        //System.out.println("Form Title: " +  FormTitle);
        this.setTitle(FormTitle);
        //t = new Thread(this, FormTitle + " " + threadInt++);
    }

    public void LoadAll(String table, EntityManager eManager, List<Object> l, Query qx, String LinkedColumnNames[]) {
        LoadAll(table, eManager, l, qx);
        this.LinkedColumnsInDatabase = LinkedColumnNames;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SearchTextBox = new javax.swing.JTextField();
        ColumnsCombo = new javax.swing.JComboBox();
        SearchMethodsCombo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        InstantSearchCheckbox = new javax.swing.JCheckBox();
        OperatorsCombo = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.bd.dev.restaurantmanagement.mainpackage.RestaurantManagementApp.class).getContext().getResourceMap(SearchForm.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(resourceMap.getIcon("SearchLong.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        SearchTextBox.setForeground(resourceMap.getColor("SearchTextBox.foreground")); // NOI18N
        SearchTextBox.setText(resourceMap.getString("SearchTextBox.text")); // NOI18N
        SearchTextBox.setToolTipText(resourceMap.getString("SearchTextBox.toolTipText")); // NOI18N
        SearchTextBox.setName("SearchTextBox"); // NOI18N
        SearchTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchTextBox_Focus(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchTextBox_LostFocus(evt);
            }
        });
        SearchTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keypressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SearchForm.this.keyTyped(evt);
            }
        });

        ColumnsCombo.setFont(resourceMap.getFont("ColumnsCombo.font")); // NOI18N
        ColumnsCombo.setToolTipText(resourceMap.getString("ColumnsCombo.toolTipText")); // NOI18N
        ColumnsCombo.setName("ColumnsCombo"); // NOI18N
        ColumnsCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SearchColumnChangeEvent(evt);
            }
        });

        SearchMethodsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Exact", "Exact From First Search", "Anywhere", "Word Based Smart Search", "Word Based Smart Search For Anyword in Text", "Between Method(Seperate text by ';')" }));
        SearchMethodsCombo.setToolTipText(resourceMap.getString("SearchMethodsCombo.toolTipText")); // NOI18N
        SearchMethodsCombo.setName("SearchMethodsCombo"); // NOI18N
        SearchMethodsCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SearchMethodsComboItemStateChanged(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.bd.dev.restaurantmanagement.mainpackage.RestaurantManagementApp.class).getContext().getActionMap(SearchForm.class, this);
        jButton1.setAction(actionMap.get("Close")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("SearchTask")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setToolTipText(resourceMap.getString("jButton2.toolTipText")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        InstantSearchCheckbox.setSelected(true);
        InstantSearchCheckbox.setText(resourceMap.getString("InstantSearchCheckbox.text")); // NOI18N
        InstantSearchCheckbox.setName("InstantSearchCheckbox"); // NOI18N

        OperatorsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "=", ">", "<", ">=", "<=", "<>" }));
        OperatorsCombo.setToolTipText(resourceMap.getString("OperatorsCombo.toolTipText")); // NOI18N
        OperatorsCombo.setName("OperatorsCombo"); // NOI18N
        OperatorsCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OperatorsChangedEvent(evt);
            }
        });

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setIcon(resourceMap.getIcon("Search.icon")); // NOI18N
        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setAction(actionMap.get("SearchTask")); // NOI18N
        jMenuItem1.setIcon(resourceMap.getIcon("Search.icon")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAction(actionMap.get("ColumnUp")); // NOI18N
        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAction(actionMap.get("ColumnDown")); // NOI18N
        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAction(actionMap.get("SearchMethodChange")); // NOI18N
        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(InstantSearchCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(SearchMethodsCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ColumnsCombo, 0, 129, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(OperatorsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SearchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ColumnsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OperatorsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchMethodsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InstantSearchCheckbox)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>                        

private void keypressed(java.awt.event.KeyEvent evt) {                            
}                           

private void SearchTextBox_Focus(java.awt.event.FocusEvent evt) {                                     
// TODO add your handling code here:
    if (SearchTextBox.getText().equals("Search...")) {
        SearchTextBox.setText("");
    }
    SearchTextBox.setForeground(Color.black);
}                                    

private void SearchTextBox_LostFocus(java.awt.event.FocusEvent evt) {                                         
// TODO add your handling code here:
    if (SearchTextBox.getText().equals("Search...") || SearchTextBox.getText().equals("")) {
        SearchTextBox.setText("Search...");
        SearchTextBox.setForeground(Color.gray);
    }
}                                        

    private void keyTyped(java.awt.event.KeyEvent evt) {                          
        try {
            if (InstantSearchCheckbox.isSelected()) {
                runSearchThread();
            }
        } catch (Exception e) {
            ErrorMessage("KeyTyped Method error");

        }

    }                         

    private void OperatorsChangedEvent(java.awt.event.ItemEvent evt) {                                       
        // TODO add your handling code here:
        // System.out.println("opt m");
        runSearchThread();
    }                                      

    private void SearchColumnChangeEvent(java.awt.event.ItemEvent evt) {                                         
        // TODO add your handling code here:
        //System.out.println("sea col m");
        runSearchThread();
    }                                        

    private void SearchMethodsComboItemStateChanged(java.awt.event.ItemEvent evt) {                                                    
        // TODO add your handling code here:
        // System.out.println("sea m");
        runSearchThread();
    }                                                   

    public void runSearchThread() {

//        if (t.isAlive()) {
//            try {
//                t.wait();
//            } catch (InterruptedException ex) {
//                ErrorMessage(ex, "Thread Wait", "keypressed(java.awt.event.KeyEvent evt)");
//            }
//        }

        try {
            t = new Thread(this, FormTitle + " " + threadInt++);
            t.start();
        } catch (Exception e) {
            System.out.println("Error on runSearchThread()");
        }

    }

    public boolean isIntNumber(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean isLongNumber(String num) {
        try {
            Long.parseLong(num);
        } catch (Exception nfe) {
            return false;
        }
        return true;
    }

    public boolean isFloatNumber(String num) {
        try {
            Double.parseDouble(num);
        } catch (Exception nfe) {
            return false;
        }
        return true;
    }

    private boolean shouldSearch(int col, String search, int method) {
        try {
            if (DataTypes == null) {
                return true;
            }
            //exact method and between method is needed to be verified and else it doesn't need to .
            if (method == QueryTypes.EXACT || method == QueryTypes.BETWEEN) {

                if (method == QueryTypes.BETWEEN) {
                    // System.out.println("method 5");
                    //between
                    if (search.equals("")) {
                        return false;
                    }
                    String param[] = search.split(";");
                    if (param.length == 2) {
                        for (String s : param) {
                            boolean b = shouldSearch(col, s, 0);//now checked as a exact method if param matchs the citeria
                            //System.out.println("b:" + b + "s:" + s);
                            if (b == false) {
                                return false;
                            }
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
                String type = DataTypes[col];
                if (type.indexOf("int") > - 1 || type.indexOf("short") > -1) {
                    if (isIntNumber(search) || isLongNumber(search)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (type.indexOf("float") > - 1 || type.indexOf("double") > - 1 || type.indexOf("decimal") > - 1) {
                    if (isFloatNumber(search)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            ErrorMessage(e, "", "shouldSearch() Method");
        }


        return true;
    }

    public void SearchExecute(String search, int column) {
        SearchExecute(search, column, 0, "=");
    }

    public void SearchExecute(String search, int column, int method) {
        SearchExecute(search, column, method, "=");
    }

    public void SearchExecute(String search, int column, int method, String opt) {
        try {
            if (shouldSearch(column, search, method) == false) {
                if (getPreviousWithCurrentStatus(search, column)) {
                    return;
                }
                String type = "";
                if (DataTypes != null) {
                    type = "(=\"" + DataTypes[column] + "\")";
                }
                if (method == QueryTypes.BETWEEN) {
                    System.err.println("Search String(=\"" + search + "\") DataType" + type + " must be formated as (search1;search2), moreoever datatype is strict.");
                } else {
                    System.err.println("Search String(=\"" + search + "\") DataType" + type + " does not fit in the query format.");
                }

                setPrevious(search, column, method, OperatorsCombo.getSelectedIndex());
                return;
            }

        } catch (Exception e) {
            ErrorMessage(e, "Checking DataType", "SearchExecute()");
        }
        String sql = "";
        try {
            
            String c = columnNames[column];
            dbe.setSpecialQueryFields_(c);
            DataTypes = dbe.getFieldDataTypes();
            //arr.readShow(DataTypes);
            dbe.setSpecialQueryValues_(search); // value
            dbe.setSpecialTypes_(method);
            if (LinkedColumnsInDatabase != null) {
                dbe.setSpecialLinkedFields_(LinkedColumnsInDatabase);
            }
            dbe.setSpecialOperators_(opt);
            sql = dbe.completeReadQuery();
        } catch (Exception e) {
            System.err.println("SearchExecute() method's middle portion Error:" + e);
        }

        //sql = "SELECT f FROM FoodCategory f  WHERE f.foodCollection.foodName = '" + search + "'";
        if (showQueries) {
            System.out.println("SQL:" + sql);
        }
        //FoodCategory fc = new FoodCategory();

        try {
            em.getTransaction().rollback();
            em.getTransaction().begin();
            //queryQ = em.createNamedQuery(sql);
            queryQ = em.createQuery(sql);
            //queryQ.setParameter("foodCategoryIdx", search);
            //data = org.jdesktop.observablecollections.ObservableCollections.observableList(queryQ.getResultList());
            data = queryQ.getResultList();
            for (Object entity : data) {
                em.refresh(entity);
                em.flush();
            }

            //System.out.println(data);
            list.clear();
            list.addAll(data);
            setPrevious(search, column, method, OperatorsCombo.getSelectedIndex());
        } catch (Exception e) {
            ErrorMessage(e, sql, "Search(String search, int column, int method)");
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Error Message">

    public void ErrorMessage(Exception e, String QueryText) {
        System.out.println("SQL Syntax: " + QueryText);
        System.err.println(e);
    }

    public void ErrorMessage(Exception e, String QueryText, String MethodName) {
        System.out.println("Syntax: " + QueryText + " \nMethod Name: [" + MethodName + "]");
        System.err.println(e + e.getMessage());
    }

    public void ErrorMessage(String QueryText) {
        System.out.println("SQL Syntax: " + QueryText);
    }

    public void ErrorMessage(Exception e) {
        System.err.println(e);
    }
    // </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SearchForm().setVisible(true);
            }
        });
    }

    @Action
    public void SearchTask() {
        runSearchThread();
    }

    @Action
    public void Close() {
        this.hide();
    }

    @Action
    public void ColumnUp() {
        arr.comboSelectNextIndex(ColumnsCombo);
    }

    @Action
    public void ColumnDown() {
        arr.comboSelectPreviousIndex(ColumnsCombo);
    }

    @Action
    public void SearchMethodChange() {
        arr.comboSelectNextIndex(SearchMethodsCombo);
        System.out.println("ase");
    }
    // Variables declaration - do not modify                     
    public javax.swing.JComboBox ColumnsCombo;
    public javax.swing.JCheckBox InstantSearchCheckbox;
    public javax.swing.JComboBox OperatorsCombo;
    public javax.swing.JComboBox SearchMethodsCombo;
    public javax.swing.JTextField SearchTextBox;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JMenu jMenu1;
    public javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JMenuItem jMenuItem3;
    public javax.swing.JMenuItem jMenuItem4;
    public javax.swing.JMenuItem jMenuItem5;
    // End of variables declaration          
}
