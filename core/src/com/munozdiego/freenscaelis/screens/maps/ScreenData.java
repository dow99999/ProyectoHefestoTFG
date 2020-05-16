/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.screens.maps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.munozdiego.freenscaelis.models.Enemigo;
import com.munozdiego.freenscaelis.utils.Assets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author diego
 */
public class ScreenData {

  public static final int PUEBLO_INICIAL = 0,
          BOSQUE_ESTE = 1,
          BOSQUE_NORTE = 2,
          BOSQUE_SUR = 3,
          PLAYA = 4;

  private static ScreenData instance;

  public static ScreenData getInstance() {
    if (instance == null) {
      instance = new ScreenData();
    }
    return instance;
  }

  private int currentMapa;

  public ScreenData() {
    currentMapa = PUEBLO_INICIAL;
    initWarpZones();
    initWarpPositions();
    initEnemiesPositions();
  }

  private final Rectangle[][] colliders = new Rectangle[][]{
    {
      new Rectangle(384, 661, 670, 730),
      new Rectangle(1056, 661, 239, 490),
      new Rectangle(1440, 661, 239, 490),
      new Rectangle(1679, 661, 623, 730),
      new Rectangle(577, 1656, 478, 552),
      new Rectangle(1055, 1909, 241, 297),
      new Rectangle(1441, 1909, 241, 297),
      new Rectangle(1681, 1656, 429, 552),
      new Rectangle(383, 1535, 94, 901),
      new Rectangle(383, 2436, 911, 58),
      new Rectangle(1440, 2436, 863, 58),
      new Rectangle(2208, 1535, 94, 901),
      new Rectangle(1214, 0, 77, 680),
      new Rectangle(1439, 0, 80, 666),
      new Rectangle(2304, 1325, 334, 66),
      new Rectangle(2304, 1538, 334, 80),
      new Rectangle(1440, 2495, 65, 383),
      new Rectangle(1220, 2496, 76, 383),
      new Rectangle(0, 1316, 385, 76),
      new Rectangle(0, 1536, 385, 66)
    },
    {
      new Rectangle(0,0,629,1791),
      new Rectangle(0,1918,628,232),
      new Rectangle(0,2161,2696,638),
      new Rectangle(756,1144,691,889),
      new Rectangle(1287,469,489,1037),
      new Rectangle(1780,1049,918,450),
      new Rectangle(1622,1624,1076,541),
      new Rectangle(626,0,4172,371),
      new Rectangle(1912,349,2886,570),
      new Rectangle(2831,928,1966,570),
      new Rectangle(2437,2803,256,186),
      new Rectangle(2436,3109,2361,409),
      new Rectangle(0,3538,4795,300),
      new Rectangle(0,2784,1825,767),
      new Rectangle(4019,1620,776,1501),
      new Rectangle(3208,2105,304,305),
      new Rectangle(3204,2422,165,234)
    }
  };

  private void initWarpZones() {
    HashMap<Rectangle, Integer> aux;

    aux = new HashMap<>();
    aux.put(new Rectangle(1296, 0, 145, 61), BOSQUE_NORTE);
    aux.put(new Rectangle(2578, 1391, 60, 144), BOSQUE_ESTE);
    aux.put(new Rectangle(1295, 2821, 145, 57), BOSQUE_SUR);
    aux.put(new Rectangle(0, 1391, 95, 145), PLAYA);
    warpZones.put(PUEBLO_INICIAL, aux);
    aux = new HashMap<>();
    aux.put(new Rectangle(0, 1775, 57, 144), PUEBLO_INICIAL);
    aux.put(new Rectangle(4702, 1502, 93, 130), PUEBLO_INICIAL); //TODO cambio a mapa final
    warpZones.put(BOSQUE_ESTE, aux);
  }

  private final HashMap<Integer, HashMap<Rectangle, Integer>> warpZones = new HashMap<>();

