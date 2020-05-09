package com.munozdiego.freenscaelis;

import com.munozdiego.freenscaelis.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.munozdiego.freenscaelis.screens.LoginScreen;
import com.munozdiego.freenscaelis.screens.RegisterScreen;
import java.util.HashMap;
import java.util.Map;

public class MyGame extends Game {

  public static final boolean DEBUG_MODE = true;
  public static final int WIDTH = 1920;
  public static final int HEIGHT = 1080;

  public SpriteBatch batch;
  public Map<String, Screen> screens;

  @Override
  public void create() {
    screens = new HashMap<>();
    batch = new SpriteBatch();
    Assets.init();
    Assets.addFont("pixel32b", 32, Color.BLACK, "fonts/pixelart.otf");
    Assets.addFont("pixel32w", 32, Color.WHITE, "fonts/pixelart.otf");

    screens.put("menu-principal", new MenuScreen(this));
    screens.put("login", new LoginScreen(this));
    screens.put("register", new RegisterScreen(this));
    
    setScreen(screens.get("menu-principal"));
  }

}
