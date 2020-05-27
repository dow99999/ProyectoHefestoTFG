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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.munozdiego.freenscaelis.models.HUD;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.utils.ColliderUtils;
import com.munozdiego.freenscaelis.utils.SocketDataManager;
import com.munozdiego.freenscaelis.utils.UserData;

/**
 *
 * @author diego
 */
public class ArenaScreen implements Screen {

  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;
  ShapeRenderer shape;

  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fontb;

  //used to compute the dimensions of the text
  GlyphLayout layout;

  //final Rectangle[] boxes = new Rectangle[texts.length];
  Personaje pj;
  Personaje pj2;
  float stateTime;
  float attackTime;

  float respawnx;
  float respawny;
  int respawnmap;

  UserData userdata;

  boolean colliderdebug;
  boolean disableCameraLock;
  boolean disableSmoothCamera;
  boolean disableMultiPosGuess;

  boolean initialized = false;

  ScreenData screendata;

  Rectangle[] colliders;
  Sprite[] layers;

  Music bg;

  Sound att;
  Sound hit;

  HUD health;

  public ArenaScreen(MyGame g) {
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
    disableSmoothCamera = false;
    disableMultiPosGuess = false;

    health = new HUD(camera);
    health.getTextura_estatica_estados().put(0, Assets.getSprite("HUD/HP/Value/HP_Value_0.png"));
    health.getTextura_estatica_estados().put(1, Assets.getSprite("HUD/HP/Value/HP_Value_1.png"));
    health.getTextura_estatica_estados().put(2, Assets.getSprite("HUD/HP/Value/HP_Value_2.png"));
    health.getTextura_estatica_estados().put(3, Assets.getSprite("HUD/HP/Value/HP_Value_3.png"));
    health.getTextura_estatica_estados().put(4, Assets.getSprite("HUD/HP/Value/HP_Value_4.png"));
    health.getTextura_estatica_estados().put(5, Assets.getSprite("HUD/HP/Value/HP_Value_5.png"));
    health.setPosx(50);
    health.setPosy(-50);

    bg = Assets.getMusic(String.valueOf(ScreenData.ARENA));
    bg.setLooping(true);
    bg.setVolume(0.2f);
    att = Assets.getSound("attack");
    hit = Assets.getSound("hit_pj");
  }

  private void initScreenData() {
    colliders = screendata.getColliders();
    layers = screendata.getLayers();
  }

