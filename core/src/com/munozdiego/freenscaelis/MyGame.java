package com.munozdiego.freenscaelis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

public class MyGame extends Game {
    public static final boolean DEBUG_MODE = true;
    
    public GameScreen gs;
	
    @Override
    public void create () {
        Assets.load();
        
        gs = new GameScreen(this);
        
        setScreen(gs);
    }
}
