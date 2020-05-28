/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.MyGame;
import java.util.Set;

/**
 *
 * @author diego
 */
public class ColliderUtils {

  /**
   * Method that checks if a rectangle is over an array of rectangles
   * @param colliders Rectangle[] colliders of the map
   * @param check Rectangle collider to check
   * @return Rectangle if there is a colition | null if there is no collition
   */
  public static Rectangle checkCollitions(Rectangle[] colliders, Rectangle check) {
    boolean col = false;
    Rectangle aux = null;
    int i = 0;

    //if (MyGame.DEBUG_MODE) {
    //  System.out.println("--------checkCollitions----------");
    //  System.out.println(check.getX() + " " + check.getY() + " " + check.getWidth() + " " + check.getHeight());
    //}
    while (i < colliders.length && !col) {
      if (check.overlaps(colliders[i])) {
        col = true;
        aux = colliders[i];
      }
      //if (MyGame.DEBUG_MODE) {
      //  System.out.println(colliders[i].getX() + " " + colliders[i].getY() + " " + colliders[i].getWidth() + " " + colliders[i].getHeight());
      //}
      i++;
    }
    //if (MyGame.DEBUG_MODE) {
    //  System.out.println("----------------------");
    //}

    return aux;
  }

  /**
   * Method that checks if a rectangle is over a set of rectangles
   * @param colliders set<Rectangle> colliders of the map
   * @param check Rectangle collider to check
   * @return Rectangle if there is a colition | null if there is no collition
   */
  public static Rectangle checkCollitions(Set<Rectangle> colliders, Rectangle check) {
    Rectangle aux = null;

    //if (MyGame.DEBUG_MODE) {
    //  System.out.println("--------checkCollitions----------");
    //  System.out.println(check.getX() + " " + check.getY() + " " + check.getWidth() + " " + check.getHeight());
    //}

    for (Rectangle iter : colliders) {
      if (check.overlaps(iter)) {
        aux = iter;
        break;
      }
      //if (MyGame.DEBUG_MODE) {
      //  System.out.println(iter.getX() + " " + iter.getY() + " " + iter.getWidth() + " " + iter.getHeight());
      //}
    }
    
    //if (MyGame.DEBUG_MODE) {
    //  System.out.println("----------------------");
    //}

    return aux;
  }

  /**
   * Method to control the camera view over the map
   * 
   * @param camx flaot cam x
   * @param camy float cam y
   * @param camw float cam width
   * @param camh float cam height
   * @param mapw float map width
   * @param maph float map height
   * @return int code of the camera collition
   */
  public static int cameraOutside(float camx, float camy, float camw, float camh, float mapw, float maph) {
    int outside = 0;
    //System.out.println(camx + " " + camy + " " + camw + " " + camh + " " + mapw + " " + maph);

    if (camx < 0) {
      outside = 1;
    } else {
      if (camx + camw > mapw) {
        outside = 1;
      }
    }

    if (camy < 0) {
      if (outside == 1) {
        outside = 3;
      } else {
        outside = 2;
      }
    } else {
      if (camy + camh > maph) {
        if (outside == 1) {
          outside = 3;
        } else {
          outside = 2;
        }
      }
    }

    return outside;
  }
}
