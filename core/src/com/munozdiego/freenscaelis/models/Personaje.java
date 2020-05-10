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
public class Personaje extends Entidad {
  public Personaje(){
    super();
    super.setCurrentState(Estado.IDLE_RIGHT);
  }
}
