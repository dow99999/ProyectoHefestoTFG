/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.utils.InputText;
import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.utils.SocketDataManager;
import com.munozdiego.freenscaelis.utils.UserData;

/**
 *
 * @author diego
 */
public class ConnectScreen implements Screen {

  SpriteBatch batch;
  MyGame m_game;
  OrthographicCamera camera;

  //types of font used in the screen
  BitmapFont fontw;
  BitmapFont fonty;

  Sprite sprite_back;

  InputText textListener;

  String ip = "";
  boolean clicked;
  float delayClick;
  
  //used to compute the dimensions of the text we are showing
  GlyphLayout layout;

  String[] texts = new String[]{
    "IP. ",
    "Type the host's IP",
    "Connect"
  };

  //Boxes where the user will click
  Rectangle connect;

  //used to control in which field the user is writing
  boolean focus;

  public ConnectScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fonty = Assets.getFont("pixel32y");

    layout = new GlyphLayout();

    sprite_back = Assets.getSprite("images/bg-blur.png");

    connect = new Rectangle();

    //we set the boxes were the user will be able to click to change the input focus
    layout.setText(fontw, texts[2]);
    connect.set(MyGame.WIDTH / 2 - layout.width / 2, 700, layout.width, layout.height * -1);

    //we add mouse input control to our textListener
    textListener = new InputText() {
      @Override
      public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (connect.contains(screenX, screenY)) {
          texts[2] = "Waiting for the other player...";
          clicked = true;
        }
        return super.touchDown(screenX, screenY, pointer, button);
      }
    };

    //the limit of characters we can enter in one field
    textListener.setLimit(15);
  }

  public void conect(boolean pvp) {
    if (SocketDataManager.lastInstance != null) {
      if (SocketDataManager.lastInstance.isAlive()) {
        SocketDataManager.lastInstance.interrupt();
      }
    }
    
    new SocketDataManager(UserData.getInstance().getCurrentCharacter(), ip).start();
    Assets.getMusic(MyGame.CodeScreen.MAIN_MENU.name()).stop();

    if (!pvp) {
      m_game.showScreen(MyGame.CodeScreen.LEVELS);
    } else {
      m_game.showScreen(MyGame.CodeScreen.PVP);
    }
  }

  @Override
  public void show() {
    texts[2] = "Connect";
    clicked = false;
    delayClick = 0.3f;
    //we set our listener to libGDX only when this screen is being shown
    Gdx.input.setInputProcessor(textListener);
  }

  @Override
  public void render(float delta) {
    camera.update();
    batch.setProjectionMatrix(camera.combined);

    //go back to the main menu if ESC is pressed
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.SELECT_CHAR);
    }

    ip = textListener.getText();

    //start of the drawing process
    batch.begin();

    batch.draw(sprite_back, 0, 0);

    fonty.draw(batch, texts[0], 300, 300);

    if (ip.equals("")) {
      fonty.draw(batch, texts[1], 360, 300);
    } else {
      fonty.draw(batch, ip, 360, 300);
    }

    layout.setText(fontw, texts[2]);
    fontw.draw(batch, texts[2], MyGame.WIDTH / 2 - layout.width / 2, 700);

    batch.end();
    
    if(clicked){
      delayClick-= Gdx.graphics.getDeltaTime();
      if(delayClick <= 0){
        delayClick = 0.3f;
        clicked = false;
        conect(((SelectPlayerScreen) m_game.screens.get(MyGame.CodeScreen.SELECT_CHAR)).pvp);
      }
    }
    //end of the drawing process
  }

  @Override
  public void resize(int arg0, int arg1) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {
    //reset the listener when this screen is no longer visible
    textListener.resetText();
  }

  @Override
  public void dispose() {
    textListener.resetText();
    fonty.dispose();
    fontw.dispose();
    batch.dispose();
  }

}
