/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.screens.maps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.utils.Assets;

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
    {}
  };

  private final Rectangle[][] warpZones = new Rectangle[][]{
    {
      new Rectangle(1296, 0, 145, 61), //bosque norte
      new Rectangle(2578, 1391, 60, 144), //bosque este
      new Rectangle(1295, 2821, 145, 57), //bosque sur
      new Rectangle(0, 1391, 95, 145) //playa
    },
    {}
  };

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
  
  
  public void setCurrentMapa(int m) {
    currentMapa = m;
  }

  public Rectangle[] getWarpZones() {
    return warpZones[currentMapa];
  }

  public Rectangle[] getColliders() {
    return colliders[currentMapa];
  }

  public int getBaseLayers() {
    return baseLayers[currentMapa];
  }
  
  public Sprite[] getLayers(){
    return layers[currentMapa];
  }

}
