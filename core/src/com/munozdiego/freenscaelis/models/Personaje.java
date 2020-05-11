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

  public enum Clase {
    SWORD,
    SCEPTER
  }

  private Clase clase;

  public Personaje() {
    super();
    super.setCurrentState(Estado.IDLE_RIGHT);
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
        this.getAnimaciones().put(Entidad.Estado.DEAD, Assets.getAnimation("Sprites/Player/Sword/", "Player_Death_Sword", 4, "png", 0.17f, false));
        this.getColliders().put(Entidad.Estado.IDLE_LEFT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.IDLE_RIGHT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.RUN_LEFT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.RUN_RIGHT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.DEAD, new Rectangle(0, 0, 40, 70));
        this.speed = 5f;
        break;
      case SCEPTER:
        this.getAnimaciones().put(Entidad.Estado.IDLE_LEFT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Idle_Scepter_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.IDLE_RIGHT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Idle_Scepter_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.RUN_LEFT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Walk_Scepter_Defence" + playernum, 4, "png", 0.17f, true));
        this.getAnimaciones().put(Entidad.Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Player/Scepter/Defence" + playernum + "/", "Player_Walk_Scepter_Defence" + playernum, 4, "png", 0.17f, false));
        this.getAnimaciones().put(Entidad.Estado.DEAD, Assets.getAnimation("Sprites/Player/Scepter/", "Player_Death_Scepter", 4, "png", 0.17f, false));
        this.getColliders().put(Entidad.Estado.IDLE_LEFT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.IDLE_RIGHT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.RUN_LEFT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.RUN_RIGHT, new Rectangle(0, 0, 40, 70));
        this.getColliders().put(Entidad.Estado.DEAD, new Rectangle(0, 0, 40, 70));
        this.speed = 4f;
        break;
    }
  }
}
