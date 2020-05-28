/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import com.munozdiego.freenscaelis.models.Personaje;

/**
 *
 * @author diego
 */
public class UserData {

  private static UserData instance;

  public static UserData getInstance() {
    if (instance == null) {
      instance = new UserData();
    }
    return instance;
  }

  private Personaje[] characters;
  private Personaje current;

  public UserData() {
    characters = new Personaje[3];
  }

  public Personaje[] getCharacters() {
    return characters;
  }
  
  /**
   * Method that sets the user's characters
   * @param p 
   */
  public void setCharacters(Personaje[] p) {
    for(Personaje a : p){
      if(a != null)
        a.init(a.getClase(), 0);
    }
    characters = p;
  }

  /**
   * Method that erases an especific character
   * @param i int index of the character
   */
  public void eraseCharacter(int i) {
    characters[i] = null;
  }

  /**
   * Method that adds a character
   * @param p Personaje the character
   * @return true if it is saved, false if not
   */
  public boolean addCharacter(Personaje p) {
    boolean done = false;
    boolean error = false;
    int i = 0;

    while (i < characters.length && !error) {
      if (characters[i] != null) {
        error = characters[i].getName().equals(p.getName());
      }
      i++;
    }
    
    i = 0;
    if (!error) {
      while (i < characters.length && !done) {
        if (characters[i] == null) {
          characters[i] = p; //ojo con la posicion guardada TODO
          p.init(p.getClase(), 0);
          done = true;
        }
        i++;
      }
    }

    return done;
  }

  /**
   * Method that sets the current character given a character
   * @param p 
   */
  public void selectCharacter(Personaje p) {
    current = p;
  }

  /**
   * Method that returns the current character
   * @return Personaje the current character
   */
  public Personaje getCurrentCharacter() {
    return current;
  }

}
