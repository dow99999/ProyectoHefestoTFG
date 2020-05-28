/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.models.Enemigo.Tipo;
import com.munozdiego.freenscaelis.models.Entidad.Estado;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author diego
 */
public class Assets {

  private static Map<String, BitmapFont> fonts = new HashMap<>();
  private static Map<String, Music> music = new HashMap<>();
  private static Map<String, Sound> sounds = new HashMap<>();
  private static Map<Tipo, Map<Estado, Animation<TextureRegion>>> enemyAnimations;
  private static Map<Tipo, Map<Estado, Animation<TextureRegion>>> enemyAnimationsAlt;

  public static void init() {
    enemyAnimations = new HashMap<>();
    enemyAnimationsAlt = new HashMap<>();
    initEnemyAnimations();
  }

  /**
   * Method to initialize the visual of the enemies
   */
  private static void initEnemyAnimations() {
    Map<Estado, Animation<TextureRegion>> aux;

    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Goblin/Variant0/", "Goblin_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Goblin/Variant0/", "Goblin_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Goblin/Variant0/", "Goblin_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.GOBLIN, aux);
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Goblin/Variant1/", "Goblin_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Goblin/Variant1/", "Goblin_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Goblin/Variant1/", "Goblin_Death", 4, "png", 0.17f, false));
    enemyAnimationsAlt.put(Tipo.GOBLIN, aux);

    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant0/", "Skeleton_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant0/", "Skeleton_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant0/", "Skeleton_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.SKELETON, aux);
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant1/", "Skeleton_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant1/", "Skeleton_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Skeleton/Variant1/", "Skeleton_Death", 4, "png", 0.17f, false));
    enemyAnimationsAlt.put(Tipo.SKELETON, aux);

    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Slime/Variant0/", "Slime_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Slime/Variant0/", "Slime_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Slime/Variant0/", "Slime_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.SLIME, aux);
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Monsters/Slime/Variant1/", "Slime_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Monsters/Slime/Variant1/", "Slime_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Monsters/Slime/Variant1/", "Slime_Death", 4, "png", 0.17f, false));
    enemyAnimationsAlt.put(Tipo.SLIME, aux);
    
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Goblin King/", "GoblinKing_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Goblin King/", "GoblinKing_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Goblin King/", "GoblinKing_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.BOSS_GOBLIN, aux);
    
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.BOSS_SKELETON, aux);
    
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Walk", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Walk", 4, "png", 0.17f, true));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.BOSS_SLIME, aux);
    
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Obstacle", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Obstacle", 4, "png", 0.17f, false));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Skeleton King/", "SkeletonKing_Ostacle_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.OBSTACLE_SKELETON, aux);
    
    aux = new HashMap<>();
    aux.put(Estado.RUN_RIGHT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Obstacle", 4, "png", 0.17f, false));
    aux.put(Estado.RUN_LEFT, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Obstacle", 4, "png", 0.17f, false));
    aux.put(Estado.DEAD, Assets.getAnimation("Sprites/Bosses/Slime King/", "SlimeKing_Obstacle_Death", 4, "png", 0.17f, false));
    enemyAnimations.put(Tipo.OBSTACLE_SLIME, aux);
    
  }

  /**
   * Funcion que devuelve una animacion
   *
   * @param path String directorio donde buscar
   * @param filename String prefijo del set de imagenes de la animacion
   * @param num int numero de fotogramas
   * @param ext String extension de cada fotograma (debe ser la misma para todos
   * los fotogramas)
   * @param space float tiempo entre fotogramas
   * @param flip boolean si hay que voltear la imagen horizontalmente o no
   * @return Animation<TextureRegion> la animacion
   */
  public static Animation<TextureRegion> getAnimation(String path, String filename, int num, String ext, float space, boolean flip) {
    TextureRegion[] tr = new TextureRegion[num];
    Sprite auxs;

    for (int i = 0; i < num; i++) {
      auxs = new Sprite(new Texture(Gdx.files.internal(path + filename + "_" + i + "." + ext)));
      auxs.flip(flip, true);
      tr[i] = new TextureRegion(auxs);
    }

    return new Animation<>(space, tr);
  }

  /**
   * Funcion que devuelve un sprite a partir de su direccion
   *
   * @param path Strig direccion en la que se encuentra la imagen
   * @return Sprite el sprite de la imagen
   */
  public static Sprite getSprite(String path) {
    Texture auxt;
    Sprite auxs;

    auxt = new Texture(Gdx.files.internal(path));
    auxt.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    auxs = new Sprite(auxt);
    auxs.flip(false, true);

    return auxs;
  }

  /**
   * Metodo para anadir una nueva fuente a las fuentes disponibles en assets
   *
   * @param name String nombre de la fuente con la que se identificara
   * @param size int amano de la fuente
   * @param color Color color de la letra
   * @param path String directorio dentro de los assets del proyecto en el que
   * esta el archivo de fuente(.ttf, .otf)
   */
  public static void addFont(String name, int size, Color color, String path) {
    BitmapFont aux;
    FreeTypeFontParameter param = new FreeTypeFontParameter();
    param.size = size;

    FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
    aux = gen.generateFont(param);
    aux.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    aux.setColor(color);
    aux.getData().setScale(1, -1);

    fonts.put(name, aux);
  }

  /**
   * Funcion para recuperar una fuente de la lista de fuentes
   *
   * @param name String nombre de la fuente
   * @return BitmapFont la fuente
   */
  public static BitmapFont getFont(String name) {
    return fonts.get(name);
  }

  /**
   * Function that returns an animation given the tipe of an enemy and whether
   * or not it has variants
   *
   * @param t Tipo Enemy class
   * @param variant whether or not there are variants of the enemy
   * @return
   */
  public static Map<Estado, Animation<TextureRegion>> getEnemyAnimations(Tipo t, boolean variant) {
    return ((int) (Math.random() * 2)) == 0 && variant ? enemyAnimationsAlt.get(t) : enemyAnimations.get(t);
  }
  
  /**
   * Method to add music
   * @param id String id to retrieve it
   * @param path String path of the music
   */
  public static void addMusic(String id, String path){
    music.put(id, Gdx.audio.newMusic(Gdx.files.internal(path)));
  }
  
  /**
   * Method to get music
   * @param id String id of the music
   * @return Music the music
   */
  public static Music getMusic(String id){
    return music.get(id);
  }
  
  /**
   * Method to add audio
   * @param id String id to retrieve it
   * @param path String path of the audio
   */
  public static void addSound(String id, String path){
    sounds.put(id, Gdx.audio.newSound(Gdx.files.internal(path)));
  }
  
  /**
   * Method to get a sound
   * @param id String id of the sound
   * @return Sound the sound
   */
  public static Sound getSound(String id){
    return sounds.get(id);
  }
}
