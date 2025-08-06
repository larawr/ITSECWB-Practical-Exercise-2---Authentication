package View;

import javax.swing.*;

public class Register extends javax.swing.JPanel {

    public Frame frame;

    public Register() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        registerBtn = new javax.swing.JButton();
        passwordFld = new javax.swing.JPasswordField(); // changed from JTextField
        usernameFld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        confpassFld = new javax.swing.JPasswordField(); // changed from JTextField
        backBtn = new javax.swing.JButton();

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24));
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18));
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true),
                "PASSWORD", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18));
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true),
                "USERNAME", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        confpassFld.setBackground(new java.awt.Color(240, 240, 240));
        confpassFld.setFont(new java.awt.Font("Tahoma", 0, 18));
        confpassFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confpassFld.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true),
                "CONFIRM PASSWORD", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));

        backBtn.setFont(new java.awt.Font("Tahoma", 1, 12));
        backBtn.setText("<Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(200, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(usernameFld)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(confpassFld, javax.swing.GroupLayout.Alignment.LEADING))
                    .addContainerGap(200, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(backBtn)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(backBtn)
                    .addGap(24, 24, 24)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)
                    .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(confpassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(64, Short.MAX_VALUE))
        );
    }


    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameFld.getText().trim();
        String password = new String(passwordFld.getPassword());
        String confirmPassword = new String(confpassFld.getPassword());

        // Enhanced input validation and sanitization
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        // Sanitize inputs
        username = frame.main.sqlite.sanitizeInput(username);
        password = frame.main.sqlite.sanitizeInput(password);
        confirmPassword = frame.main.sqlite.sanitizeInput(confirmPassword);

        // Check for XSS attempts
        if (!frame.main.sqlite.isInputSafe(username) || !frame.main.sqlite.isInputSafe(password)) {
            JOptionPane.showMessageDialog(this, "Invalid characters detected in input.");
            return;
        }

        // Username validation
        if (!frame.main.sqlite.isUsernameValid(username)) {
            JOptionPane.showMessageDialog(this, "Invalid username format. Use 3-20 characters, letters, numbers, and underscores only.");
            return;
        }

        // Check if username already exists
        if (frame.main.sqlite.userExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.");
            return;
        }

        // Password confirmation validation
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        // Enhanced password validation
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.");
            return;
        }

        // Password complexity validation
        if (!frame.main.sqlite.isPasswordComplex(password)) {
            JOptionPane.showMessageDialog(this, "Password must contain at least:\n" +
                "• 8 characters\n" +
                "• One uppercase letter\n" +
                "• One lowercase letter\n" +
                "• One number\n" +
                "• One special character");
            return;
        }

        // Check for common passwords
        if (frame.main.sqlite.isCommonPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password is too common. Please choose a more secure password.");
            return;
        }

        // Proceed with registration
        boolean success = frame.registerAction(username, password, confirmPassword);
        
        if (success) {
            // Log successful registration
            frame.main.sqlite.logSecurityEvent("USER_REGISTERED", username, "New user account created");
            
            // Clear sensitive fields
            passwordFld.setText("");
            confpassFld.setText("");
            
            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.");
            frame.loginNav();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
        }
    }


    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        frame.loginNav();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton backBtn;
    private javax.swing.JPasswordField confpassFld; // changed
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField passwordFld; // changed
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration
}
