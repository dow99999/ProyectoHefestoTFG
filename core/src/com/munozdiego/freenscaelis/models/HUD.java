/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public class HUD {
  private HashMap<Integer, Sprite> textura_estatica_estados;
  private HashMap<Integer, Animation<TextureRegion>> textura_dinamica_estados;
  private float posx, posy;
  private OrthographicCamera camera;
  
  public HUD(OrthographicCamera camera){
    textura_estatica_estados = new HashMap<>();
    textura_dinamica_estados = new HashMap<>();
    this.camera = camera;
    posx = 0;
    posy = 0;
  }

  public HashMap<Integer, Sprite> getTextura_estatica_estados() {
    return textura_estatica_estados;
  }

  public void setTextura_estatica_estados(HashMap<Integer, Sprite> textura_estatica_estados) {
    this.textura_estatica_estados = textura_estatica_estados;
  }

  public HashMap<Integer, Animation<TextureRegion>> getTextura_dinamica_estados() {
    return textura_dinamica_estados;
  }

  public void setTextura_dinamica_estados(HashMap<Integer, Animation<TextureRegion>> textura_dinamica_estados) {
    this.textura_dinamica_estados = textura_dinamica_estados;
  }

  public float getPosx() {
    return camera.position.x - camera.viewportWidth/2 + posx;
  }

  public void setPosx(float posx) {
    this.posx = posx;
  }

  public float getPosy() {
    return camera.position.y - camera.viewportHeight/2 + posy;
  }

  public void setPosy(float posy) {
    this.posy = posy;
  }
  
  
  
}
