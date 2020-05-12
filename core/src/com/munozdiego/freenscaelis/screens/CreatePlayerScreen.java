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
import com.munozdiego.freenscaelis.models.Entidad;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.utils.InputText;
import com.munozdiego.freenscaelis.utils.LocalDataManager;
import com.munozdiego.freenscaelis.utils.UserData;

/**
 *
 * @author diego
 */
public class CreatePlayerScreen implements Screen {

  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;

  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fonty;
  BitmapFont fontr;

  Sprite sprite_back;

  final String[] texts = new String[]{
    "Choose your class",
    "Select",
    "Name. "
  };

  //used to compute the dimensions of the text
  GlyphLayout layout;

  final float[][] posCol = new float[][]{
    {300, 500}, //Class box
    {500, 500},
    {-1, 600} //Select (la x hay que calcularla luego)
  };

  final Rectangle[] boxes_player_selection = new Rectangle[posCol.length];

  Personaje pj_sword;
  Personaje pj_scepter;
  Personaje selected;
  float stateTime;

  UserData userdata;

  InputText textAdapter;
  
  public CreatePlayerScreen(MyGame g) {

    userdata = UserData.getInstance();

    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fonty = Assets.getFont("pixel32y");
    fontr = Assets.getFont("pixel32r");

    layout = new GlyphLayout();

    pj_sword = new Personaje();
    pj_sword.setClase(Personaje.Clase.SWORD);
    pj_sword.init(Personaje.Clase.SWORD, 0);
    pj_sword.setPosx(posCol[0][0]);
    pj_sword.setPosy(posCol[0][1]);

    pj_scepter = new Personaje();
    pj_scepter.setClase(Personaje.Clase.SCEPTER);
    pj_scepter.init(Personaje.Clase.SCEPTER, 0);
    pj_scepter.setPosx(posCol[1][0]);
    pj_scepter.setPosy(posCol[1][1]);

    for (int i = boxes_player_selection.length - 1; i >= 0; --i) {
      boxes_player_selection[i] = new Rectangle();
    }

    //Doesn't work outside the adapter in this screen, don't know why
    //setting the boxes where the user will click
    for (int i = 0; i < 2; i++) {
      boxes_player_selection[i].set(posCol[i][0], posCol[i][1], pj_scepter.getAnimaciones().get(Entidad.Estado.IDLE_RIGHT).getKeyFrame(0).getRegionWidth(), pj_scepter.getAnimaciones().get(Entidad.Estado.IDLE_RIGHT).getKeyFrame(0).getRegionHeight());
    }
    layout.setText(fontw, texts[1]);
    boxes_player_selection[2].set(camera.viewportWidth/2 - layout.width/2, posCol[2][1], layout.width, layout.height * -1);

    //sprite_back = Assets.getSprite("images/bg-re.png");
    
    textAdapter = new InputText() {

      @Override
      public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (MyGame.DEBUG_MODE) {
          System.out.println("Before Touch(" + screenX + "," + screenY + ")");
          System.out.println("camera(" + camera.position.x + "," + camera.position.y + ")");
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
            selected = pj_sword;
            pj_sword.setCurrentState(Entidad.Estado.RUN_RIGHT);
            pj_scepter.setCurrentState(Entidad.Estado.IDLE_RIGHT);
          }
        } else {
          if (boxes_player_selection[1].contains(screenX, screenY)) {
            if (MyGame.DEBUG_MODE) {
              System.out.println("player 1");
              pj_scepter.setCurrentState(Entidad.Estado.RUN_RIGHT);
              pj_sword.setCurrentState(Entidad.Estado.IDLE_RIGHT);
              selected = pj_scepter;
            }
          } else {
            if (boxes_player_selection[2].contains(screenX, screenY)) {
              if (MyGame.DEBUG_MODE) {
                System.out.println("Create player");
              }
              if (selected != null) {
                if(!textAdapter.getText().equals("")){
                  selected.setName(textAdapter.getText());
                  userdata.addCharacter(selected);
                  LocalDataManager.getInstance().savePlayerData(userdata.getCharacters());
                  m_game.showScreen(MyGame.CodeScreen.SELECT_CHAR);
                }
              }
            }
          }
        }
        return true;
      }
    };
  }

  @Override
  public void show() {
    selected = null;

    //setting of the InputProcessor we'll use in this screen
    Gdx.input.setInputProcessor(textAdapter);
  }

  /**
   * This method controls the camera movement, it is only a test, it won't be
   * used in this screen
   */
  public void processUserInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.SELECT_CHAR);
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
    batch.draw(pj_sword.getAnimaciones().get(pj_sword.getCurrentState()).getKeyFrame(stateTime, true), pj_sword.getPosx(), pj_sword.getPosy());
    batch.draw(pj_scepter.getAnimaciones().get(pj_scepter.getCurrentState()).getKeyFrame(stateTime, true), pj_scepter.getPosx(), pj_scepter.getPosy());

    layout.setText(fontw, texts[0]);
    fontw.draw(batch, texts[0], camera.viewportWidth/2 - layout.width/2, 100);
    layout.setText(fontw, texts[1]);
    fontw.draw(batch, texts[1], camera.viewportWidth/2 - layout.width/2, posCol[2][1]);
    layout.setText(fontw, texts[2]);
    fonty.draw(batch, texts[2], 200, 150);
    fontw.draw(batch, textAdapter.getText(), layout.width + 20 + 200, 150);

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
    pj_scepter.setCurrentState(Entidad.Estado.IDLE_RIGHT);
    pj_sword.setCurrentState(Entidad.Estado.IDLE_RIGHT);
    textAdapter.resetText();
    selected = null;
  }

  @Override
  public void dispose() {
    batch.dispose();
    sprite_back.getTexture().dispose();
  }
}
