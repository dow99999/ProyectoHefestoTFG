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
import com.munozdiego.freenscaelis.utils.DatabaseDataManager;
import com.munozdiego.freenscaelis.utils.InputText;
import com.munozdiego.freenscaelis.MyGame;

/**
 *
 * @author diego
 */
public class LoginScreen implements Screen {

  SpriteBatch batch;
  MyGame m_game;
  OrthographicCamera camera;
  
  //types of font used in the screen
  BitmapFont fontw;
  BitmapFont fontb;

  Sprite sprite_back;

  InputText textListener;

  String user = "";
  String pass = "";
  String passc = "";
  
  //used to compute the dimensions of the text we are showing
  GlyphLayout layout;

  String[] texts = new String[]{
    "Username. ",
    "Password. ",
    "Login",
    "Type your username",
    "Type your password"
  };

  //Boxes where the user will click
  Rectangle ruser;
  Rectangle rpass;
  Rectangle rlogin;

  //used to control in which field the user is writing
  boolean focus;

  public LoginScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fontb = Assets.getFont("pixel32b");

    layout = new GlyphLayout();

    sprite_back = Assets.getSprite("images/bg-blur.png");

    ruser = new Rectangle();
    rpass = new Rectangle();
    rlogin = new Rectangle();
    
    //we set the boxes were the user will be able to click to change the input focus
    layout.setText(fontw, texts[3]);
    float widthAux = layout.width;
    layout.setText(fontw, texts[0]);
    ruser.set(200, 300, layout.width + widthAux, layout.height * -1);
    layout.setText(fontw, texts[4]);
    widthAux = layout.width;
    layout.setText(fontw, texts[1]);
    rpass.set(200, 400, layout.width + widthAux, layout.height * -1);
    layout.setText(fontw, texts[2]);
    rlogin.set(MyGame.WIDTH / 2 - layout.width / 2, 700, layout.width, layout.height * -1);

    focus = true;

    //we add mouse input control to our textListener
    textListener = new InputText() {
      @Override
      public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (ruser.contains(screenX, screenY)) {
          focus = true;
          textListener.resetText(); //reset the text when clicking on the field
        } else {
          if (rpass.contains(screenX, screenY)) {
            focus = false;
            textListener.resetText();
          } else {
            if (rlogin.contains(screenX, screenY)) {
              if(DatabaseDataManager.getInstance().checkUserLogin(user, pass))
                m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
            }
          }
        }
        return super.touchDown(screenX, screenY, pointer, button);
      }
    };

    //the limit of characters we can enter in one field
    textListener.setLimit(10);
  }

  @Override
  public void show() {
    //we set our listener to libGDX only when this screen is being shown
    Gdx.input.setInputProcessor(textListener);
  }

  @Override
  public void render(float delta) {
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    
    //go back to the main menu if ESC is pressed
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
    }

    //control field input
    if (focus) {
      user = textListener.getText();
    } else {
      pass = textListener.getText();
      passc = InputText.Utils.hideText(pass);
    }

    //start of the drawing process
    batch.begin();

    batch.draw(sprite_back, 0, 0);

    fontw.draw(batch, texts[0], 200, 300);
    layout.setText(fontw, texts[0]);
    fontw.draw(batch, user.equals("") ? texts[3] : user, 200 + layout.width, 300);

    fontw.draw(batch, texts[1], 200, 400);
    layout.setText(fontw, texts[1]);
    fontw.draw(batch, pass.equals("") ? texts[4] : passc, 200 + layout.width, 400);

    layout.setText(fontw, texts[2]);
    fontw.draw(batch, texts[2], MyGame.WIDTH / 2 - layout.width / 2, 700);

    batch.end();
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
    fontb.dispose();
    fontw.dispose();
    batch.dispose();
  }

}
