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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.munozdiego.freenscaelis.Assets;
import com.munozdiego.freenscaelis.InputText;
import com.munozdiego.freenscaelis.MyGame;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class LoginScreen implements Screen {

  SpriteBatch batch;
  MyGame m_game;
  OrthographicCamera camera;

  BitmapFont fontw;
  BitmapFont fontb;

  Sprite sprite_back;
  
  InputText textListener;

  public LoginScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fontb = Assets.getFont("pixel32b");

    sprite_back = Assets.getSprite("images/bg-blur.png");

    textListener = new InputText();
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(textListener);
  }

  @Override
  public void render(float delta) {
    camera.update();
    batch.setProjectionMatrix(camera.combined);

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.setScreen(m_game.screens.get("menu-principal"));
    }

    batch.begin();

    batch.draw(sprite_back, 0, 0);
    
    fontw.draw(batch, textListener.getText(), 50, 50);

    batch.end();
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
