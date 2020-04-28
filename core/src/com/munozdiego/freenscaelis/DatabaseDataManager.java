/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class DatabaseDataManager {
    private static DatabaseDataManager m_instance;
    private Connection m_conn;
    
    public static DatabaseDataManager getInstance(){
        if(m_instance == null)
            m_instance = new DatabaseDataManager();
        return m_instance;
    }
    
    public DatabaseDataManager(){
        String ip = "localhost";
        String port = "3306";
        String dbname = "freen_game";
        String user = "freen_admin";
        String pass = "rootfreen";
        
        try {
            m_conn = m_conn = DriverManager.getConnection("jdbc:mysql://" + ip +":" + port + "/" + dbname + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    user,
                    pass);
        } catch (SQLException ex) {
            System.out.println("DB Error: " + ex.getMessage());
        }
    }
    
    public void createUser(String user, String pass){
        PreparedStatement ps;
        try {
            ps = m_conn.prepareStatement("INSERT INTO usuarios VALUES(?,?)");
            ps.setString(1, user);
            ps.setString(2, stringToMD5(pass));
            ps.execute();
        } catch (SQLException ex) {
            System.out.println("TODO: ERROR HANDLING (user already exists / database conection error)");
        }
    }
    
    public boolean checkUserLogin(String user, String pass){
        PreparedStatement ps;
        ResultSet rs;
        
        //TODO
        
        return true;
    }
    
    private String stringToMD5(String s){
        String generatedPassword = "null";
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(s.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {}
        return generatedPassword;
    }
}
