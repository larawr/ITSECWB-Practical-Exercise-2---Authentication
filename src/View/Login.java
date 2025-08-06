
package View;

import Controller.Main;
import Model.User;
import javax.swing.JOptionPane;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Login extends javax.swing.JPanel {

    public Frame frame;
    
    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameFld = new javax.swing.JTextField();
        passwordFld = new javax.swing.JPasswordField();
        registerBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        loginBtn.setText("LOGIN");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private String getClientIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1"; // Default to localhost if can't determine IP
        }
    }
    
    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        String username = usernameFld.getText().trim();
        String password = new String(passwordFld.getPassword());
        String clientIP = getClientIP();

        // Enhanced input validation and sanitization
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        // Sanitize inputs
        username = frame.main.sqlite.sanitizeInput(username);
        password = frame.main.sqlite.sanitizeInput(password);

        // Check for XSS attempts
        if (!frame.main.sqlite.isInputSafe(username) || !frame.main.sqlite.isInputSafe(password)) {
            JOptionPane.showMessageDialog(this, "Invalid characters detected in input.");
            return;
        }

        // Username format validation
        if (!frame.main.sqlite.isUsernameValid(username)) {
            JOptionPane.showMessageDialog(this, "Invalid username format. Use 3-20 characters, letters, numbers, and underscores only.");
            return;
        }

        // IP-based rate limiting
        if (frame.main.sqlite.isIPRateLimited(clientIP)) {
            JOptionPane.showMessageDialog(this, "Too many login attempts detected. Please wait a few minutes before trying again.");
            frame.main.sqlite.logSecurityEvent("IP_RATE_LIMITED", username, "IP: " + clientIP);
            return;
        }

        // Check if user exists
        if (!frame.main.sqlite.userExists(username)) {
            // Log failed login attempt
            frame.main.sqlite.logLoginAttempt(username, clientIP, false);
            frame.main.sqlite.logSecurityEvent("FAILED_LOGIN", username, "User does not exist - IP: " + clientIP);
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
            return;
        }

        // Check if account is recently locked
        if (frame.main.sqlite.isAccountRecentlyLocked(username)) {
            JOptionPane.showMessageDialog(this, "Account is temporarily locked. Please try again later.");
            return;
        }

        // Check for suspicious activity
        if (frame.main.sqlite.isSuspiciousActivity(username, clientIP)) {
            frame.main.sqlite.logSecurityEvent("SUSPICIOUS_ACTIVITY", username, "Multiple IPs detected - IP: " + clientIP);
            JOptionPane.showMessageDialog(this, "Unusual login activity detected. Please contact support for assistance.");
            return;
        }

        // Calculate progressive delay
        long delay = frame.main.sqlite.calculateDelay(username);
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Authenticate user
        User user = frame.main.sqlite.authenticateUser(username, password);
        
        if (user == null) {
            // Increment failed attempts
            frame.main.sqlite.incrementFailedAttempts(username);
            
            // Log failed login attempt
            frame.main.sqlite.logLoginAttempt(username, clientIP, false);
            frame.main.sqlite.logSecurityEvent("FAILED_LOGIN", username, "Invalid password - IP: " + clientIP);
            
            // Check if account should be locked (after 3 failed attempts)
            int failedAttempts = frame.main.sqlite.getFailedAttempts(username);
            int attemptsLeft = 3 - failedAttempts;
            
            if (attemptsLeft <= 0) {
                frame.main.sqlite.lockUser(username);
                frame.main.sqlite.logSecurityEvent("ACCOUNT_LOCKED", username, "Too many failed attempts - IP: " + clientIP);
                JOptionPane.showMessageDialog(this, "Account temporarily locked for security. Please contact support to unlock your account.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. " + attemptsLeft + " attempts remaining.");
            }
            return;
        }

        // Check if account is locked
        if (user.getLocked() == 1) {
            frame.main.sqlite.logSecurityEvent("LOCKED_LOGIN_ATTEMPT", username, "Attempted login to locked account - IP: " + clientIP);
            JOptionPane.showMessageDialog(this, "Account is locked. Please contact administrator.");
            return;
        }

        // Successful login
        frame.main.sqlite.resetFailedAttempts(username);
        frame.main.sqlite.updateLastLogin(username);
        frame.main.sqlite.logLoginAttempt(username, clientIP, true);
        frame.main.sqlite.logSecurityEvent("SUCCESSFUL_LOGIN", username, "User logged in successfully - IP: " + clientIP);
        
        // Clear password field for security
        passwordFld.setText("");
        
        frame.mainNav();
    }//GEN-LAST:event_loginBtnActionPerformed

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        frame.registerNav();
    }//GEN-LAST:event_registerBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
