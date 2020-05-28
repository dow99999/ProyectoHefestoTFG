package com.munozdiego.freenscaelis;

import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.screens.ConnectScreen;
import com.munozdiego.freenscaelis.screens.CreatePlayerScreen;
import com.munozdiego.freenscaelis.screens.LoginScreen;
import com.munozdiego.freenscaelis.screens.PauseScreen;
import com.munozdiego.freenscaelis.screens.maps.LevelScreen;
import com.munozdiego.freenscaelis.screens.RegisterScreen;
import com.munozdiego.freenscaelis.screens.SelectPlayerScreen;
import com.munozdiego.freenscaelis.screens.maps.ArenaScreen;
import com.munozdiego.freenscaelis.screens.maps.ScreenData;
import com.munozdiego.freenscaelis.utils.LocalDataManager;
import com.munozdiego.freenscaelis.utils.UserData;
import java.util.HashMap;
import java.util.Map;

public class MyGame extends Game {

  public static final boolean DEBUG_MODE = true;
  public static final int WIDTH = 1920;
  public static final int HEIGHT = 1080;

  public enum CodeScreen {
    MAIN_MENU,
    LOGIN,
    REGISTER,
    SELECT_CHAR,
    CREATE_CHAR,
    LEVELS,
    PAUSE,
    CONNECT,
    PVP
  }

  public Map<CodeScreen, Screen> screens;

  public void showScreen(CodeScreen s) {
    setScreen(screens.get(s));
  }

  @Override
  public void create() {
    screens = new HashMap<>();
    //load of assets
    Assets.addFont("pixel32r", 32, Color.RED, "fonts/pixelart.otf");
    Assets.addFont("pixel32y", 32, Color.YELLOW, "fonts/pixelart.otf");
    Assets.addFont("pixel32b", 32, Color.BLACK, "fonts/pixelart.otf");
    Assets.addFont("pixel32w", 32, Color.WHITE, "fonts/pixelart.otf");

    Assets.addMusic(MyGame.CodeScreen.MAIN_MENU.name(), "audio/music/menu_st.mp3");
    Assets.addMusic(String.valueOf(ScreenData.PUEBLO_INICIAL), "audio/music/town_st.wav");
    Assets.addMusic(String.valueOf(ScreenData.BOSQUE_ESTE), "audio/music/forest_st.wav");
    Assets.addMusic(String.valueOf(ScreenData.BOSQUE_NORTE), "audio/music/forest_st.wav");
    Assets.addMusic(String.valueOf(ScreenData.BOSQUE_SUR), "audio/music/forest_st.wav");
    Assets.addMusic(String.valueOf(ScreenData.PLAYA), "audio/music/beach_st.wav");
    Assets.addMusic(String.valueOf(ScreenData.CUEVA), "audio/music/final_st.mp3");
    Assets.addMusic(String.valueOf(ScreenData.FINAL), "audio/music/final_st.mp3");
    Assets.addMusic(String.valueOf(ScreenData.LABERINTO), "audio/music/town_st.wav");
    Assets.addMusic(String.valueOf(ScreenData.MADERA), "audio/music/final_st.mp3");
    Assets.addMusic(String.valueOf(ScreenData.ARENA), "audio/music/arena_st.mp3");
    
    Assets.addSound("click", "audio/sounds/click.wav");
    Assets.addSound("type", "audio/sounds/type.wav");
    Assets.addSound("attack", "audio/sounds/att.wav");
    Assets.addSound("hit_pj", "audio/sounds/hit_pj.wav");
    Assets.addSound("hit_gb", "audio/sounds/hit_gb.wav");
    Assets.addSound("hit_sk", "audio/sounds/hit_sk.wav");
    Assets.addSound("hit_sl", "audio/sounds/hit_slime.wav");
    Assets.addSound("enemy_death", "audio/sounds/mob_death.wav");
    
    Assets.init();

    //init list of local user's characters
    UserData.getInstance().setCharacters(LocalDataManager.getInstance().retrievePlayerData());

    //init character's animations
    for (int i = UserData.getInstance().getCharacters().length - 1; i >= 0; --i) {
      Personaje aux = UserData.getInstance().getCharacters()[i];
      if (aux != null) {
        aux.init(aux.getClase(), 0);
      }
    }

    //set of the aplication's screens
    screens.put(CodeScreen.MAIN_MENU, new MenuScreen(this));
    screens.put(CodeScreen.LOGIN, new LoginScreen(this));
    screens.put(CodeScreen.REGISTER, new RegisterScreen(this));
    screens.put(CodeScreen.SELECT_CHAR, new SelectPlayerScreen(this));
    screens.put(CodeScreen.CREATE_CHAR, new CreatePlayerScreen(this));
    screens.put(CodeScreen.LEVELS, new LevelScreen(this));
    screens.put(CodeScreen.PAUSE, new PauseScreen(this));
    screens.put(CodeScreen.CONNECT, new ConnectScreen(this));
    screens.put(CodeScreen.PVP, new ArenaScreen(this));
    showScreen(CodeScreen.MAIN_MENU);

  }
}
