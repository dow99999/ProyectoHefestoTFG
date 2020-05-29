/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.MyGame;

/**
 *
 * @author diego
 */
public class CreditsScreen implements Screen {

  SpriteBatch batch;
  MyGame m_game;
  OrthographicCamera camera;
  
  //types of font used in the screen
  BitmapFont fontw;
  BitmapFont fonty;

  Sprite sprite_back;

  String[] texts = new String[]{
    "Credits\n"
          + "\n"
          + "Programming and Design\n"
          + "Diego Munoz Jorquera\n"
          + "\n"
          + "Assets\n"
          + "tileset by finalbossblues\n"
          + "\n"
          + "Music\n"
          + "'Snowfall final' by ShadyDave\n"
          + "'New York Jazz Loop' by FoolBoyMedia\n"
          + "'Happy Sandbox' by Mative\n"
          + "'Jungle Shake Loop' by rhodesmas\n"
          + "'Hallow Lights' by ShadyDave\n"
          + "\n"
          + "Special Effects by Diego Munoz Jorquera\n"
          + "\n"
          + "Thanks to my project tutor Chris and all DAM teachers for making this project possible\n"
          + "Special thanks to all the people that helped testing this game\n"
          + "And thank you for playing Freen's Caelis\n"
  };


  public CreditsScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fonty = Assets.getFont("pixel32y");

    sprite_back = Assets.getSprite("images/bg-blur.png");
  }

  @Override
  public void show() {
    //we set our listener to libGDX only when this screen is being shown
    Gdx.input.setInputProcessor(new InputAdapter());
  }

  @Override
  public void render(float delta) {
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    
    //go back to the main menu if ESC is pressed
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
    }

    //start of the drawing process
    batch.begin();

    batch.draw(sprite_back, 0, 0);

    fontw.draw(batch, texts[0], 50, 100);
    
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
  }

  @Override
  public void dispose() {
    fonty.dispose();
    fontw.dispose();
    batch.dispose();
  }

}
