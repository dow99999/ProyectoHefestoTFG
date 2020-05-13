/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.screens.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.models.Entidad;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.utils.ColliderUtils;
import com.munozdiego.freenscaelis.utils.UserData;
import java.util.Set;

/**
 *
 * @author diego
 */
public class LevelScreen implements Screen {

  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;
  ShapeRenderer shape;

  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fontb;

//  final String[] texts = new String[]{
//    "Log in",
//    "Register",
//    "One player",
//    "Two players",
//    "Player vs player",
//    "Configuration",
//    "Credits",
//    "Exit"
//  };
  //used to compute the dimensions of the text
  GlyphLayout layout;

  //final Rectangle[] boxes = new Rectangle[texts.length];
  Personaje pj;
  float stateTime;
  float attackTime;

  UserData userdata;

  boolean colliderdebug;
  boolean disableCameraLock;

  ScreenData screendata;

  Rectangle[] colliders;
  Set<Rectangle> warpZones;
  Sprite[] layers;

  public LevelScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);
    shape = new ShapeRenderer();

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fontb = Assets.getFont("pixel32b");

    layout = new GlyphLayout();

    userdata = UserData.getInstance();
    screendata = ScreenData.getInstance();

    colliderdebug = false;
    disableCameraLock = false;

    initBoxes();
  }

  private void initBoxes() {

    colliders = screendata.getColliders();
    warpZones = screendata.getWarpZones();
    layers = screendata.getLayers();
  }

  @Override
  public void show() {
    pj = userdata.getCurrentCharacter();
    pj.init(pj.getClase(), 0);

    camera.position.x = layers[0].getWidth() / 2;
    camera.position.y = layers[0].getHeight() / 2;

    pj.setPosx(camera.position.x);
    pj.setPosy(camera.position.y);

    //setting of the InputProcessor we'll use in this screen
    Gdx.input.setInputProcessor(new InputAdapter() {
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

        return true;
      }
    });
  }

  /**
   * This method controls the player movement
   */
  public void processUserInput() {
    boolean moving = false;
    float lastPos;

    //movement player
    if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {

      if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        pj.setCurrentState(
                pj.getCurrentState() == Entidad.Estado.RUN_RIGHT || pj.getCurrentState() == Entidad.Estado.IDLE_RIGHT
                ? Entidad.Estado.RUN_RIGHT
                : Entidad.Estado.RUN_LEFT);
        lastPos = pj.getPosy();
        pj.setPosy(pj.getPosy() - pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
          pj.setPosy(lastPos);
        }
        moving = true;
      }

      if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        pj.setCurrentState(
                pj.getCurrentState() == Entidad.Estado.RUN_RIGHT || pj.getCurrentState() == Entidad.Estado.IDLE_RIGHT
                ? Entidad.Estado.RUN_RIGHT
                : Entidad.Estado.RUN_LEFT);
        lastPos = pj.getPosy();
        pj.setPosy(pj.getPosy() + pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
          pj.setPosy(lastPos);
        }
        moving = true;
      }

      if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        pj.setCurrentState(Entidad.Estado.RUN_RIGHT);
        lastPos = pj.getPosx();
        pj.setPosx(pj.getPosx() + pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
          pj.setPosx(lastPos);
        }
        moving = true;
      }

      if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        pj.setCurrentState(Entidad.Estado.RUN_LEFT);
        lastPos = pj.getPosx();
        pj.setPosx(pj.getPosx() - pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
          pj.setPosx(lastPos);
        }
        moving = true;
      }

      if (!moving) {
        pj.setCurrentState(pj.getCurrentState() == Entidad.Estado.RUN_RIGHT || pj.getCurrentState() == Entidad.Estado.ATT_RIGHT || pj.getCurrentState() == Entidad.Estado.IDLE_RIGHT
                ? Entidad.Estado.IDLE_RIGHT
                : Entidad.Estado.IDLE_LEFT);
      }

    } else {
      attackTime += Gdx.graphics.getDeltaTime();
      if (attackTime > (pj.getAnimaciones().get(pj.getCurrentState()).getAnimationDuration())) {
        pj.setCurrentState(pj.getCurrentState() == Entidad.Estado.ATT_RIGHT
                ? Entidad.Estado.IDLE_RIGHT
                : Entidad.Estado.IDLE_LEFT);
      }
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
      pj.setCurrentState(pj.getCurrentState() == Entidad.Estado.IDLE_RIGHT || pj.getCurrentState() == Entidad.Estado.RUN_RIGHT || pj.getCurrentState() == Entidad.Estado.ATT_RIGHT
              ? Entidad.Estado.ATT_RIGHT
              : Entidad.Estado.ATT_LEFT);
      attackTime = 0;
      pj.getAnimaciones().get(pj.getCurrentState()).setPlayMode(Animation.PlayMode.NORMAL);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
      //TODO menu de pausa
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
      colliderdebug = !colliderdebug;
    }
    
    if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
      disableCameraLock = !disableCameraLock;
    }
    
    if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
      System.out.println("Player position: " + pj.getPosx() + ", " + pj.getPosy());
      System.out.println("Camera position: " + camera.position.x + ", " + camera.position.y);
    }

    float camx = camera.position.x + (pj.getPosx() + pj.getCenterX() - camera.position.x) * Gdx.graphics.getDeltaTime();
    float camy = camera.position.y + (pj.getPosy() + pj.getCenterY() - camera.position.y) * Gdx.graphics.getDeltaTime();
    int cameraBounds = ColliderUtils.cameraOutside(camx - camera.viewportWidth / 2, camy - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight, layers[0].getWidth(), layers[0].getHeight());

    if (!disableCameraLock) {

      if (cameraBounds == 0) { //free camera movement
        camera.position.set(
                camx,
                camy,
                0);
      } else {
        if (cameraBounds == 1) { //horizontal movement locked
          camera.position.set(
                  camera.position.x,
                  camy,
                  0);
        } else {
          if (cameraBounds == 2) { //vertical movement locked
            camera.position.set(
                    camx,
                    camera.position.y,
                    0);
          }
        }
      }
    } else {
      camera.position.set(
              camx,
              camy,
              0);
    }
  }

  public void processWarpEnter() {
    Rectangle warp = ColliderUtils.checkCollitions(warpZones, pj.getColliders().get(pj.getCurrentState()));
    int auxMap;
    if (warp != null) {
      auxMap = screendata.getCurrentMapa();
      screendata.setCurrentMapa(screendata.getWarpMap(warp));
      camera.position.x = screendata.getCameraWarpPos(auxMap, screendata.getCurrentMapa()).x;
      camera.position.y = screendata.getCameraWarpPos(auxMap, screendata.getCurrentMapa()).y;
      pj.setPosx(screendata.getPjWarpPos(auxMap, screendata.getCurrentMapa()).x);
      pj.setPosy(screendata.getPjWarpPos(auxMap, screendata.getCurrentMapa()).y);
      initBoxes();
    }
  }

  @Override
  public void render(float f) {
    Gdx.gl.glClearColor(0F, 0F, 0F, 0F);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    //we process the user input before we draw
    processUserInput();
    processWarpEnter();

    stateTime += Gdx.graphics.getDeltaTime();

    camera.update();

    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    for (int i = 0; i < screendata.getBaseLayers(); i++) {
      batch.draw(layers[i], 0, 0);
    }

    if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(stateTime, true), pj.getPosx(), pj.getPosy());
    } else {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(attackTime, true), pj.getPosx(), pj.getPosy());
    }

    for (int i = screendata.getBaseLayers(); i < layers.length; i++) {
      batch.draw(layers[i], 0, 0);
    }

    batch.end();

    if (colliderdebug) {

      shape.setProjectionMatrix(camera.combined);
      if (MyGame.DEBUG_MODE) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        for (Rectangle r : colliders) {
          shape.rect(r.x, r.y, r.width, r.height);
        }
        shape.setColor(Color.BLUE);
        for (Rectangle r : warpZones) {
          shape.rect(r.x, r.y, r.width, r.height);
        }
        shape.setColor(Color.GREEN);
        Rectangle aux = pj.getColliders().get(pj.getCurrentState());
        shape.rect(aux.x, aux.y, aux.width, aux.height);
        shape.end();
      }
    }
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
