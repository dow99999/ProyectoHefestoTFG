/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models;

import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.utils.Assets;

/**
 *
 * @author diego
 */
public class Personaje extends Entidad {

  private final int COL_WIDTH = 40;
  private final int COL_HEIGHT = 35;
  
  public enum Clase {
    SWORD,
    SCEPTER
  }

  private Clase clase;
  private float invTime;
  private float invTimef;
  
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
        this.getColliders().put(Entidad.Estado.ATT_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.ATT_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.DEAD, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.speed = 5f;
        this.stats[0] = 5;
        break;
      case SCEPTER:
        this.getAnimaciones().put(Entidad.Estado.IDLE_LEFT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Idle_Scepter_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.IDLE_RIGHT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Idle_Scepter_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.RUN_LEFT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Walk_Scepter_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Walk_Scepter_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.ATT_LEFT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Attack_Scepter_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.ATT_RIGHT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Attack_Scepter_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.DEAD, Assets.getAnimation("Sprites/Player/Scepter/", "Player_Death_Scepter", 4, "png", 0.17f, false));
        this.getColliders().put(Entidad.Estado.IDLE_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.IDLE_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.RUN_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.RUN_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.ATT_LEFT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.ATT_RIGHT, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.getColliders().put(Entidad.Estado.DEAD, new Rectangle(0, 0, COL_WIDTH, COL_HEIGHT));
        this.speed = 4f;
        this.stats[0] = 5;
        break;
    }
  }

  public float getInvTime() {
    return invTime;
  }

  public void setInvTime(float invTime) {
    this.invTime = invTime;
  }
  
  public void resetInvTime() {
    this.invTime = this.invTimef;
  }
  
}
