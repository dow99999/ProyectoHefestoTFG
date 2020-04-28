package com.munozdiego.freenscaelis;

import com.munozdiego.freenscaelis.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;

public class MyGame extends Game {
    public static final boolean DEBUG_MODE = true;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    
    public MenuScreen menuScreen;
	
    @Override
    public void create () {
        Assets.init();
        Assets.addFont("pixel32b", 32, Color.BLACK, "fonts/pixelart.otf");
        Assets.addFont("pixel32w", 32, Color.WHITE, "fonts/pixelart.otf");
        
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }
    
}
