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
          PLAYA = 4,
          LABERINTO = 5,
          MADERA = 6,
          CUEVA = 7,
          FINAL = 8;

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
      new Rectangle(0, 0, 629, 1791),
      new Rectangle(0, 1918, 628, 232),
      new Rectangle(0, 2161, 2696, 638),
      new Rectangle(756, 1144, 691, 889),
      new Rectangle(1287, 469, 489, 1037),
      new Rectangle(1780, 1049, 918, 450),
      new Rectangle(1622, 1624, 1076, 541),
      new Rectangle(626, 0, 4172, 371),
      new Rectangle(1912, 349, 2886, 570),
      new Rectangle(2831, 928, 1966, 570),
      new Rectangle(2437, 2803, 256, 186),
      new Rectangle(2436, 3109, 2361, 409),
      new Rectangle(0, 3538, 4795, 300),
      new Rectangle(0, 2784, 1825, 767),
      new Rectangle(4019, 1620, 776, 1501),
      new Rectangle(3208, 2105, 304, 305),
      new Rectangle(3204, 2422, 165, 234)
    },
    {
      new Rectangle(0, 0, 384, 3837),
      new Rectangle(88, 3064, 814, 775),
      new Rectangle(518, 0, 787, 1070),
      new Rectangle(516, 1191, 784, 942),
      new Rectangle(1300, 1481, 668, 646),
      new Rectangle(1309, 0, 657, 495),
      new Rectangle(1189, 2248, 1114, 1589),
      new Rectangle(2437, 2248, 734, 1590),
      new Rectangle(3174, 3066, 1249, 772),
      new Rectangle(4411, 0, 384, 3833),
      new Rectangle(1960, 0, 1214, 2127),
      new Rectangle(3175, 0, 1239, 1112)
    },
    {
      new Rectangle(0, 0, 2359, 1118),
      new Rectangle(0, 1110, 533, 1716),
      new Rectangle(0, 2826, 2409, 1008),
      new Rectangle(1382, 1239, 972, 1463),
      new Rectangle(2356, 1485, 722, 1004),
      new Rectangle(2534, 2501, 2264, 1333),
      new Rectangle(2495, 0, 2303, 389),
      new Rectangle(4363, 1359, 435, 1136),
      new Rectangle(2496, 400, 729, 549),
      new Rectangle(2484, 955, 1121, 403),
      new Rectangle(3735, 951, 1059, 392),
      new Rectangle(4124, 402, 670, 541),
      new Rectangle(2355, 2488, 177, 214)
    },
    {
      new Rectangle(0, 0, 750, 5594),
      new Rectangle(0, 5598, 402, 70),
      new Rectangle(0, 5684, 755, 1510),
      new Rectangle(0, 7194, 3355, 50),
      new Rectangle(3355, 0, 50, 7194),
      new Rectangle(0, -50, 3355, 50),
      new Rectangle(749, 1070, 315, 1986),
      new Rectangle(753, 6868, 344, 326),
      new Rectangle(749, 4883, 220, 362)
    },
    {},
    {},
    {
      new Rectangle(0, 230, 382, 1352),
      new Rectangle(0, 1583, 1151, 569),
      new Rectangle(0, 2161, 622, 1669),
      new Rectangle(623, 3119, 1107, 715),
      new Rectangle(1730, 2063, 668, 1771),
      new Rectangle(1296, 2063, 431, 87),
      new Rectangle(1150, 1583, 817, 374),
      new Rectangle(2112, 1583, 1632, 376),
      new Rectangle(2543, 2112, 384, 711),
      new Rectangle(2925, 2112, 675, 336),
      new Rectangle(3601, 2112, 142, 671),
      new Rectangle(2543, 3023, 381, 811),
      new Rectangle(2925, 3408, 675, 426),
      new Rectangle(3740, 2784, 772, 1050),
      new Rectangle(4513, 1624, 284, 2211),
      new Rectangle(3648, 240, 1149, 1381),
      new Rectangle(0, 0, 4797, 236),
      new Rectangle(1007, 580, 238, 178),
      new Rectangle(1680, 1105, 238, 178),
      new Rectangle(2400, 578, 238, 178),
      new Rectangle(2976, 963, 238, 178)
    },
    {}
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
    aux.put(new Rectangle(4702, 1502, 93, 130), LABERINTO);
    warpZones.put(BOSQUE_ESTE, aux);

    aux = new HashMap<>();
    aux.put(new Rectangle(2303, 3782, 145, 58), PUEBLO_INICIAL);
    aux.put(new Rectangle(392, 18, 124, 80), CUEVA);
    warpZones.put(BOSQUE_NORTE, aux);

    aux = new HashMap<>();
    aux.put(new Rectangle(2350, 0, 144, 60), PUEBLO_INICIAL);
    aux.put(new Rectangle(2399, 3784, 144, 50), MADERA);
    warpZones.put(BOSQUE_SUR, aux);

    aux = new HashMap<>();
    aux.put(new Rectangle(3297, 3602, 61, 141), PUEBLO_INICIAL);
    warpZones.put(PLAYA, aux);

    aux = new HashMap<>();
    aux.put(new Rectangle(2399, 3752, 144, 82), BOSQUE_NORTE);
    warpZones.put(CUEVA, aux);

    aux = new HashMap<>();
    warpZones.put(MADERA, aux);

    aux = new HashMap<>();
    warpZones.put(LABERINTO, aux);

    aux = new HashMap<>();
    warpZones.put(FINAL, aux);

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
    },
    {
      Assets.getSprite("maps/bosque_norte/bosque_norte_base.png"),
      Assets.getSprite("maps/bosque_norte/bosque_norte_vegetacion.png"),
      Assets.getSprite("maps/bosque_norte/bosque_norte_arboles.png")
    },
    {
      Assets.getSprite("maps/bosque_sur/bosque_sur_base.png"),
      Assets.getSprite("maps/bosque_sur/bosque_sur_vegetacion.png")
    },
    {
      Assets.getSprite("maps/playa/playa_base.png"),
      Assets.getSprite("maps/playa/playa_muelle.png"),
      Assets.getSprite("maps/playa/playa_arboles.png"),
      Assets.getSprite("maps/playa/playa_copas.png")
    },
    {},
    {},
    {
      Assets.getSprite("maps/cueva/cueva_base.png"),
      Assets.getSprite("maps/cueva/cueva_back.png"),
      Assets.getSprite("maps/cueva/cueva_front.png")
    },
    {}
  };

  private final int[] baseLayers = new int[]{
    3,
    2,
    2,
    2,
    3,
    0,
    0,
    2,
    0,};

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

    aux = new ArrayList<>();
    aux.add(new Enemigo(3379, 1279, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(4137, 1370, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(4041, 1651, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(3759, 1929, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(4156, 2107, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3834, 2275, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3395, 2476, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3544, 2722, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(3584, 2752, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(4025, 2736, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(601, 2338, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(883, 2478, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(641, 2690, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(1497, 727, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(1631, 880, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(1514, 1158, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(1750, 1252, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(1651, 1004, Enemigo.Tipo.SLIME));
    enemigos.put(BOSQUE_NORTE, aux);

    aux = new ArrayList<>();
    aux.add(new Enemigo(666, 1252, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(811, 1334, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(1150, 1633, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(783, 1915, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(1212, 2420, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(785, 2597, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(747, 2193, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(4118, 2108, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3305, 2318, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3381, 2031, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3855, 1722, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3383, 1586, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(4118, 1520, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3384, 501, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3676, 576, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3929, 828, Enemigo.Tipo.SLIME));
    aux.add(new Enemigo(3505, 737, Enemigo.Tipo.SLIME));
    enemigos.put(BOSQUE_SUR, aux);

    aux = new ArrayList<>();
    enemigos.put(PLAYA, aux);

    aux = new ArrayList<>();
    aux.add(new Enemigo(1742, 631, Enemigo.Tipo.BOSS_GOBLIN));
    aux.add(new Enemigo(875, 2370, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(1491, 2799, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(975, 2863, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(3153, 2670, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(3379, 3167, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(4017, 1886, Enemigo.Tipo.GOBLIN));
    aux.add(new Enemigo(4217, 2350, Enemigo.Tipo.GOBLIN));
    enemigos.put(CUEVA, aux);

    aux = new ArrayList<>();
    enemigos.put(MADERA, aux);

    aux = new ArrayList<>();
    enemigos.put(LABERINTO, aux);

    aux = new ArrayList<>();
    enemigos.put(FINAL, aux);

  }

  private void initWarpPositions() {
    HashMap<Integer, Vector2> aux;

    //FROM PUEBLO INICIAL
    aux = new HashMap<>();
    aux.put(BOSQUE_ESTE, new Vector2(71, 1705));
    aux.put(BOSQUE_NORTE, new Vector2(2304, 3550));
    aux.put(BOSQUE_SUR, new Vector2(2344, 10));
    aux.put(PLAYA, new Vector2(3120, 3535));
    initPositionPj.put(PUEBLO_INICIAL, aux);

    aux = new HashMap<>();
    aux.put(BOSQUE_ESTE, new Vector2(970, 1785));
    aux.put(BOSQUE_NORTE, new Vector2(2384, 3297));
    aux.put(BOSQUE_SUR, new Vector2(2424, 540));
    aux.put(PLAYA, new Vector2(2399, 3615));
    initPositionCamera.put(PUEBLO_INICIAL, aux);

    //FROM BOSQUE ESTE
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(2440, 1340));
    aux.put(LABERINTO, new Vector2(0, 0));
    initPositionPj.put(BOSQUE_ESTE, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(1676, 1420));
    aux.put(LABERINTO, new Vector2(0, 0));
    initPositionCamera.put(BOSQUE_ESTE, aux);

    //FROM BOSQUE NORTE
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(1285, 15));
    aux.put(CUEVA, new Vector2(2390, 3540));
    initPositionPj.put(BOSQUE_NORTE, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(1365, 545));
    aux.put(CUEVA, new Vector2(2470, 3295));
    initPositionCamera.put(BOSQUE_NORTE, aux);

    //FROM BOSQUE SUR
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(1285, 2620));
    aux.put(MADERA, new Vector2(0, 0));
    initPositionPj.put(BOSQUE_SUR, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(1365, 2340));
    aux.put(MADERA, new Vector2(0, 0));
    initPositionCamera.put(BOSQUE_SUR, aux);

    //FROM PLAYA
    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(100, 1340));
    initPositionPj.put(PLAYA, aux);

    aux = new HashMap<>();
    aux.put(PUEBLO_INICIAL, new Vector2(963, 1423));
    initPositionCamera.put(PLAYA, aux);

    //FROM CUEVA
    aux = new HashMap<>();
    aux.put(BOSQUE_NORTE, new Vector2(374, 16));
    initPositionPj.put(CUEVA, aux);

    aux = new HashMap<>();
    aux.put(BOSQUE_NORTE, new Vector2(961, 541));
    initPositionCamera.put(CUEVA, aux);

    //FROM MADERA
    aux = new HashMap<>();
    initPositionPj.put(MADERA, aux);

    aux = new HashMap<>();
    initPositionCamera.put(MADERA, aux);

    //FROM LABERINTO
    aux = new HashMap<>();
    initPositionPj.put(LABERINTO, aux);

    aux = new HashMap<>();
    initPositionCamera.put(LABERINTO, aux);

    //FROM FINAL
    aux = new HashMap<>();
    initPositionPj.put(FINAL, aux);

    aux = new HashMap<>();
    initPositionCamera.put(FINAL, aux);

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
