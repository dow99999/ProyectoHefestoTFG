package com.munozdiego.freenscaelis;

import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.screens.CreatePlayerScreen;
import com.munozdiego.freenscaelis.screens.LoginScreen;
import com.munozdiego.freenscaelis.screens.maps.LevelScreen;
import com.munozdiego.freenscaelis.screens.RegisterScreen;
import com.munozdiego.freenscaelis.screens.SelectPlayerScreen;
import com.munozdiego.freenscaelis.utils.LocalDataManager;
import com.munozdiego.freenscaelis.utils.UserData;
import java.util.HashMap;
import java.util.Map;

public class MyGame extends Game {

  public static final boolean DEBUG_MODE = true;
  public static final int WIDTH = 1920;
  public static final int HEIGHT = 1080;

  public enum CodeScreen{
    MAIN_MENU,
    LOGIN,
    REGISTER,
    SELECT_CHAR,
    CREATE_CHAR,
    PUEBLO_INICIAL
  }
  
  private Map<CodeScreen, Screen> screens;

  public void showScreen(CodeScreen s){
    setScreen(screens.get(s));
  }
  
  @Override
  public void create() {
    screens = new HashMap<>();
    Assets.init();
    Assets.addFont("pixel32r", 32, Color.RED, "fonts/pixelart.otf");
    Assets.addFont("pixel32y", 32, Color.YELLOW, "fonts/pixelart.otf");
    Assets.addFont("pixel32b", 32, Color.BLACK, "fonts/pixelart.otf");
    Assets.addFont("pixel32w", 32, Color.WHITE, "fonts/pixelart.otf");

    //init list of local user's characters
    UserData.getInstance().setCharacters(LocalDataManager.getInstance().retrievePlayerData());
    
    //init character's animations
    for(int i = UserData.getInstance().getCharacters().length - 1; i >= 0 ; --i){
      Personaje aux = UserData.getInstance().getCharacters()[i];
      if(aux != null)
        aux.init(aux.getClase(), 0);
    }
    
    screens.put(CodeScreen.MAIN_MENU, new MenuScreen(this));
    screens.put(CodeScreen.LOGIN, new LoginScreen(this));
    screens.put(CodeScreen.REGISTER, new RegisterScreen(this));
    screens.put(CodeScreen.SELECT_CHAR, new SelectPlayerScreen(this));
    screens.put(CodeScreen.CREATE_CHAR, new CreatePlayerScreen(this));
    screens.put(CodeScreen.PUEBLO_INICIAL, new LevelScreen(this));
    
    showScreen(CodeScreen.MAIN_MENU);
  }

}