  private final Sprite[][] layers = new Sprite[][]{
    {
      Assets.getSprite("maps/pueblo_principal/ciudad_principal_base.png"),
      Assets.getSprite("maps/pueblo_principal/ciudad_principal_muralla_detras.png"),
      Assets.getSprite("maps/pueblo_principal/ciudad_principal_edificios.png"),
      Assets.getSprite("maps/pueblo_principal/ciudad_principal_muralla_delante.png"),
      Assets.getSprite("maps/pueblo_principal/ciudad_principal_carteles.png")
    },
    {
      Assets.getSprite("maps/bosque_este/bosque_este_base.png"),
      Assets.getSprite("maps/bosque_este/bosque_este_vegetacion.png"),
      Assets.getSprite("maps/bosque_este/bosque_este_arboles.png")
    }
  };

  private final int[] baseLayers = new int[]{
    3,
    2
  };

  private final HashMap<Integer, ArrayList<Enemigo>> enemigos = new HashMap();

  private void initEnemiesPositions() {
    ArrayList<Enemigo> aux;
    
    aux = new ArrayList<>();
    enemigos.put(PUEBLO_INICIAL, aux);
    aux = new ArrayList<>();
    aux.add(new Enemigo(3626, 2725, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3301, 2730, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(2906, 2470, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(2951, 2000, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3241, 1830, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3371, 1750, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3676, 1925, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(1086, 660, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(891, 700, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(986, 545, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(1956, 3275, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(2246, 2994, Enemigo.Tipo.SLIME));
    enemigos.put(BOSQUE_ESTE, aux);
  }

  private void initWarpPositions() {
    HashMap<Integer, Vector2> aux;

    //PUEBLO INICIAL
    aux = new HashMap<>();
    aux.put(BOSQUE_ESTE, new Vector2(71, 1705));
    aux.put(BOSQUE_NORTE, new Vector2(0, 0));
    aux.put(BOSQUE_SUR, new Vector2(0, 0));
    aux.put(PLAYA, new Vector2(0, 0));
    initPositionPj.put(PUEBLO_INICIAL, aux);

    aux = new HashMap<>();
    aux.put(BOSQUE_ESTE, new Vector2(970, 1785));
    aux.put(BOSQUE_NORTE, new Vector2(0, 0));
    aux.put(BOSQUE_SUR, new Vector2(0, 0));
    aux.put(PLAYA, new Vector2(0, 0));
    initPositionCamera.put(PUEBLO_INICIAL, aux);

    //BOSQUE ESTE
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(2440, 1340));
    initPositionPj.put(BOSQUE_ESTE, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(1676, 1420));
    initPositionCamera.put(BOSQUE_ESTE, aux);

    //BOSQUE NORTE
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(0, 0));
    initPositionPj.put(BOSQUE_NORTE, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(0, 0));
    initPositionCamera.put(BOSQUE_NORTE, aux);

    //BOSQUE SUR
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(0, 0));
    initPositionPj.put(BOSQUE_SUR, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(0, 0));
    initPositionCamera.put(BOSQUE_SUR, aux);

    //PLAYA
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(0, 0));
    initPositionPj.put(PLAYA, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(0, 0));
    initPositionCamera.put(PLAYA, aux);
  }

  private final HashMap<Integer, HashMap<Integer, Vector2>> initPositionPj = new HashMap<>();
  private final HashMap<Integer, HashMap<Integer, Vector2>> initPositionCamera = new HashMap<>();

  public void setCurrentMapa(int m) {
    currentMapa = m;
  }

  public int getCurrentMapa() {
    return currentMapa;
  }

  public Set<Rectangle> getWarpZones() {
    return warpZones.get(currentMapa).keySet();
  }

  public int getWarpMap(Rectangle warp) {
    return warpZones.get(currentMapa).get(warp);
  }

  public ArrayList<Enemigo> getEnemigos() {
    return enemigos.get(currentMapa);
  }

  public Vector2 getPjWarpPos(int from, int to) {
    return initPositionPj.get(from).get(to);
  }

  public Vector2 getCameraWarpPos(int from, int to) {
    return initPositionCamera.get(from).get(to);
  }

  public Rectangle[] getColliders() {
    return colliders[currentMapa];
  }

  public int getBaseLayers() {
    return baseLayers[currentMapa];
  }

  public Sprite[] getLayers() {
    return layers[currentMapa];
  }

}
