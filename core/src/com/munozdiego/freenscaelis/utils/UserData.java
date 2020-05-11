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

  public void setCharacters(Personaje[] p) {
    characters = p;
  }

  public void eraseCharacter(int i) {
    characters[i] = null;
  }

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
          characters[i] = p;
          done = true;
        }
        i++;
      }
    }

    return done;
  }

  public void selectCharacter(Personaje p) {
    current = p;
  }

  public Personaje getCurrentCharacter() {
    return current;
  }

}
