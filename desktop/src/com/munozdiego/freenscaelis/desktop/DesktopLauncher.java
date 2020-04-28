package com.munozdiego.freenscaelis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.munozdiego.freenscaelis.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Freen's Caelis";
                config.width = MyGame.WIDTH;
                config.height = MyGame.HEIGHT;
//                config.width = 1280;
//                config.height = 720;
                config.useGL30 = true;
                config.fullscreen = true;
                
		new LwjglApplication(new MyGame(), config);
	}
}
