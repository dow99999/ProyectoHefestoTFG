/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models;

/**
 *
 * @author diego
 */
public class Item {
  int id;
  String name;
  int att;
  int def;

  public Item() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAtt() {
    return att;
  }

  public void setAtt(int att) {
    this.att = att;
  }

  public int getDef() {
    return def;
  }

  public void setDef(int def) {
    this.def = def;
  }

  
  
}
