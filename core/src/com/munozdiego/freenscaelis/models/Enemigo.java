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
public class Enemigo extends Entidad {

  public enum Tipo {
    GOBLIN,
    SKELETON,
    SLIME,
    BOSS_GOBLIN,
    BOSS_SLIME,
    BOSS_SKELETON,
    OBSTACLE_SKELETON,
    OBSTACLE_SLIME
  }

  
  public Tipo tipo;
  
  private final Rectangle goblin = new Rectangle(0, 0, 20, 50);
  private final Rectangle skeleton = new Rectangle(0, 0, 40, 80);
  private final Rectangle slime = new Rectangle(0, 0, 80, 50);
  private final Rectangle boss_goblin = new Rectangle(0, 0, 80, 130);
  private final Rectangle boss_slime = new Rectangle(0, 0, 290, 130);
  private final Rectangle boss_skeleton = new Rectangle(0, 0, 240, 160);
  private final Rectangle obstacle_skeleton = new Rectangle(0, 0, 45, 50);
  private final Rectangle obstacle_slime = new Rectangle(0, 0, 40, 40);

  private float followRangePow;
  private float followRange;

  private float invTimef;
  private float invTime;

  private float directionTimef;
  private float directionTime;
  private int direction;

  public Enemigo() {
    super();
    super.setCurrentState(Estado.RUN_LEFT);
  }

  public Enemigo(int x, int y, Tipo t) {
    super();
    super.setCurrentState(Estado.RUN_LEFT);
    init(t);
    tipo = t;
    directionTime = 0;
    invTime = 0;
    setPosx(x);
    setPosy(y);
  }

  public final void init(Tipo t) {
    switch (t) {
      case GOBLIN:
        setAnimaciones(Assets.getEnemyAnimations(t, true));
        getColliders().put(Estado.RUN_LEFT, goblin);
        getColliders().put(Estado.RUN_RIGHT, goblin);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 4;
        this.stats[0] = 2;
        this.stats[1] = 2;
        followRangePow = 200 * 200;
        followRange = 200;
        directionTimef = 5;
        invTimef = 1;
        break;
      case SKELETON:
        setAnimaciones(Assets.getEnemyAnimations(t, true));
        getColliders().put(Estado.RUN_LEFT, skeleton);
        getColliders().put(Estado.RUN_RIGHT, skeleton);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 3;
        this.stats[0] = 3;
        this.stats[1] = 3;
        followRangePow = 200 * 200;
        followRange = 200;
        directionTimef = 5;
        invTimef = 1;
        break;
      case SLIME:
        setAnimaciones(Assets.getEnemyAnimations(t, true));
        getColliders().put(Estado.RUN_LEFT, slime);
        getColliders().put(Estado.RUN_RIGHT, slime);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 2;
        this.stats[0] = 5;
        this.stats[1] = 2;
        followRangePow = 200 * 200;
        followRange = 200;
        directionTimef = 5;
        invTimef = 0.5f;
        break;
      case BOSS_GOBLIN:
        setAnimaciones(Assets.getEnemyAnimations(t, false));
        getColliders().put(Estado.RUN_LEFT, boss_goblin);
        getColliders().put(Estado.RUN_RIGHT, boss_goblin);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 1;
        this.stats[0] = 20;
        this.stats[1] = 20;
        followRangePow = 600 * 600;
        followRange = 600;
        directionTimef = 5;
        invTimef = 1;
        break;
      case BOSS_SKELETON:
        setAnimaciones(Assets.getEnemyAnimations(t, false));
        getColliders().put(Estado.RUN_LEFT, boss_skeleton);
        getColliders().put(Estado.RUN_RIGHT, boss_skeleton);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, -40));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, -40));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, -40));
        this.speed = 3;
        this.stats[0] = 40;
        this.stats[1] = 40;
        followRangePow = 600 * 600;
        followRange = 600;
        directionTimef = 5;
        invTimef = 1;
        break;
      case BOSS_SLIME:
        setAnimaciones(Assets.getEnemyAnimations(t, false));
        getColliders().put(Estado.RUN_LEFT, boss_slime);
        getColliders().put(Estado.RUN_RIGHT, boss_slime);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, -90));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, -90));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, -90));
        this.speed = 2;
        this.stats[0] = 60;
        this.stats[1] = 60;
        followRangePow = 800 * 800;
        followRange = 800;
        directionTimef = 5;
        invTimef = 1;
        break;
      case OBSTACLE_SKELETON:
        setAnimaciones(Assets.getEnemyAnimations(t, false));
        getColliders().put(Estado.RUN_LEFT, obstacle_skeleton);
        getColliders().put(Estado.RUN_RIGHT, obstacle_skeleton);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, -130));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, -130));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, -130));
        this.speed = 0;
        this.stats[0] = 1;
        this.stats[1] = 1;
        followRangePow = 0;
        followRange = 0;
        directionTimef = 5;
        invTimef = 1;
        break;
      case OBSTACLE_SLIME:
        setAnimaciones(Assets.getEnemyAnimations(t, false));
        getColliders().put(Estado.RUN_LEFT, obstacle_slime);
        getColliders().put(Estado.RUN_RIGHT, obstacle_slime);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, -80));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, -80));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, -80));
        this.speed = 0;
        this.stats[0] = 1;
        this.stats[1] = 1;
        followRangePow = 0;
        followRange = 0;
        directionTimef = 5;
        invTimef = 1;
        break;
    }
  }

  public float getFollowRangePow() {
    return followRangePow;
  }

  public void setFollowRangePow(float followRangePow) {
    this.followRangePow = followRangePow;
  }

  public float getFollowRange() {
    return followRange;
  }

  public void setFollowRange(float followRange) {
    this.followRange = followRange;
  }

  public float getDirectionTime() {
    return directionTime;
  }
  
  public float getDirectionTimeF() {
    return directionTimef;
  }

  public void setDirectionTime(float directionTime) {
    this.directionTime = directionTime;
  }

  public void resetDirectionTime() {
    this.directionTime = this.directionTimef;
  }

  public float getInvTime() {
    return this.invTime;
  }

  public void setInvTime(float invTime) {
    this.invTime = invTime;
  }

  public void resetInvTime() {
    this.invTime = this.invTimef;
  }

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

}
