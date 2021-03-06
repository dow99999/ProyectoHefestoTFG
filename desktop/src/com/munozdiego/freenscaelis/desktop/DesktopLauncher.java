package com.munozdiego.freenscaelis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.munozdiego.freenscaelis.MyGame;

public class DesktopLauncher {
  
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = "Freen's Caelis";
    config.width = 1920;
    config.height = 1080;
    config.useGL30 = true;
    config.fullscreen = true;

    new LwjglApplication(new MyGame(), config);
  }
}