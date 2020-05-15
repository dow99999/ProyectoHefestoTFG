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

  //TODO colliders
  private final Rectangle goblin = new Rectangle(0, 0, 0, 0);
  private final Rectangle skeleton = new Rectangle(0, 0, 0, 0);
  private final Rectangle slime = new Rectangle(0, 0, 50, 50);
  private final Rectangle boss_goblin = new Rectangle(0, 0, 0, 0);
  private final Rectangle boss_slime = new Rectangle(0, 0, 0, 0);
  private final Rectangle boss_skeleton = new Rectangle(0, 0, 0, 0);
  private final Rectangle obstacle_skeleton = new Rectangle(0, 0, 0, 0);
  private final Rectangle obstacle_slime = new Rectangle(0, 0, 0, 0);

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
    directionTime = 0;
    invTime = 0;
    setPosx(x);
    setPosy(y);
  }

  public final void init(Tipo t) {
    int variant;

    switch (t) {
      case GOBLIN:
        variant = (int) (Math.random() * 2);
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Goblin/Variant" + variant + "/", "Goblin_Walk", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Goblin/Variant" + variant + "/", "Goblin_Walk", 4, "png", 0.17f, true));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Goblin/Variant" + variant + "/", "Goblin_Death", 4, "png", 0.17f, false));
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
        variant = (int) (Math.random() * 2);
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant" + variant + "/", "Skeleton_Walk", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant" + variant + "/", "Skeleton_Walk", 4, "png", 0.17f, true));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant" + variant + "/", "Skeleton_Death", 4, "png", 0.17f, false));
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
        variant = (int) (Math.random() * 2);
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Slime/Variant" + variant + "/", "Slime_Walk", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Slime/Variant" + variant + "/", "Slime_Walk", 4, "png", 0.17f, true));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Slime/Variant" + variant + "/", "Slime_Death", 4, "png", 0.17f, false));
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
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Goblin King/", "GoblinKing_Walk", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Goblin King/", "GoblinKing_Walk", 4, "png", 0.17f, true));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Goblin King/", "GoblinKing_Death", 4, "png", 0.17f, false));
        getColliders().put(Estado.RUN_LEFT, boss_goblin);
        getColliders().put(Estado.RUN_RIGHT, boss_goblin);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 1;
        this.stats[0] = 20;
        this.stats[1] = 20;
        followRangePow = 200 * 200;
        followRange = 200;
        directionTimef = 5;
        invTimef = 1;
        break;
      case BOSS_SKELETON:
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Walk", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Walk", 4, "png", 0.17f, true));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Death", 4, "png", 0.17f, false));
        getColliders().put(Estado.RUN_LEFT, boss_skeleton);
        getColliders().put(Estado.RUN_RIGHT, boss_skeleton);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 3;
        this.stats[0] = 40;
        this.stats[1] = 40;
        followRangePow = 200 * 200;
        followRange = 200;
        directionTimef = 5;
        invTimef = 1;
        break;
      case BOSS_SLIME:
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Walk", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Walk", 4, "png", 0.17f, true));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Death", 4, "png", 0.17f, false));
        getColliders().put(Estado.RUN_LEFT, boss_slime);
        getColliders().put(Estado.RUN_RIGHT, boss_slime);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 2;
        this.stats[0] = 60;
        this.stats[1] = 60;
        followRangePow = 200 * 200;
        followRange = 200;
        directionTimef = 5;
        invTimef = 1;
        break;
      case OBSTACLE_SKELETON:
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Obstacle", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Obstacle", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Ostacle_Death", 4, "png", 0.17f, false));
        getColliders().put(Estado.RUN_LEFT, obstacle_skeleton);
        getColliders().put(Estado.RUN_RIGHT, obstacle_skeleton);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
        this.speed = 0;
        this.stats[0] = 1;
        this.stats[1] = 1;
        followRangePow = 0;
        followRange = 0;
        directionTimef = 5;
        invTimef = 1;
        break;
      case OBSTACLE_SLIME:
        getAnimaciones().put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Obstacle", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Obstacle", 4, "png", 0.17f, false));
        getAnimaciones().put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Obstacle_Death", 4, "png", 0.17f, false));
        getColliders().put(Estado.RUN_LEFT, obstacle_slime);
        getColliders().put(Estado.RUN_RIGHT, obstacle_slime);
        getColliders().put(Estado.DEAD, new Rectangle(0, 0, 0, 0));
        getColliderOffset().put(Estado.RUN_LEFT, new Vector2(0, 0));
        getColliderOffset().put(Estado.RUN_RIGHT, new Vector2(0, 0));
        getColliderOffset().put(Estado.DEAD, new Vector2(0, 0));
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
