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

/**
 *
 * @author diego
 */
public class PuebloInicialScreen implements Screen {

  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;
  ShapeRenderer shape;

  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fontb;

  Sprite sprite_back;
  Sprite sprite_muralla_delante;
  Sprite sprite_edificios;
  Sprite sprite_muralla_detras;
  Sprite sprite_carteles;

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

  final Rectangle[] colliders = new Rectangle[]{
    new Rectangle(384, 661, 670, 730),
    new Rectangle(1056, 661, 239, 490),
    new Rectangle(1440, 661, 239, 490),
    new Rectangle(1679, 661, 623, 730),
    new Rectangle(577, 1656, 478, 552),
    new Rectangle(1055, 1909, 241, 297),
    new Rectangle(1441, 1909, 241, 297),
    new Rectangle(1681, 1656, 429, 552),
    new Rectangle(383, 1535, 94, 901),
    new Rectangle(383, 2436, 911, 58),
    new Rectangle(1440, 2436, 863, 58),
    new Rectangle(2208, 1535, 94, 901),
    new Rectangle(1214, 0, 77, 680),
    new Rectangle(1439, 0, 80, 666),
    new Rectangle(2304, 1325, 334, 66),
    new Rectangle(2304, 1538, 334, 80),
    new Rectangle(1440, 2495, 65, 383),
    new Rectangle(1220, 2496, 76, 383),
    new Rectangle(0, 1316, 385, 76),
    new Rectangle(0, 1536, 385, 66)
  };

  final Rectangle[] warpZones = new Rectangle[]{
    new Rectangle(1296, 0, 415, 61), //bosque norte
    new Rectangle(2578, 1391, 60, 144), //bosque este
    new Rectangle(1295, 2821, 145, 57), //bosque sur
    new Rectangle(0, 1391, 95, 145) //playa
  };

  public PuebloInicialScreen(MyGame g) {
    m_game = g;
    camera = new OrthographicCamera();
    camera.setToOrtho(true, 1920, 1080);
    shape = new ShapeRenderer();

    batch = new SpriteBatch();
    fontw = Assets.getFont("pixel32w");
    fontb = Assets.getFont("pixel32b");

    layout = new GlyphLayout();

    userdata = UserData.getInstance();

    colliderdebug = false;

    sprite_back = Assets.getSprite("maps/pueblo_principal/ciudad_principal_base.png");
    sprite_muralla_delante = Assets.getSprite("maps/pueblo_principal/ciudad_principal_muralla_delante.png");
    sprite_edificios = Assets.getSprite("maps/pueblo_principal/ciudad_principal_edificios.png");
    sprite_muralla_detras = Assets.getSprite("maps/pueblo_principal/ciudad_principal_muralla_detras.png");
    sprite_carteles = Assets.getSprite("maps/pueblo_principal/ciudad_principal_carteles.png");

    /*
    //setting the boxes where the user will click
    for (int i = texts.length - 1; i >= 0; --i) {
      boxes[i] = new Rectangle();
    }

    layout.setText(fontw, texts[0]);
    boxes[0].set(50, 50, layout.width, layout.height * -1);
    layout.setText(fontw, texts[1]);
    boxes[1].set(50, 50 + 35, layout.width, layout.height * -1);

    for (int i = 0; i < 6; i++) {
      layout.setText(fontw, texts[i + 2]);
      boxes[i + 2].set(100, 750 + 35 * i, layout.width, layout.height * -1);
    }

    sprite_back = Assets.getSprite("images/bg-re.png");
    
    pj = new Personaje();
    //Sprites/Player/Sword/Defence0/Player_Idle_Sword_Defence0_0.png
    pj.getAnimaciones().put(Entidad.Estado.IDLE_RIGHT, Assets.getAnimation("Sprites/Player/Sword/Defence1/", "Player_Walk_Sword_Defence1", 4, "png", 0.17f, false));
    pj.getAnimaciones().put(Entidad.Estado.IDLE_LEFT, Assets.getAnimation("Sprites/Player/Sword/Defence1/", "Player_Walk_Sword_Defence1", 4, "png", 0.17f, true));
    stateTime = 0.1f;
    pj.setPosx(100);
    pj.setPosy(100);

    pja = new Personaje();
    //Sprites/Player/Sword/Defence0/Player_Idle_Sword_Defence0_0.png
    pja.getAnimaciones().put(Entidad.Estado.IDLE_RIGHT, Assets.getAnimation("Sprites/Player/Sword/Defence0/", "Player_Walk_Sword_Defence0", 4, "png", 0.17f, false));
    pja.getAnimaciones().put(Entidad.Estado.IDLE_LEFT, Assets.getAnimation("Sprites/Player/Sword/Defence0/", "Player_Walk_Sword_Defence0", 4, "png", 0.17f, true));
    //stateTime = 0.1f;
    pja.setPosx(100);
    pja.setPosy(100);
     */
  }

  @Override
  public void show() {
    pj = userdata.getCurrentCharacter();
    pj.init(pj.getClase(), 0);

    camera.position.x = sprite_back.getWidth() / 2;
    camera.position.y = sprite_back.getHeight() / 2;

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
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState()))) {
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
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState()))) {
          pj.setPosy(lastPos);
        }
        moving = true;
      }

      if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        pj.setCurrentState(Entidad.Estado.RUN_RIGHT);
        lastPos = pj.getPosx();
        pj.setPosx(pj.getPosx() + pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState()))) {
          pj.setPosx(lastPos);
        }
        moving = true;
      }

      if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        pj.setCurrentState(Entidad.Estado.RUN_LEFT);
        lastPos = pj.getPosx();
        pj.setPosx(pj.getPosx() - pj.getSpeed() * Gdx.graphics.getDeltaTime() * 60);
        if (ColliderUtils.checkCollitions(colliders, pj.getColliders().get(pj.getCurrentState()))) {
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

    //TODO camera lock when outside the map
    float camx = camera.position.x + (pj.getPosx() + pj.getCenterX() - camera.position.x) * Gdx.graphics.getDeltaTime();
    float camy = camera.position.y + (pj.getPosy() + pj.getCenterY() - camera.position.y) * Gdx.graphics.getDeltaTime();
    int cameraBounds = ColliderUtils.cameraOutside(camx - camera.viewportWidth / 2, camy - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight, sprite_back.getWidth(), sprite_back.getHeight());

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
    batch.draw(sprite_back, 0, 0);
    batch.draw(sprite_muralla_detras, 0, 0);
    batch.draw(sprite_edificios, 0, 0);

    if (pj.getCurrentState() != Entidad.Estado.ATT_LEFT && pj.getCurrentState() != Entidad.Estado.ATT_RIGHT) {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(stateTime, true), pj.getPosx(), pj.getPosy());
    } else {
      batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(attackTime, true), pj.getPosx(), pj.getPosy());
    }

    batch.draw(sprite_muralla_delante, 0, 0);
    batch.draw(sprite_carteles, 0, 0);

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
    sprite_back.getTexture().dispose();
  }
}
