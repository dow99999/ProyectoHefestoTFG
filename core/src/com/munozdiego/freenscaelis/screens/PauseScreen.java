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
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.utils.DatabaseDataManager;
import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.utils.LocalDataManager;
import com.munozdiego.freenscaelis.utils.UserData;
/**
 *
 * @author diego
 */
public class PauseScreen implements Screen {

  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;

  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fontb;

  Sprite sprite_back;

  final String[] texts = new String[]{
    "Pause",
    "Save and exit"
  };
  
  //used to compute the dimensions of the text
  GlyphLayout layout;
  
  final Rectangle[] boxes = new Rectangle[texts.length];

  float stateTime;
  
  public PauseScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fontb = Assets.getFont("pixel32b");
    
    layout = new GlyphLayout();

    //setting the boxes where the user will click
    for (int i = texts.length - 1; i >= 0; --i) {
      boxes[i] = new Rectangle();
    }

    layout.setText(fontw, texts[0]);
    boxes[0].set(50, 50, layout.width, layout.height * -1);
    layout.setText(fontw, texts[1]);
    boxes[1].set(MyGame.WIDTH/2 + layout.width/2, 800, layout.width, layout.height * -1);
  }

  
  @Override
  public void show() {
    //setting of the InputProcessor we'll use in this screen
    Gdx.input.setInputProcessor(new InputAdapter() {
      @Override
      public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (MyGame.DEBUG_MODE) {
          System.out.println("Before Touch(" + screenX + "," + screenY + ")");
          System.out.println("camera(" + camera.position.x + "," + camera.position.y + ")");
        }
        
        //testing on changing the position were the user clicked with a moved camera
        screenX += camera.position.x - camera.viewportWidth/2;
        screenY += camera.position.y - camera.viewportHeight/2;
        
        if (MyGame.DEBUG_MODE) {
          System.out.println("After Touch(" + screenX + "," + screenY + ")");
        }

        if (boxes[0].contains(screenX, screenY)) {
          System.out.println(texts[0]);
        } else {
          if (boxes[1].contains(screenX, screenY)) {
            System.out.println(texts[1]);
            LocalDataManager.getInstance().savePlayerData(UserData.getInstance().getCharacters());
            m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
          }
        }
        return true;
      }
    });
  }

  /**
   * This method controls the camera movement, it is only a test,
   * it won't be used in this screen
   */
  public void processUserInput() {
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      camera.translate(5 * Gdx.graphics.getDeltaTime() * 60, 0);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      camera.translate(-5 * Gdx.graphics.getDeltaTime() * 60, 0);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      camera.translate(0, -5 * Gdx.graphics.getDeltaTime() * 60);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      camera.translate(0, 5 * Gdx.graphics.getDeltaTime() * 60);
    }
    
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.LEVELS);
    }
    
    //if (MyGame.DEBUG_MODE) {
    //  System.out.println("CameraPointing(" + camera.position.x + ", " + camera.position.y + ")");
    //}
  }

  @Override
  public void render(float f) {
    Gdx.gl.glClearColor(0F, 0F, 0F, 0F);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    //we process the user input before we draw
    processUserInput();
    
    stateTime += Gdx.graphics.getDeltaTime();

    camera.update();

    batch.setProjectionMatrix(camera.combined);

    batch.begin();

    layout.setText(fontw, DatabaseDataManager.getInstance().getUser());
    
    fontw.draw(batch, texts[0], 50, 50);
    fontw.draw(batch, texts[1], MyGame.WIDTH/2 + layout.width/2, 800);
    
    batch.end();
  }

  @Override
  public void resize(int i, int i1) {
    
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
    batch.dispose();
  }
}
