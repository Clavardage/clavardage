package clavardage.model.managers;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Database Manager
 */
abstract public class DatabaseManager {

    private static Connection conn;

    static {
        conn = null;
    }

    public DatabaseManager() {
        this.connect();
    }

    /**
     * Connection to the local SQLite Database
     */
    private void connect() {

        // Reuse existing connection if already instantiated
        if(Objects.nonNull(conn)) {
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver error. [" + e + "]");
            e.printStackTrace();
        }

        AppDirs appDirs = AppDirsFactory.getInstance();
        String app_dir = appDirs.getUserDataDir("Clavardage", null, "Clavardage") + File.separator + "CLAVARDAGE" + File.separator + "db";
        (new File(app_dir)).mkdirs();
        System.out.println("OK: app_dir for database : " + app_dir);

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + app_dir + "//save.db");
        } catch (SQLException e) {
            System.err.println("Database error. [" + e + "]");
            e.printStackTrace();
        }

        /* CREATE DATABASE STRUCTURE IF NOT CREATED */

        try {
            PreparedStatement pstmt = getConnection().prepareStatement("""
CREATE TABLE IF NOT EXISTS user (
    uuid CHAR(36) PRIMARY KEY NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    mail VARCHAR(255) UNIQUE,
    last_ip VARCHAR(255)
)
""");
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = getConnection().prepareStatement("""
CREATE TABLE IF NOT EXISTS conversation (
    uuid CHAR(36) PRIMARY KEY NOT NULL,
    name VARCHER(255),
    date_created DATETIME NOT NULL
)
""");
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = getConnection().prepareStatement("""
CREATE TABLE IF NOT EXISTS user_in_conversation (
    uuid CHAR(36) PRIMARY KEY NOT NULL,
    uuid_user CHAR(36) NOT NULL,
    uuid_conversation CHAR(36) NOT NULL,
    FOREIGN KEY(uuid_user) REFERENCES user(uuid),
    FOREIGN KEY(uuid_conversation) REFERENCES conversation(uuid)
)
""");
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = getConnection().prepareStatement("""
CREATE TABLE IF NOT EXISTS message (
    uuid CHAR(36) PRIMARY KEY NOT NULL,
    text VARCHAR(255) NOT NULL,
    user_conv CHAR(36) NOT NULL,
    FOREIGN KEY(user_conv) REFERENCES user_in_conversation(uuid)
)
""");
            pstmt.executeUpdate();
            pstmt.close();
        } catch(Exception e) {
            closeConnection(); // cancel the connection if error
            System.err.println("Database initialization failed. [" + e + "]");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try { conn.close(); } catch (Exception ignored) { }
        conn = null;
    }
}