  @Override
  public void show() {
    pj = userdata.getCurrentCharacter();
    if (!initialized) {
      synchronized (SocketDataManager.lastInstance) {
        pj2 = SocketDataManager.lastInstance.pj2;
        while (pj2 == null) {
          try {
            SocketDataManager.lastInstance.wait();
          } catch (InterruptedException ex) {
          }
          pj2 = SocketDataManager.lastInstance.pj2;
        }
        pj2.init(pj2.getClase(), 1);

        pj.setMapa(ScreenData.ARENA);
        pj2.setMapa(ScreenData.ARENA);

        if (SocketDataManager.lastInstance.main) {
          pj.setPosx(1656);
          pj2.setPosx(381);
          pj.setCamx(1196);
          pj.setCamy(579);
        } else {
          pj.setPosx(381);
          pj2.setPosx(1656);
          pj.setCamx(961);
          pj.setCamy(579);
        }

        pj.setPosy(500);
        pj2.setPosy(500);
        
        setRespawnPoint();
      }
    }

    bg.play();
    initialized = true;
    camera.position.x = pj.getCamx();
    camera.position.y = pj.getCamy();

    screendata.setCurrentMapa(pj.getMapa());
    initScreenData();

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

  public void setRespawnPoint(){
    respawnx = pj.getPosx();
    respawny = pj.getPosy();
    respawnmap = pj.getMapa();
  }
  
  public void processEnemies() {
    pj.setInvTime(pj.getInvTime() - Gdx.graphics.getDeltaTime());
    pj2.setInvTime(pj2.getInvTime() - Gdx.graphics.getDeltaTime());

    //hit player
    if (pj.getColliders().get(pj.getCurrentState()).overlaps(pj2.getColliders().get(pj2.getCurrentState()))) {
      if (!((pj2.getCurrentState() == Entidad.Estado.ATT_LEFT || pj2.getCurrentState() == Entidad.Estado.ATT_RIGHT)
              && (pj.getCurrentState() == Entidad.Estado.ATT_LEFT || pj.getCurrentState() == Entidad.Estado.ATT_RIGHT))) {

        if ((pj2.getCurrentState() == Entidad.Estado.ATT_LEFT || pj2.getCurrentState() == Entidad.Estado.ATT_RIGHT)) {
          if (pj.getInvTime() <= 0) {
            pj.resetInvTime();
            hit.play();
            if (pj.getStats()[0] > 0) {
              pj.getStats()[0] -= pj2.getStats()[1];
              if (pj.getStats()[0] < 0) {
                pj.getStats()[0] = 0;
              }
            } else {
              pj.setCurrentState(Entidad.Estado.DEAD);
            }
          }
        } else {
          if ((pj.getCurrentState() == Entidad.Estado.ATT_LEFT || pj.getCurrentState() == Entidad.Estado.ATT_RIGHT)) {
            if (pj2.getInvTime() <= 0) {
              pj2.resetInvTime();
              hit.play();
              if (pj2.getStats()[0] > 0) {
                pj2.getStats()[0] -= pj.getStats()[1];
                if (pj2.getStats()[0] < 0) {
                  pj2.getStats()[0] = 0;
                }
              } else {
                pj2.setCurrentState(Entidad.Estado.DEAD);
              }
            }
          }
        }
      }
    }
  }

  /**
   * This method controls the player movement
   */
  public void processUserInput() {
    boolean moving = false;
    float lastPos;
    attackTime += Gdx.graphics.getDeltaTime();

    //movement player
    if (pj.getStats()[0] > 0) {
      if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
        pj.setvDir(0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
          pj.setCurrentState(
                  pj.getCurrentState() == Entidad.Estado.RUN_RIGHT || pj.getCurrentState() == Entidad.Estado.IDLE_RIGHT
                  ? Entidad.Estado.RUN_RIGHT
                  : Entidad.Estado.RUN_LEFT);
          lastPos = pj.getPosy();
          pj.setPosy(pj.getPosy() - pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
          pj.setvDir(-2);
          if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
            pj.setvDir(0);
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
          pj.setvDir(2);
          if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
            pj.setvDir(0);
            pj.setPosy(lastPos);
          }
          moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
          pj.setCurrentState(Entidad.Estado.RUN_RIGHT);
          lastPos = pj.getPosx();
          pj.setPosx(pj.getPosx() + pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
          if (pj.getvDir() != 0) {
            pj.setvDir(pj.getvDir() / 2);
          }
          if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState())) != null) {
            pj.setPosx(lastPos);
          }
          moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
          pj.setCurrentState(Entidad.Estado.RUN_LEFT);
          lastPos = pj.getPosx();
          pj.setPosx(pj.getPosx() - pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
          if (pj.getvDir() != 0 && pj.getvDir() != 1 && pj.getvDir() != -1) {
            pj.setvDir(pj.getvDir() / 2);
          }
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
        if (attackTime > (pj.getAnimaciones().get(pj.getCurrentState()).getAnimationDuration())) {
          pj.setCurrentState(pj.getCurrentState() == Entidad.Estado.ATT_RIGHT
                  ? Entidad.Estado.IDLE_RIGHT
                  : Entidad.Estado.IDLE_LEFT);
        }
      }

      if (Gdx.input.isKeyJustPressed(Input.Keys.O) && (attackTime > (pj.getAnimaciones().get(pj.getCurrentState()).getAnimationDuration()) + 0.2f)) {
        pj.setCurrentState(pj.getCurrentState() == Entidad.Estado.IDLE_RIGHT || pj.getCurrentState() == Entidad.Estado.RUN_RIGHT || pj.getCurrentState() == Entidad.Estado.ATT_RIGHT
                ? Entidad.Estado.ATT_RIGHT
                : Entidad.Estado.ATT_LEFT);
        attackTime = 0;
        att.play();
        pj.getAnimaciones().get(pj.getCurrentState()).setPlayMode(Animation.PlayMode.NORMAL);
      }
    } else {
      pj.setCurrentState(Entidad.Estado.DEAD);
      if (pj.getDeathDelta() > pj.getAnimaciones().get(Entidad.Estado.DEAD).getAnimationDuration() + 0.2f) {
        pj.setPosx(respawnx);
        pj.setPosy(respawny);
        pj.setMapa(respawnmap);
        pj.getStats()[0] = 5;
        pj.setDeathDelta(0);
      } else {
        pj.setDeathDelta(pj.getDeathDelta() + Gdx.graphics.getDeltaTime());
      }
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      SocketDataManager.lastInstance.interrupt();
      m_game.showScreen(MyGame.CodeScreen.MAIN_MENU);
    }

    if (MyGame.DEBUG_MODE) {

      if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
        colliderdebug = !colliderdebug;
      }

      if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
        disableCameraLock = !disableCameraLock;
      }

