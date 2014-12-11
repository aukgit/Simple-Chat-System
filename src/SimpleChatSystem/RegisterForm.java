/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

import InputValidation.ErrorHighLight;
import Cryptography.Hasher;
import CurrentDb.TableColumns.User;
import CurrentDb.TableNames;

import DesignPattern.InheritableJFrame;
import InputValidation.Validate;

/**
 *
 * @author Alim
 */
public class RegisterForm extends InheritableJFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form RegisterForm
     */
    public RegisterForm() {

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        UsernameTextBox = new javax.swing.JTextField();
        EmailTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        PasswordTextBox = new javax.swing.JPasswordField();
        ConfrimPasswordTextBox = new javax.swing.JPasswordField();
        usernameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        PasswrodLabel = new javax.swing.JLabel();
        ConfirmPasswordLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Register");

        UsernameTextBox.setText("Log in name");
        UsernameTextBox.setName("UsernameText"); // NOI18N
        UsernameTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameTextBoxActionPerformed(evt);
            }
        });

        EmailTextBox.setText("Email@");
        EmailTextBox.setName("EmailText"); // NOI18N

        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        PasswordTextBox.setToolTipText("Password");
        PasswordTextBox.setName("PasswordText"); // NOI18N

        ConfrimPasswordTextBox.setToolTipText("Password");
        ConfrimPasswordTextBox.setName("ConfirmPasswordText"); // NOI18N

        usernameLabel.setText("Username");

        emailLabel.setText("Email");

        PasswrodLabel.setText("Password");

        ConfirmPasswordLabel.setText("Confirm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addGap(10, 10, 10)
                        .addComponent(UsernameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addGap(34, 34, 34)
                        .addComponent(EmailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PasswrodLabel)
                        .addGap(12, 12, 12)
                        .addComponent(PasswordTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ConfirmPasswordLabel)
                        .addGap(21, 21, 21)
                        .addComponent(ConfrimPasswordTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel)
                    .addComponent(UsernameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(EmailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PasswrodLabel)
                    .addComponent(PasswordTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConfirmPasswordLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(ConfrimPasswordTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(jButton1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        ConfrimPasswordTextBox.getAccessibleContext().setAccessibleName("confirmPasswordAccess");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void UsernameTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameTextBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String passwordError = "Password doesn't match.";
        String passwordNormal = "Password is fine.";
        int numberOfFields = 6;
        String Columns[] = new String[numberOfFields];
        String Values[] = new String[numberOfFields];
        int i = 0;
        @SuppressWarnings("deprecation")
        boolean passwordNotMatch = !this.PasswordTextBox.getText().equals(this.ConfrimPasswordTextBox.getText());

        boolean isNotEmailExist = false;
        boolean isNotUsernameExist = false;

        boolean isUsernameValid = Validate.userName(this.UsernameTextBox, usernameLabel, 3, 30, false);
        boolean isEmailValid = Validate.email(this.EmailTextBox, emailLabel, 5, 256, false);
        boolean isPasswordSatisfyMinMax;
        int passwordMin = 3, passwordMax = 20;

        if (isEmailValid) {
            isNotEmailExist = !this.getDb().isExist(User.Email, this.EmailTextBox.getText());
            ErrorHighLight.ErrorValidate(isNotEmailExist, this.emailLabel, this.EmailTextBox, "Email already exist.", "Email doesn't exist.");

        }

        if (isUsernameValid) {
            isNotUsernameExist = !this.getDb().isExist(User.Username, this.UsernameTextBox.getText());
            ErrorHighLight.ErrorValidate(isNotUsernameExist, this.usernameLabel, this.UsernameTextBox, "Username already exist.", "Username is fine.");

        }

        isPasswordSatisfyMinMax = Validate.minMaxCheck(this.PasswordTextBox, PasswrodLabel, passwordMin, passwordMax, false, passwordNormal, "(Min,Max) = (3,20)");
        if (isPasswordSatisfyMinMax) {
            ErrorHighLight.ErrorValidate(passwordNotMatch, this.PasswrodLabel, this.PasswordTextBox, passwordError, passwordNormal);
        }
        ErrorHighLight.ErrorValidate(passwordNotMatch, this.ConfirmPasswordLabel, this.ConfrimPasswordTextBox, passwordError, passwordNormal);

        if (passwordNotMatch && isPasswordSatisfyMinMax && isNotEmailExist && isNotUsernameExist && isUsernameValid && isEmailValid) {
            String hashSh1Password = Hasher.getShA1Hash(User.Password);
            //0
            Columns[i] = User.Username;
            Values[i++] = this.UsernameTextBox.getText();

            //1
            Columns[i] = User.Email;
            Values[i++] = this.EmailTextBox.getText();

            //2
            Columns[i] = User.Password;
            Values[i++] = hashSh1Password;

            //3
            Columns[i] = User.LastLogin;
            Values[i++] = getDb().getCurrentDateInMySQLFormat(); // returns current date.

            //4
            Columns[i] = User.IsActive;
            Values[i++] = "1";

            //5
            Columns[i] = User.IsBlocked;
            Values[i++] = "0";
            if (this.getDb().insertData(Columns, Values)) {
                this.getMessageBox().show(this, "User created successfully.");
            } else {
                this.getMessageBox().showError(this, "User account creation failed. Please take a look at those errors.");
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ConfirmPasswordLabel;
    public javax.swing.JPasswordField ConfrimPasswordTextBox;
    public javax.swing.JTextField EmailTextBox;
    public javax.swing.JPasswordField PasswordTextBox;
    private javax.swing.JLabel PasswrodLabel;
    public javax.swing.JTextField UsernameTextBox;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initalizeTableName() {
        this.getDb().setTableName(TableNames.USER);
    }
}
