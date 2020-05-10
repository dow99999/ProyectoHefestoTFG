/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author diego
 */
public class Entidad{
  public enum Estado{
    IDLE_RIGHT,
    IDLE_LEFT,
    RUN_RIGHT,
    RUN_LEFT,
    DEAD
  }
  protected float posx, posy;
  protected float vida;
  protected Item[] inventario;
  protected int[] stats;
  protected Sprite textura;
  protected Estado currentState;
  protected Map<Estado, Animation<TextureRegion>> animaciones;
  protected Map<Estado, Rectangle> colliders;
  
  public Entidad(){
    stats = new int[2];
    inventario = new Item[10];
    
    animaciones = new HashMap<>();
    colliders = new HashMap<>();
  }

  public Estado getCurrentState() {
    return currentState;
  }

  public void setCurrentState(Estado currentState) {
    this.currentState = currentState;
  }
  
  public float getPosx() {
    return posx;
  }

  public void setPosx(float posx) {
    this.posx = posx;
  }

  public float getPosy() {
    return posy;
  }

  public void setPosy(float posy) {
    this.posy = posy;
  }

  public float getVida() {
    return vida;
  }

  public void setVida(float vida) {
    this.vida = vida;
  }

  public Item[] getInventario() {
    return inventario;
  }

  public void setInventario(Item[] inventario) {
    this.inventario = inventario;
  }

  public int[] getStats() {
    return stats;
  }

  public void setStats(int[] stats) {
    this.stats = stats;
  }

  public Sprite getTextura() {
    return textura;
  }

  public void setTextura(Sprite textura) {
    this.textura = textura;
  }

  public Map<Estado, Animation<TextureRegion>> getAnimaciones() {
    return animaciones;
  }

  public void setAnimaciones(Map<Estado, Animation<TextureRegion>> animaciones) {
    this.animaciones = animaciones;
  }

  public Map<Estado, Rectangle> getColliders() {
    return colliders;
  }

  public void setColliders(Map<Estado, Rectangle> colliders) {
    this.colliders = colliders;
  }
  
  
}
