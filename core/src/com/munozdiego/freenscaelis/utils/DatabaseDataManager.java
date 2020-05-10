/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author diego
 */
public class DatabaseDataManager {

  private static DatabaseDataManager m_instance;
  private Connection m_conn;

  private String m_user = "";

  /**
   * Method to get an instance of the DatabaseDataManager
   * @return DatabaseDataManager the instance
   */
  public static DatabaseDataManager getInstance() {
    if (m_instance == null) {
      m_instance = new DatabaseDataManager();
    }
    return m_instance;
  }

  /**
   * The constructor starts the connection with the external database
   */
  public DatabaseDataManager() {
    String ip = "localhost";
    String port = "3306";
    String dbname = "freen_game";
    String user = "freen_admin";
    String pass = "rootfreen";

    try {
      m_conn = m_conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbname + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
              user,
              pass);
    } catch (SQLException ex) {
      System.out.println("DB Error: " + ex.getMessage());
    }
  }

  /**
   * This method us ised to retrieve the current logged user
   * @return String name of the user
   */
  public String getUser() { return m_user; }
  
  public void createUser(String user, String pass) {
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

  /**
   * Method to check and log a user
   * @param user String name of the user
   * @param pass String password of the user(non encrypted)
   * @return boolean true ? the user has been logged : the user doesn't exist / no connection to the database
   */
  public boolean checkUserLogin(String user, String pass) {
    PreparedStatement ps;
    ResultSet rs;
    boolean found = false;
    
    try {
      ps = m_conn.prepareStatement("SELECT * FROM usuarios WHERE nombre_user = ? AND pass = ?");
      
      ps.setString(1, user);
      ps.setString(2, stringToMD5(pass));
      
      rs = ps.executeQuery();
      
      if(rs.next()){
        found = true;
        m_user = user;
      }
      
    } catch (SQLException ex) {
    }

    return found;
  }

  /**
   * Method to parse a string to a MD5
   * @param s String to be converted
   * @return String MD5 of the original string
   */
  private String stringToMD5(String s) {
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
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      //Get complete hashed password in hex format
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
    }
    return generatedPassword;
  }
}