      if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
        disableSmoothCamera = !disableSmoothCamera;
      }

      if (Gdx.input.isKeyJustPressed(Input.Keys.F6)) {
        disableMultiPosGuess = !disableMultiPosGuess;
      }

      if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
        System.out.println("Player position: " + pj.getPosx() + ", " + pj.getPosy());
        System.out.println("Camera position: " + camera.position.x + ", " + camera.position.y);
      }
    }

    float camx = disableSmoothCamera ? pj.getPosx() + pj.getCenterX() : camera.position.x + (pj.getPosx() + pj.getCenterX() - camera.position.x) * Gdx.graphics.getDeltaTime();
    float camy = disableSmoothCamera ? pj.getPosy() + pj.getCenterY() : camera.position.y + (pj.getPosy() + pj.getCenterY() - camera.position.y) * Gdx.graphics.getDeltaTime();

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
    pj.setCamx(camera.position.x);
    pj.setCamy(camera.position.y);
  }

  public void guessSecondPlayerPos() {
    if (pj2.getCurrentState() == Entidad.Estado.RUN_LEFT) {
      synchronized (pj2) {
        if (pj2.getvDir() != 2 && pj2.getvDir() != -2) {
          pj2.setPosx(pj2.getPosx() - pj2.getSpeed());
        }
      }
    } else {
      if (pj2.getCurrentState() == Entidad.Estado.RUN_RIGHT) {
        synchronized (pj2) {
          if (pj2.getvDir() != 2 && pj2.getvDir() != -2) {
            pj2.setPosx(pj2.getPosx() + pj2.getSpeed());
          }
        }
      }
    }
    synchronized (pj2) {
      switch (pj2.getvDir()) {
        case 2:
          pj2.setPosy(pj2.getPosy() + pj2.getSpeed());
          break;
        case -2:
          pj2.setPosy(pj2.getPosy() + pj2.getSpeed() * -1);
          break;
        default:
          pj2.setPosy(pj2.getPosy() + pj2.getSpeed() * pj2.getvDir());
          break;
      }
    }
  }

  @Override
  public void render(float f) {
    Gdx.gl.glClearColor(0F, 0F, 0F, 0F);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    //we process the user input before we draw
    processUserInput();
    guessSecondPlayerPos();

    processEnemies();
    stateTime += Gdx.graphics.getDeltaTime();

    camera.update();

    batch.setProjectionMatrix(camera.combined);

    batch.begin();

    //Drawing the scene
    for (int i = 0; i < screendata.getBaseLayers(); i++) {
      batch.draw(layers[i], 0, 0);
    }

    //drawing user 2
    if (pj2.getCurrentState() != Entidad.Estado.ATT_LEFT && pj2.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
      batch.draw(pj2.getAnimaciones().get(pj2.getCurrentState()).getKeyFrame(stateTime, true), pj2.getPosx(), pj2.getPosy());
    } else {
      batch.draw(pj2.getAnimaciones().get(pj2.getCurrentState()).getKeyFrame(attackTime, true), pj2.getPosx(), pj2.getPosy());
    }

    //drawing user
    if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(stateTime, true), pj.getPosx(), pj.getPosy());
    } else {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(attackTime, true), pj.getPosx(), pj.getPosy());
    }

    for (int i = screendata.getBaseLayers(); i < layers.length; i++) {
      batch.draw(layers[i], 0, 0);
    }

    //Drawing the HUD
    batch.draw(health.getTextura_estatica_estados().get((int) pj.getStats()[0]), health.getPosx(), health.getPosy());

    batch.end();

    //Drawing Debug tools
    if (colliderdebug) {

      shape.setProjectionMatrix(camera.combined);
      if (MyGame.DEBUG_MODE) {
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.setColor(Color.ORANGE);
        for (Rectangle r : colliders) {
          shape.rect(r.x, r.y, r.width, r.height);
        }

        shape.setColor(Color.GREEN);
        Rectangle aux = pj.getColliders().get(pj.getCurrentState());
        shape.rect(aux.x, aux.y, aux.width, aux.height);

        shape.setColor(Color.RED);
        Rectangle aux2 = pj2.getColliders().get(pj2.getCurrentState());
        shape.rect(aux2.x, aux2.y, aux2.width, aux2.height);

        shape.end();
      }
    }
  }

  @Override
  public void resize(int i, int i1) {

  }

  @Override
  public void pause() {
    bg.stop();
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
    shape.dispose();
  }
}
