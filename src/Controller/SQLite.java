package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

public class SQLite {
    
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";
    
    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " name TEXT NOT NULL,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " event TEXT NOT NULL,\n"
            + " username TEXT NOT NULL,\n"
            + " desc TEXT NOT NULL,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " name TEXT NOT NULL UNIQUE,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " price REAL DEFAULT 0.00\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL UNIQUE,\n"
            + " password TEXT NOT NULL,\n"
            + " role INTEGER DEFAULT 2,\n"
            + " locked INTEGER DEFAULT 0,\n"
            + " failed_attempts INTEGER DEFAULT 0,\n"
            + " last_login TEXT\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES('" + username + "','" + name + "','" + stock + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES('" + event + "','" + username + "','" + desc + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES('" + name + "','" + stock + "','" + price + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public ArrayList<History> getHistory(){
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return histories;
    }
    
    public ArrayList<Logs> getLogs(){
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<Logs>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                                   rs.getString("event"),
                                   rs.getString("username"),
                                   rs.getString("desc"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logs;
    }
    
    public ArrayList<Product> getProduct(){
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<Product>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return products;
    }
    
    public ArrayList<User> getUsers(){
        String sql = "SELECT id, username, password, role, locked, failed_attempts FROM users";
        ArrayList<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("locked"),
                                   rs.getInt("failed_attempts")));
            }
        } catch (Exception ex) {}
        return users;
    }
    
    public boolean addUser(String username, String password, int role) {
        if (userExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, role);
            stmt.executeUpdate();
            return true;

        } catch (Exception ex) {
            System.out.println("Error adding user: " + ex.getMessage());
            return false;
        }
    }
    
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            if (rs.next()) {
                product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return product;
    }

    public boolean userExists(String username) {
        String query = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            boolean exists = rs.next(); // true if a row is returned
            rs.close();
            return exists;

        } catch (Exception e) {
            System.out.println("Error checking user existence: " + e.getMessage());
            return false; // assume false on failure to prevent false positives
        }
    }
    
    public User authenticateUser(String username, String password) {
        String query = "SELECT id, username, password, role, locked, failed_attempts FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                // Check if password matches (for hashed passwords)
                if (BCrypt.checkpw(password, storedPassword)) {
                    return new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("locked"),
                                   rs.getInt("failed_attempts"));
                }
            }
            return null;

        } catch (Exception e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return null;
        }
    }
    
    public void incrementFailedAttempts(String username) {
        String sql = "UPDATE users SET failed_attempts = failed_attempts + 1 WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("Failed attempts incremented for user: " + username);

        } catch (Exception ex) {
            System.out.println("Error incrementing failed attempts: " + ex.getMessage());
        }
    }
    
    public void resetFailedAttempts(String username) {
        String sql = "UPDATE users SET failed_attempts = 0 WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("Failed attempts reset for user: " + username);

        } catch (Exception ex) {
            System.out.println("Error resetting failed attempts: " + ex.getMessage());
        }
    }
    
    public void lockUser(String username) {
        String sql = "UPDATE users SET locked = 1 WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("User account locked: " + username);

        } catch (Exception ex) {
            System.out.println("Error locking user account: " + ex.getMessage());
        }
    }
    
    public int getFailedAttempts(String username) {
        String query = "SELECT failed_attempts FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("failed_attempts");
            }
            return 0;

        } catch (Exception e) {
            System.out.println("Error getting failed attempts: " + e.getMessage());
            return 0;
        }
    }
    
    // New security methods
    
    public boolean isPasswordComplex(String password) {
        // Check for minimum 8 characters, at least one uppercase, one lowercase, one digit, one special character
        if (password.length() < 8) return false;
        
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    public boolean isCommonPassword(String password) {
        // Simple common password check - in production, use a comprehensive list
        String[] commonPasswords = {
            "password", "123456", "123456789", "qwerty", "abc123", 
            "password123", "admin", "letmein", "welcome", "monkey"
        };
        
        String lowerPassword = password.toLowerCase();
        for (String common : commonPasswords) {
            if (lowerPassword.equals(common)) return true;
        }
        return false;
    }
    
    public void logSecurityEvent(String event, String username, String details) {
        String sql = "INSERT INTO logs(event, username, desc, timestamp) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, event);
            stmt.setString(2, username);
            stmt.setString(3, details);
            stmt.setString(4, new java.sql.Timestamp(System.currentTimeMillis()).toString());
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error logging security event: " + ex.getMessage());
        }
    }
    
    public boolean isUsernameValid(String username) {
        // Username validation: 3-20 characters, alphanumeric and underscore only
        if (username == null || username.length() < 3 || username.length() > 20) {
            return false;
        }
        
        return username.matches("^[a-zA-Z0-9_]+$");
    }
    
    public void updateLastLogin(String username) {
        String sql = "UPDATE users SET last_login = ? WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, new java.sql.Timestamp(System.currentTimeMillis()).toString());
            stmt.setString(2, username);
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error updating last login: " + ex.getMessage());
        }
    }
    
    public boolean isAccountRecentlyLocked(String username) {
        // Check if account was locked in the last 30 minutes
        String sql = "SELECT locked, last_login FROM users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int locked = rs.getInt("locked");
                String lastLogin = rs.getString("last_login");
                
                if (locked == 1 && lastLogin != null) {
                    // Simple check - in production, use proper timestamp comparison
                    return true;
                }
            }
            
        } catch (Exception ex) {
            System.out.println("Error checking account lock status: " + ex.getMessage());
        }
        
        return false;
    }
    
    // Rate Limiting and Brute Force Protection
    
    public void logLoginAttempt(String username, String ipAddress, boolean success) {
        String sql = "INSERT INTO login_attempts(username, ip_address, success, timestamp) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, ipAddress);
            stmt.setBoolean(3, success);
            stmt.setString(4, new java.sql.Timestamp(System.currentTimeMillis()).toString());
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error logging login attempt: " + ex.getMessage());
        }
    }
    
    public int getRecentFailedAttempts(String username, int minutes) {
        String sql = "SELECT COUNT(*) FROM login_attempts WHERE username = ? AND success = 0 AND timestamp > datetime('now', '-" + minutes + " minutes')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (Exception ex) {
            System.out.println("Error getting recent failed attempts: " + ex.getMessage());
        }
        
        return 0;
    }
    
    public int getRecentFailedAttemptsByIP(String ipAddress, int minutes) {
        String sql = "SELECT COUNT(*) FROM login_attempts WHERE ip_address = ? AND success = 0 AND timestamp > datetime('now', '-" + minutes + " minutes')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ipAddress);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (Exception ex) {
            System.out.println("Error getting recent failed attempts by IP: " + ex.getMessage());
        }
        
        return 0;
    }
    
    public long calculateDelay(String username) {
        int failedAttempts = getRecentFailedAttempts(username, 30); // Last 30 minutes
        
        // Progressive delay: 1s, 2s, 4s, 8s, 16s, 30s, 60s, 120s
        if (failedAttempts <= 0) return 0;
        if (failedAttempts == 1) return 1000; // 1 second
        if (failedAttempts == 2) return 2000; // 2 seconds
        if (failedAttempts == 3) return 4000; // 4 seconds
        if (failedAttempts == 4) return 8000; // 8 seconds
        if (failedAttempts == 5) return 16000; // 16 seconds
        if (failedAttempts == 6) return 30000; // 30 seconds
        if (failedAttempts == 7) return 60000; // 1 minute
        return 120000; // 2 minutes for 8+ attempts
    }
    
    public boolean isIPRateLimited(String ipAddress) {
        int recentAttempts = getRecentFailedAttemptsByIP(ipAddress, 5); // Last 5 minutes
        return recentAttempts >= 10; // Block IP after 10 failed attempts in 5 minutes
    }
    
    // Password History Management
    
    public void addPasswordToHistory(String username, String hashedPassword) {
        String sql = "INSERT INTO password_history(username, password_hash, created_at) VALUES(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, new java.sql.Timestamp(System.currentTimeMillis()).toString());
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Error adding password to history: " + ex.getMessage());
        }
    }
    
    public boolean isPasswordInHistory(String username, String password) {
        String sql = "SELECT password_hash FROM password_history WHERE username = ? ORDER BY created_at DESC LIMIT 5";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (BCrypt.checkpw(password, storedHash)) {
                    return true; // Password found in recent history
                }
            }
            
        } catch (Exception ex) {
            System.out.println("Error checking password history: " + ex.getMessage());
        }
        
        return false;
    }
    
    // Enhanced Input Validation
    
    public boolean isInputSafe(String input) {
        if (input == null) return false;
        
        // Check for common XSS patterns
        String lowerInput = input.toLowerCase();
        String[] xssPatterns = {
            "<script", "javascript:", "onload=", "onerror=", "onclick=",
            "eval(", "document.cookie", "alert(", "prompt(", "confirm("
        };
        
        for (String pattern : xssPatterns) {
            if (lowerInput.contains(pattern)) {
                return false;
            }
        }
        
        return true;
    }
    
    public String sanitizeInput(String input) {
        if (input == null) return "";
        
        // Remove or escape potentially dangerous characters
        return input.replaceAll("[<>\"']", "")
                   .replaceAll("javascript:", "")
                   .replaceAll("on\\w+=", "")
                   .trim();
    }
    
    // Suspicious Activity Detection
    
    public boolean isSuspiciousActivity(String username, String ipAddress) {
        // Check for multiple failed attempts from different IPs
        String sql = "SELECT COUNT(DISTINCT ip_address) FROM login_attempts WHERE username = ? AND success = 0 AND timestamp > datetime('now', '-10 minutes')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int uniqueIPs = rs.getInt(1);
                return uniqueIPs > 2; // Suspicious if failed attempts from more than 2 different IPs
            }
            
        } catch (Exception ex) {
            System.out.println("Error checking suspicious activity: " + ex.getMessage());
        }
        
        return false;
    }
    
    public void createLoginAttemptsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS login_attempts (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " ip_address TEXT NOT NULL,\n"
            + " success BOOLEAN NOT NULL,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table login_attempts in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createPasswordHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS password_history (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " password_hash TEXT NOT NULL,\n"
            + " created_at TEXT NOT NULL\n"
            + ");";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table password_history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
}
