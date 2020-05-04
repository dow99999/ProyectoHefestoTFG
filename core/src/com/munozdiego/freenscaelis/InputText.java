/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author diego
 */
public class InputText extends InputAdapter {

  private String text = "";
  private boolean capsEnabled;

  public InputText() {
    super();
    capsEnabled = false;
  }
  
  public String getText() { return text; }
  public void resetText() { text = ""; }
  
  @Override
  public boolean keyDown(int keyCode) {
    if (MyGame.DEBUG_MODE) {
      System.out.println("Key code -> " + Input.Keys.toString(keyCode));
    }

    if (Input.Keys.SHIFT_LEFT == keyCode || Input.Keys.SHIFT_RIGHT == keyCode) {
      capsEnabled = true;
    }
    if (Input.Keys.DEL == keyCode) {
      if (text.length() > 0) {
        text = text.substring(0, text.length() - 1);
      }
    } else {
      if (capsEnabled) {
        switch (keyCode) {
          case Input.Keys.A:
            text += "A";
            break;
          case Input.Keys.B:
            text += "B";
            break;
          case Input.Keys.C:
            text += "C";
            break;
          case Input.Keys.D:
            text += "D";
            break;
          case Input.Keys.E:
            text += "E";
            break;
          case Input.Keys.F:
            text += "F";
            break;
          case Input.Keys.G:
            text += "G";
            break;
          case Input.Keys.H:
            text += "H";
            break;
          case Input.Keys.I:
            text += "I";
            break;
          case Input.Keys.J:
            text += "J";
            break;
          case Input.Keys.K:
            text += "K";
            break;
          case Input.Keys.L:
            text += "L";
            break;
          case Input.Keys.M:
            text += "M";
            break;
          case Input.Keys.N:
            text += "N";
            break;
          case Input.Keys.O:
            text += "O";
            break;
          case Input.Keys.P:
            text += "P";
            break;
          case Input.Keys.Q:
            text += "Q";
            break;
          case Input.Keys.R:
            text += "R";
            break;
          case Input.Keys.S:
            text += "S";
            break;
          case Input.Keys.T:
            text += "T";
            break;
          case Input.Keys.U:
            text += "U";
            break;
          case Input.Keys.V:
            text += "V";
            break;
          case Input.Keys.W:
            text += "W";
            break;
          case Input.Keys.X:
            text += "X";
            break;
          case Input.Keys.Y:
            text += "Y";
            break;
          case Input.Keys.Z:
            text += "Z";
            break;
        }
      } else {
        switch (keyCode) {
          case Input.Keys.A:
            text += "a";
            break;
          case Input.Keys.B:
            text += "b";
            break;
          case Input.Keys.C:
            text += "c";
            break;
          case Input.Keys.D:
            text += "d";
            break;
          case Input.Keys.E:
            text += "e";
            break;
          case Input.Keys.F:
            text += "f";
            break;
          case Input.Keys.G:
            text += "g";
            break;
          case Input.Keys.H:
            text += "h";
            break;
          case Input.Keys.I:
            text += "i";
            break;
          case Input.Keys.J:
            text += "j";
            break;
          case Input.Keys.K:
            text += "k";
            break;
          case Input.Keys.L:
            text += "l";
            break;
          case Input.Keys.M:
            text += "m";
            break;
          case Input.Keys.N:
            text += "n";
            break;
          case Input.Keys.O:
            text += "o";
            break;
          case Input.Keys.P:
            text += "p";
            break;
          case Input.Keys.Q:
            text += "q";
            break;
          case Input.Keys.R:
            text += "r";
            break;
          case Input.Keys.S:
            text += "s";
            break;
          case Input.Keys.T:
            text += "t";
            break;
          case Input.Keys.U:
            text += "u";
            break;
          case Input.Keys.V:
            text += "v";
            break;
          case Input.Keys.W:
            text += "w";
            break;
          case Input.Keys.X:
            text += "x";
            break;
          case Input.Keys.Y:
            text += "y";
            break;
          case Input.Keys.Z:
            text += "z";
            break;
        }
      }
      
      switch(keyCode){
        case Input.Keys.SPACE:
          text += " ";
          break;
        case Input.Keys.COMMA:
          text += ",";
          break;
        case Input.Keys.PERIOD:
          text += ".";
          break;
      }
      
    }
    
    return super.keyDown(keyCode);
  }

  @Override
  public boolean keyUp(int keyCode) {
    if (Input.Keys.SHIFT_LEFT == keyCode || Input.Keys.SHIFT_RIGHT == keyCode) {
      capsEnabled = false;
    }
    return super.keyUp(keyCode);
  }
}
