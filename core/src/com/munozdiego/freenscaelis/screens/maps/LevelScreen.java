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
import com.munozdiego.freenscaelis.models.Enemigo;
import com.munozdiego.freenscaelis.models.Entidad;
import com.munozdiego.freenscaelis.models.HUD;
import com.munozdiego.freenscaelis.models.Personaje;
import com.munozdiego.freenscaelis.screens.SelectPlayerScreen;
import com.munozdiego.freenscaelis.utils.ColliderUtils;
import com.munozdiego.freenscaelis.utils.SocketDataManager;
import com.munozdiego.freenscaelis.utils.UserData;
import java.util.ArrayList;
import java.util.Iterator;
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
  Personaje pj2;
  float stateTime;
  float attackTime;

  UserData userdata;

  boolean colliderdebug;
  boolean disableCameraLock;
  boolean disableSmoothCamera;
  boolean disableMultiPosGuess;

  boolean multi;
  boolean initialized = false;

  ScreenData screendata;

  Rectangle[] colliders;
  Set<Rectangle> warpZones;
  Sprite[] layers;
  ArrayList<Enemigo> enemigos;

  HUD health;

  Music currentbg;
  
  Sound hit_pj;
  Sound hit_sk;
  Sound hit_gb;
  Sound hit_sl;
  Sound att;
  Sound enemy_death;
  
  private int[][] enemyDir = new int[][]{
    {1, 1},
    {0, 0},
    {1, -1},
    {0, -1},
    {0, 1},
    {1, 0}
  };

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
    
    hit_pj = Assets.getSound("hit_pj");
    hit_sl = Assets.getSound("hit_sl");
    hit_gb = Assets.getSound("hit_gb");
    hit_sk = Assets.getSound("hit_sk");
    att = Assets.getSound("attack");
    enemy_death = Assets.getSound("enemy_death");
  }

  private void initScreenData() {
    colliders = screendata.getColliders();
    warpZones = screendata.getWarpZones();
    layers = screendata.getLayers();
    enemigos = screendata.getEnemigos();
  }

  @Override
  public void show() {
    pj = userdata.getCurrentCharacter();
    multi = ((SelectPlayerScreen) m_game.screens.get(MyGame.CodeScreen.SELECT_CHAR)).multi;
    if (multi && !initialized) {
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
        if (SocketDataManager.lastInstance.main) {
          pj2.setMapa(pj.getMapa());
          pj2.setPosx(pj.getPosx());
          pj2.setPosy(pj.getPosy());
        } else {
          pj.setMapa(pj2.getMapa());
          pj.setPosx(pj2.getPosx());
          pj.setPosy(pj2.getPosy());
        }
      }
    }

    initialized = true;
    camera.position.x = pj.getCamx();
    camera.position.y = pj.getCamy();

    screendata.setCurrentMapa(pj.getMapa());
    setBgMusic(pj.getMapa());
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

  public void processEnemies() {
    float last;
    int rand;
    boolean wandering;
    boolean hit;
    boolean wandering2;
    boolean hit2;

    pj.setInvTime(pj.getInvTime() - Gdx.graphics.getDeltaTime());
    for (Iterator<Enemigo> iter = enemigos.iterator(); iter.hasNext();) {
      wandering = true;
      wandering2 = true;
      hit = false;
      hit2 = false;
      Enemigo e = iter.next();
      e.setInvTime(e.getInvTime() - Gdx.graphics.getDeltaTime());
      //follow player
      if (e.getInvTime() <= 0) {

        if ((pj.getPosx() - e.getPosx()) * (pj.getPosx() - e.getPosx()) + (pj.getPosy() - e.getPosy()) * (pj.getPosy() - e.getPosy()) <= e.getFollowRangePow()) {
          wandering = false;
          if (pj.getPosx() + pj.getCenterX() > e.getPosx() + e.getCenterX()) {
            last = e.getPosx();
            e.setCurrentState(Entidad.Estado.RUN_RIGHT);
            e.setPosx(e.getPosx() + e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosx(last);
            }
          } else {
            last = e.getPosx();
            e.setCurrentState(Entidad.Estado.RUN_LEFT);
            e.setPosx(e.getPosx() - e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosx(last);
            }
          }
          if (pj.getPosy() + pj.getCenterY() > e.getPosy() + e.getCenterY()) {
            last = e.getPosy();
            e.setPosy(e.getPosy() + e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosy(last);
            }
          } else {
            last = e.getPosy();
            e.setPosy(e.getPosy() - e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosy(last);
            }
          }
        }

        if (multi && ((pj2.getPosx() - e.getPosx()) * (pj2.getPosx() - e.getPosx()) + (pj2.getPosy() - e.getPosy()) * (pj2.getPosy() - e.getPosy()) <= e.getFollowRangePow())) {
          wandering2 = false;
          if (pj2.getPosx() + pj2.getCenterX() > e.getPosx() + e.getCenterX()) {
            last = e.getPosx();
            e.setCurrentState(Entidad.Estado.RUN_RIGHT);
            e.setPosx(e.getPosx() + e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosx(last);
            }
          } else {
            last = e.getPosx();
            e.setCurrentState(Entidad.Estado.RUN_LEFT);
            e.setPosx(e.getPosx() - e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosx(last);
            }
          }
          if (pj2.getPosy() + pj2.getCenterY() > e.getPosy() + e.getCenterY()) {
            last = e.getPosy();
            e.setPosy(e.getPosy() + e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosy(last);
            }
          } else {
            last = e.getPosy();
            e.setPosy(e.getPosy() - e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
            if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
              e.setPosy(last);
            }
          }
        }

        if (wandering && wandering2) { //or do wandering
          //e.setDirectionTime(e.getDirectionTime() - Gdx.graphics.getDeltaTime());
          //if (e.getDirectionTime() <= 0) {
          rand = getSyncRandom((enemyDir.length + e.tipo.ordinal()), e.getDirectionTimeF()) % (enemyDir.length - 1);
          e.setCurrentState(enemyDir[rand][0] == 0 ? Entidad.Estado.RUN_LEFT : Entidad.Estado.RUN_RIGHT);
          e.setDirection(enemyDir[rand][1]);
          //}

          last = e.getPosy();
          e.setPosy(e.getPosy() + e.getSpeed() * e.getDirection() * Gdx.graphics.getDeltaTime() * 60);
          if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
            e.setPosy(last);
          }
          switch (e.getCurrentState()) {
            case RUN_LEFT:
              last = e.getPosx();
              e.setPosx(e.getPosx() - e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
              if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
                e.setPosx(last);
              }
              break;
            case RUN_RIGHT:
              last = e.getPosx();
              e.setPosx(e.getPosx() + e.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
              if (ColliderUtils.checkCollitions(colliders, e.getColliders().get(e.getCurrentState())) != null) {
                e.setPosx(last);
              }
              break;
          }
        }
      }

      //hit player
      if (pj.getColliders().get(pj.getCurrentState()).overlaps(e.getColliders().get(e.getCurrentState()))) {
        hit = true;
        if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
          hit = false;
          if (pj.getInvTime() <= 0) {
            pj.resetInvTime();
            hit_pj.play();
            if (pj.getStats()[0] > 0) {
              pj.getStats()[0] -= e.getStats()[1];
              if (pj.getStats()[0] < 0) {
                pj.getStats()[0] = 0;
              }
            } else {
              //TODO end game
            }
          }
        }
      }

      if (multi && pj2.getColliders().get(pj2.getCurrentState()).overlaps(e.getColliders().get(e.getCurrentState()))) {
        hit2 = true;
        if (pj2.getCurrentState() != Entidad.Estado.ATT_LEFT && pj2.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
          hit2 = false;
          if (pj2.getInvTime() <= 0) {
            pj2.resetInvTime();
            if (pj2.getStats()[0] > 0) {
              pj2.getStats()[0] -= e.getStats()[1];
              if (pj2.getStats()[0] < 0) {
                pj2.getStats()[0] = 0;
              }
            } else {
              //TODO end game
            }
          }
        }
      }

      if (hit || hit2) {
        if (e.getInvTime() <= 0) {
          e.resetInvTime();
          if (e.getStats()[0] > 0) {
            e.getStats()[0] -= hit ? pj.getStats()[1] : pj2.getStats()[1];
            switch(e.tipo){
              case GOBLIN:
              case BOSS_GOBLIN:
                hit_gb.play();
                break;
              case SKELETON:
              case BOSS_SKELETON:
                hit_sk.play();
                break;
              case SLIME:
              case BOSS_SLIME:
                hit_sl.play();
                break;
              default:
                hit_sl.play();
            }
            if (e.getStats()[0] < 0) {
              e.getStats()[0] = 0;
            }
          } else {
            enemy_death.play();
            iter.remove();
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
        System.out.println("W: " + pj.getvDir());
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
        System.out.println("S: " + pj.getvDir());
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
        System.out.println("D: " + pj.getvDir());
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
        System.out.println("A: " + pj.getvDir());
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

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      m_game.showScreen(MyGame.CodeScreen.PAUSE);
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

    System.out.println("end process: " + pj.getvDir());
  }

  public void guessSecondPlayerPos() {
    if (pj2.getCurrentState() == Entidad.Estado.RUN_LEFT) {
      if (pj2.getvDir() != 2 && pj2.getvDir() != -2) {
        pj2.setPosx(pj2.getPosx() - pj2.getSpeed());
      }

    } else {
      if (pj2.getCurrentState() == Entidad.Estado.RUN_RIGHT) {
        if (pj2.getvDir() != 2 && pj2.getvDir() != -2) {
          pj2.setPosx(pj2.getPosx() + pj2.getSpeed());
        }
      }

    }
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

  public void processWarpEnter() {
    Rectangle warp = ColliderUtils.checkCollitions(warpZones, pj.getColliders().get(pj.getCurrentState()));
    int auxMap;

    if (multi) {
      Rectangle warp2 = ColliderUtils.checkCollitions(warpZones, pj2.getColliders().get(pj2.getCurrentState()));
      if (warp != null && warp == warp2) {
        try {
          Thread.sleep(200); //esperamos 12 fotogramas
        } catch (InterruptedException ex) {
        }
        auxMap = screendata.getCurrentMapa();
        screendata.setCurrentMapa(screendata.getWarpMap(warp));
        camera.position.x = screendata.getCameraWarpPos(auxMap, screendata.getCurrentMapa()).x;
        camera.position.y = screendata.getCameraWarpPos(auxMap, screendata.getCurrentMapa()).y;
        pj.setPosx(screendata.getPjWarpPos(auxMap, screendata.getCurrentMapa()).x);
        pj.setPosy(screendata.getPjWarpPos(auxMap, screendata.getCurrentMapa()).y);
        pj.setMapa(screendata.getCurrentMapa());
        setBgMusic(pj.getMapa());
        initScreenData();
      }
    } else {
      if (warp != null) {
        auxMap = screendata.getCurrentMapa();
        screendata.setCurrentMapa(screendata.getWarpMap(warp));
        camera.position.x = screendata.getCameraWarpPos(auxMap, screendata.getCurrentMapa()).x;
        camera.position.y = screendata.getCameraWarpPos(auxMap, screendata.getCurrentMapa()).y;
        pj.setPosx(screendata.getPjWarpPos(auxMap, screendata.getCurrentMapa()).x);
        pj.setPosy(screendata.getPjWarpPos(auxMap, screendata.getCurrentMapa()).y);
        pj.setMapa(screendata.getCurrentMapa());
        setBgMusic(pj.getMapa());
        initScreenData();
      }
    }
  }

  @Override
  public void render(float f) {
    Gdx.gl.glClearColor(0F, 0F, 0F, 0F);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    //we process the user input before we draw
    processUserInput();
    processWarpEnter();
    if (multi && !disableMultiPosGuess) {
      guessSecondPlayerPos();
    }
    processEnemies();
    stateTime += Gdx.graphics.getDeltaTime();

    camera.update();

    batch.setProjectionMatrix(camera.combined);

    batch.begin();

    //Drawing the scene
    for (int i = 0; i < screendata.getBaseLayers(); i++) {
      batch.draw(layers[i], 0, 0);
    }

    if (multi) {
      //drawing user 2
      if (pj2.getCurrentState() != Entidad.Estado.ATT_LEFT && pj2.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
        batch.draw(pj2.getAnimaciones().get(pj2.getCurrentState()).getKeyFrame(stateTime, true), pj2.getPosx(), pj2.getPosy());
      } else {
        batch.draw(pj2.getAnimaciones().get(pj2.getCurrentState()).getKeyFrame(attackTime, true), pj2.getPosx(), pj2.getPosy());
      }
    }

    //drawing user
    if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(stateTime, true), pj.getPosx(), pj.getPosy());
    } else {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(attackTime, true), pj.getPosx(), pj.getPosy());
    }

    //drawing enemies
    for (Enemigo e : enemigos) {
      if (e.getInvTime() > 0) {
        batch.draw(e.getAnimaciones().get(Entidad.Estado.DEAD).getKeyFrame(0, false), e.getPosx(), e.getPosy());
      } else {
        batch.draw(e.getAnimaciones().get(e.getCurrentState()).getKeyFrame(stateTime, true), e.getPosx(), e.getPosy());
      }
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

        shape.setColor(Color.BLUE);
        for (Rectangle r : warpZones) {
          shape.rect(r.x, r.y, r.width, r.height);
        }

        shape.setColor(Color.GREEN);
        Rectangle aux = pj.getColliders().get(pj.getCurrentState());
        shape.rect(aux.x, aux.y, aux.width, aux.height);

        if (multi) {
          shape.setColor(Color.GREEN);
          Rectangle aux2 = pj2.getColliders().get(pj2.getCurrentState());
          shape.rect(aux2.x, aux2.y, aux2.width, aux2.height);
        }

        for (Enemigo e : enemigos) {
          aux = e.getColliders().get(e.getCurrentState());
          shape.setColor(Color.RED);
          shape.rect(aux.x, aux.y, aux.width, aux.height);
          shape.setColor(Color.PURPLE);
          shape.circle(aux.x + aux.getHeight() / 2, aux.y + aux.getHeight() / 2, e.getFollowRange());
        }

        shape.end();
      }
    }
  }

  private int getSyncRandom(double numbers, double speed) {
    int out = (int) (((((System.currentTimeMillis() / (100.0 * speed)) % 10.0) / 10.0)
            * (numbers + 1.0)));
    return out;
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
    shape.dispose();
  }

  private void setBgMusic(int map) {
    if(currentbg != null)
      currentbg.stop();
    currentbg = Assets.getMusic(String.valueOf(map));
    currentbg.setLooping(true);
    currentbg.setVolume(0.3f);
    currentbg.play();
  }
}
