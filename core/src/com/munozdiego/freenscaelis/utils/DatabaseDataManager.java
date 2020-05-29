/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import com.munozdiego.freenscaelis.models.Personaje;
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

  private String m_user = "";

  /**
   * Method to get an instance of the DatabaseDataManager
   *
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
   *
   * @return String name of the user
   */
  public String getUser() {
    return m_user;
  }

  public void createUser(String user, String pass) {
    PreparedStatement ps;
    try {
      ps = m_conn.prepareStatement("INSERT INTO usuarios VALUES(?,?)");
      ps.setString(1, user);
      ps.setString(2, stringToMD5(pass));
      ps.execute();
    } catch (SQLException ex) {
    }
  }

  /**
   * Method to check and log a user
   *
   * @param user String name of the user
   * @param pass String password of the user(non encrypted)
   * @return boolean true ? the user has been logged : the user doesn't exist /
   * no connection to the database
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

      if (rs.next()) {
        found = true;
        m_user = user;
      }

    } catch (SQLException ex) {
    }

    return found;
  }

  /**
   * Method to parse a string to a MD5
   *
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

  /**
   * Method to save the user's characters on cloud
   *
   * @param cs Personaje[] the user's characters
   */
  public void saveCharacters(Personaje[] cs) {
    PreparedStatement ps;

    try {
      ps = m_conn.prepareCall("DELETE FROM personajes WHERE nombre_user = ?");
      ps.setString(1, m_user);
      ps.execute();

      for (Personaje p : cs) {
        if (p != null) {
          ps = m_conn.prepareCall("INSERT INTO personajes (nombre_pj, clase, mapa, pos_x, pos_y, cam_x, cam_y, nombre_user) VALUES (?,?,?,?,?,?,?,?)");
          ps.setString(1, p.getName());
          ps.setInt(2, p.getClase().ordinal());
          ps.setInt(3, p.getMapa());
          ps.setInt(4, (int) p.getPosx());
          ps.setInt(5, (int) p.getPosy());
          ps.setInt(6, (int) p.getCamx());
          ps.setInt(7, (int) p.getCamy());
          ps.setString(8, m_user);
          ps.execute();
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    }
  }
  
  /**
   * Method to retrieve the user's characters from the database
   * @return Personaje[] the user's characters
   */
  public Personaje[] loadCharacters(){
    Personaje[] aux = null;
    PreparedStatement ps;
    ResultSet rs;
    Personaje auxp;
    int i;
    
    aux = new Personaje[3];
    for(i = 0; i < aux.length; i++)
      aux[i] = null;
    i = 0;
    try{
      ps = m_conn.prepareCall("SELECT * FROM personajes WHERE nombre_user = ?");
      ps.setString(1, m_user);
      rs = ps.executeQuery();
      
      while(rs.next()){
        auxp = new Personaje();
        auxp.setName(rs.getString("nombre_pj"));
        auxp.setClase(Personaje.Clase.values()[rs.getInt("clase")]);
        auxp.setMapa(rs.getInt("mapa"));
        auxp.setPosx(rs.getInt("pos_x"));
        auxp.setPosy(rs.getInt("pos_y"));
        auxp.setCamx(rs.getInt("cam_x"));
        auxp.setCamy(rs.getInt("cam_y"));
        aux[i] = auxp;
        i++;
      }
      
    }catch(SQLException e){
      System.out.println(e);
    }
    
    return aux;
  }
}
