/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.munozdiego.freenscaelis.utils.Assets;

/**
 *
 * @author diego
 */
public class Personaje extends Entidad {

  private final int COL_WIDTH = 40;
  private final int COL_WIDTH_ATT = 80;
  private final int COL_HEIGHT = 35;
  private final int COL_HEIGHT_ATT = 70;
  
  public enum Clase {
    SWORD,
    AXE
  }

  private float camx;
  private float camy;
  
  private Clase clase;
  private float invTime;
  private float invTimef;
  
  //current player map
  private int mapa;
  
  //player's vertical direction
  private int vDir;
          
  public Personaje() {
    super();
    super.setCurrentState(Estado.IDLE_RIGHT);
    invTime = 0;
    invTimef = 1;
  }

  public Clase getClase() {
    return clase;
  }

  public void setClase(Clase clase) {
    this.clase = clase;
  }

  /**
   * Method to initialize the players stats + visual
   * @param clase
   * @param playernum 
   */
  public void init(Clase clase, int playernum) {
    switch (clase) {
      case SWORD:
        this.getAnimaciones().put(Entidad.Estado.IDLE_LEFT, Assets.getAnimation("Sprites/Player/Sword/Defence" + playernum + "/", "Player_Idle_Sword_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.IDLE_RIGHT, Assets.getAnimation("Sprites/Player/Sword/Defence" + playernum + "/", "Player_Idle_Sword_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.RUN_LEFT, Assets.getAnimation("Sprites/Player/Sword/Defence" + playernum + "/", "Player_Walk_Sword_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Player/Sword/Defence" + playernum + "/", "Player_Walk_Sword_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.ATT_LEFT, Assets.getAnimation("Sprites/Player/Sword/Defence" + playernum + "/", "Player_Attack_Sword_Defence" + playernum, 4, "png", 0.10f, true));
        this.getAnimaciones().put(Entidad.Estado.ATT_RIGHT, Assets.getAnimation("Sprites/Player/Sword/Defence" + playernum + "/", "Player_Attack_Sword_Defence" + playernum, 4, "png", 0.10f, false));
        this.getAnimaciones().put(Entidad.Estado.DEAD, Assets.getAnimation("Sprites/Player/Sword/", "Player_Death_Sword", 4, "png", 0.17f, false));
        this.getColliders().put(Entidad.Estado.IDLE_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.IDLE_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.RUN_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.RUN_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.ATT_LEFT, new Rectangle(0, 0, COL_WIDTH_ATT, COL_HEIGHT_ATT));
        this.getColliders().put(Entidad.Estado.ATT_RIGHT, new Rectangle(0, 0, COL_WIDTH_ATT, COL_HEIGHT_ATT));
        this.getColliders().put(Entidad.Estado.DEAD, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliderOffset().put(Entidad.Estado.IDLE_LEFT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.IDLE_RIGHT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.RUN_LEFT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.RUN_RIGHT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.ATT_LEFT, new Vector2(-25,0));
        this.getColliderOffset().put(Entidad.Estado.ATT_RIGHT, new Vector2(25,0));
        this.getColliderOffset().put(Entidad.Estado.DEAD, new Vector2(0,0));
        this.speed = 5f;
        this.stats[0] = 5;
        this.stats[1] = 1;
        break;
      case AXE:
        this.getAnimaciones().put(Entidad.Estado.IDLE_LEFT, Assets.getAnimation("Sprites/Player/Axe/Defence" + playernum + "/", "Player_Idle_Axe_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.IDLE_RIGHT, Assets.getAnimation("Sprites/Player/Axe/Defence" + playernum + "/", "Player_Idle_Axe_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.RUN_LEFT, Assets.getAnimation("Sprites/Player/Axe/Defence" + playernum + "/", "Player_Walk_Axe_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Player/Axe/Defence" + playernum + "/", "Player_Walk_Axe_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.ATT_LEFT, Assets.getAnimation("Sprites/Player/Axe/Defence" + playernum + "/", "Player_Attack_Axe_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.ATT_RIGHT, Assets.getAnimation("Sprites/Player/Axe/Defence" + playernum + "/", "Player_Attack_Axe_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.DEAD, Assets.getAnimation("Sprites/Player/Axe/", "Player_Death_Axe", 4, "png", 0.17f, false));
        this.getColliders().put(Entidad.Estado.IDLE_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.IDLE_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.RUN_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.RUN_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.ATT_LEFT, new Rectangle(0, 0, COL_WIDTH_ATT, COL_HEIGHT_ATT));
        this.getColliders().put(Entidad.Estado.ATT_RIGHT, new Rectangle(0, 0, COL_WIDTH_ATT, COL_HEIGHT_ATT));
        this.getColliders().put(Entidad.Estado.DEAD, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliderOffset().put(Entidad.Estado.IDLE_LEFT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.IDLE_RIGHT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.RUN_LEFT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.RUN_RIGHT, new Vector2(0,0));
        this.getColliderOffset().put(Entidad.Estado.ATT_LEFT, new Vector2(-25,0));
        this.getColliderOffset().put(Entidad.Estado.ATT_RIGHT, new Vector2(25,0));
        this.getColliderOffset().put(Entidad.Estado.DEAD, new Vector2(0,0));
        this.speed = 4f;
        this.stats[0] = 5;
        this.stats[1] = 2;
        break;
    }
  }

  //Getters & Setters
  public float getInvTime() {
    return invTime;
  }

  public void setInvTime(float invTime) {
    this.invTime = invTime;
  }
  
  public void resetInvTime() {
    this.invTime = this.invTimef;
  }

  public float getCamx() {
    return camx;
  }

  public void setCamx(float camx) {
    this.camx = camx;
  }

  public float getCamy() {
    return camy;
  }

  public void setCamy(float camy) {
    this.camy = camy;
  }

  public int getMapa() {
    return mapa;
  }

  public void setMapa(int mapa) {
    this.mapa = mapa;
  }

  public int getvDir() {
    return vDir;
  }

  public void setvDir(int vDir) {
    this.vDir = vDir;
  }
  
}
