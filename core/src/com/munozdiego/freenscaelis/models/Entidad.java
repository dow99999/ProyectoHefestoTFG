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
    ATT_RIGHT,
    ATT_LEFT,
    DEAD
  }
  protected float posx, posy;
  protected Item[] inventario;
  protected int[] stats;
  protected Sprite textura;
  protected Estado currentState;
  protected String name;
  protected float speed;
  protected Map<Estado, Animation<TextureRegion>> animaciones;
  protected Map<Estado, Rectangle> colliders;
  
  public Entidad(){
    stats = new int[2];
    inventario = new Item[10];
    name = "Name";
    animaciones = new HashMap<>();
    colliders = new HashMap<>();
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }
  
  public Estado getCurrentState() {
    return currentState;
  }

  public void setCurrentState(Estado currentState) {
    this.currentState = currentState;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public float getCenterX(){
    return animaciones.get(Estado.RUN_RIGHT).getKeyFrame(0).getRegionWidth()/2;
  }
  
  public float getCenterY(){
    return animaciones.get(Estado.RUN_RIGHT).getKeyFrame(0).getRegionHeight()/2;
  }
  
  private void updateCollider(){
    for(Rectangle aux : this.colliders.values()){
      aux.setX(posx+getCenterX()-(aux.getWidth()/2));
      aux.setY(posy+(getCenterY()*1.5f));
    } 
  }
  
  public float getPosx() {
    return posx;
  }

  public void setPosx(float posx) {
    this.posx = posx;
    if(!animaciones.isEmpty())
      updateCollider();
  }

  public float getPosy() {
    return posy;
  }

  public void setPosy(float posy) {
    this.posy = posy;
    if(!animaciones.isEmpty())
      updateCollider();
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
