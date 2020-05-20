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
import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.utils.LocalDataManager;
import com.munozdiego.freenscaelis.utils.UserData;

/**
 *
 * @author diego
 */
public class SelectPlayerScreen implements Screen {

  public boolean multi;
  
  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;
  
  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fonty;
  BitmapFont fontr;

  Sprite sprite_back;

  
  final String[] texts = new String[]{
    "Erase",
    "Create New",
    "Start Game"
  };

  //used to compute the dimensions of the text
  GlyphLayout layout;

  final float[][] posCol = new float[][]{
    {1200, 50}, //character slots
    {1200, 100},
    {1200, 150},
    {1050, 50}, //erase buttons
    {1050, 100},
    {1050, 150},
    {1050, 200}, //create new
    {1050, 800} //start game
  };

  final Rectangle[] boxes_player_selection = new Rectangle[posCol.length];

  Personaje pj;
  float stateTime;

  UserData userdata;

  public SelectPlayerScreen(MyGame g) {

    userdata = UserData.getInstance();

    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);
    
    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fonty = Assets.getFont("pixel32y");
    fontr = Assets.getFont("pixel32r");

    layout = new GlyphLayout();

    for (int i = boxes_player_selection.length - 1; i >= 0; --i) {
      boxes_player_selection[i] = new Rectangle();
    }

    //setting the boxes where the user will click
    for (int i = 0; i < 3; i++) {
      layout.setText(fontw, userdata.getCharacters()[i] == null ? "" : userdata.getCharacters()[i].getName());
      boxes_player_selection[i].set(posCol[i][0], posCol[i][1], layout.width, layout.height * -1);
    }
    for (int i = 3; i < 6; i++) {
      layout.setText(fontw, texts[0]);
      boxes_player_selection[i].set(posCol[i][0], posCol[i][1], layout.width, layout.height * -1);
    }
    layout.setText(fontw, texts[1]);
    boxes_player_selection[6].set(posCol[6][0], posCol[6][1], layout.width, layout.height * -1);
    layout.setText(fontw, texts[2]);
    boxes_player_selection[7].set(posCol[7][0], posCol[7][1], layout.width, layout.height * -1);

    //sprite_back = Assets.getSprite("images/bg-re.png");
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
        
        for (int i = 0; i < 3; i++) {
          layout.setText(fontw, userdata.getCharacters()[i] == null ? "" : userdata.getCharacters()[i].getName());
          boxes_player_selection[i].set(posCol[i][0], posCol[i][1], layout.width, layout.height * -1);
        }

        //testing on changing the position were the user clicked with a moved camera
        screenX += camera.position.x - camera.viewportWidth / 2;
        screenY += camera.position.y - camera.viewportHeight / 2;

        if (MyGame.DEBUG_MODE) {
          System.out.println("After Touch(" + screenX + "," + screenY + ")");
        }

        if (boxes_player_selection[0].contains(screenX, screenY)) {
          if (MyGame.DEBUG_MODE) {
            System.out.println("player 0");
          }
          pj = userdata.getCharacters()[0];
          userdata.selectCharacter(pj);
        } else {
          if (boxes_player_selection[1].contains(screenX, screenY)) {
            if (MyGame.DEBUG_MODE) {
              System.out.println("player 1");
            }
            pj = userdata.getCharacters()[1];
            userdata.selectCharacter(pj);
          } else {
            if (boxes_player_selection[2].contains(screenX, screenY)) {
              if (MyGame.DEBUG_MODE) {
                System.out.println("player 2");
              }
              pj = userdata.getCharacters()[2];
              userdata.selectCharacter(pj);
            } else {
              if (boxes_player_selection[3].contains(screenX, screenY)) {
                if (MyGame.DEBUG_MODE) {
                  System.out.println("erase 0");
                }
                if (userdata.getCurrentCharacter() == userdata.getCharacters()[0]) {
                  pj = null;
                  userdata.selectCharacter(null);
                }
                userdata.eraseCharacter(0);
                LocalDataManager.getInstance().savePlayerData(userdata.getCharacters());
              } else {
                if (boxes_player_selection[4].contains(screenX, screenY)) {
                  if (MyGame.DEBUG_MODE) {
                    System.out.println("erase 1");
                  }
                  if (userdata.getCurrentCharacter() == userdata.getCharacters()[1]) {
                    pj = null;
                    userdata.selectCharacter(null);
                  }
                  userdata.eraseCharacter(1);
                  LocalDataManager.getInstance().savePlayerData(userdata.getCharacters());
                } else {
                  if (boxes_player_selection[5].contains(screenX, screenY)) {
                    if (MyGame.DEBUG_MODE) {
                      System.out.println("erase 2");
                    }
                    if (userdata.getCurrentCharacter() == userdata.getCharacters()[2]) {
                      pj = null;
                      userdata.selectCharacter(null);
                    }
                    userdata.eraseCharacter(2);
                    LocalDataManager.getInstance().savePlayerData(userdata.getCharacters());
                  } else {
                    if (boxes_player_selection[6].contains(screenX, screenY)) {
                      if (MyGame.DEBUG_MODE) {
                        System.out.println("Create new");
                      }
                      m_game.showScreen(MyGame.CodeScreen.CREATE_CHAR);
                    } else {
                      if (boxes_player_selection[7].contains(screenX, screenY)) {
                        System.out.println("Start Game");
                        if (pj != null) {
                          if(multi){
                            m_game.showScreen(MyGame.CodeScreen.CONNECT);
                          } else{
                            m_game.showScreen(MyGame.CodeScreen.LEVELS);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return true;
      }
    });
  }

  /**
   * This method controls the camera movement, it is only a test, it won't be
   * used in this screen
   */
  public void processUserInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
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

    //batch.draw(sprite_back, 0, 0);
    if (pj != null) {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(stateTime, true), 500, 500);
    }

    for (int i = 0; i < 3; i++) {
      if (userdata.getCharacters()[i] == pj && pj != null) {
        fonty.draw(batch, userdata.getCharacters()[i] == null ? "" : userdata.getCharacters()[i].getName(), posCol[i][0], posCol[i][1]);
      } else {
        fontw.draw(batch, userdata.getCharacters()[i] == null ? "" : userdata.getCharacters()[i].getName(), posCol[i][0], posCol[i][1]);
      }
    }
    for (int i = 3; i < 6; i++) {
      fontr.draw(batch, texts[0], posCol[i][0], posCol[i][1]);
    }
    fontw.draw(batch, texts[1], posCol[6][0], posCol[6][1]);
    fontw.draw(batch, texts[2], posCol[7][0], posCol[7][1]);
    
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
    sprite_back.getTexture().dispose();
  }
}
