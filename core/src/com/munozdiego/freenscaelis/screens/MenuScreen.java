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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.utils.Assets;
import com.munozdiego.freenscaelis.utils.DatabaseDataManager;
import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.models.Entidad;
import com.munozdiego.freenscaelis.models.Personaje;

/**
 *
 * @author diego
 */
public class MenuScreen implements Screen {

  MyGame m_game;
  OrthographicCamera camera;
  SpriteBatch batch;

  //the fonts we'll use
  BitmapFont fontw;
  BitmapFont fontb;

  Sprite sprite_back;

  final String[] texts = new String[]{
    "Log in",
    "Register",
    "One player",
    "Two players",
    "Player vs player",
    "Configuration",
    "Credits",
    "Exit"
  };
  
  //used to compute the dimensions of the text
  GlyphLayout layout;
  
  final Rectangle[] boxes = new Rectangle[texts.length];
  
  Personaje pj;
  Personaje pja;
  float stateTime;
  
  public MenuScreen(MyGame g) {
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
          m_game.setScreen(m_game.screens.get("login"));
        } else {
          if (boxes[1].contains(screenX, screenY)) {
            System.out.println(texts[1]);
            m_game.setScreen(m_game.screens.get("register"));
          } else {
            if (boxes[2].contains(screenX, screenY)) {
              System.out.println(texts[2]);
              m_game.setScreen(m_game.screens.get("player-select"));
            } else {
              if (boxes[3].contains(screenX, screenY)) {
                System.out.println(texts[3]);
              } else {
                if (boxes[4].contains(screenX, screenY)) {
                  System.out.println(texts[4]);

                } else {
                  if (boxes[5].contains(screenX, screenY)) {
                    System.out.println(texts[5]);

                  } else {
                    if (boxes[6].contains(screenX, screenY)) {
                      System.out.println(texts[6]);

                    } else {
                      if (boxes[7].contains(screenX, screenY)) {
                        System.out.println(texts[7]);
                        Gdx.app.exit();
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
    //movement player
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      pj.setPosy(pj.getPosy() - 5 * Gdx.graphics.getDeltaTime() * 60);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      pj.setPosy(pj.getPosy() + 5 * Gdx.graphics.getDeltaTime() * 60);
      
    }

    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      pj.setCurrentState(Entidad.Estado.IDLE_RIGHT);
      pj.setPosx(pj.getPosx() + 5 * Gdx.graphics.getDeltaTime() * 60);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      pj.setCurrentState(Entidad.Estado.IDLE_LEFT);
      pj.setPosx(pj.getPosx() - 5 * Gdx.graphics.getDeltaTime() * 60);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit();
    }
    
    if (Gdx.input.isKeyPressed(Input.Keys.K)) {
      camera.position.set(
            camera.position.x + (pja.getPosx() - camera.position.x) * Gdx.graphics.getDeltaTime(),
            camera.position.y + (pja.getPosy() - camera.position.y) * Gdx.graphics.getDeltaTime(),
            0);
    } else {
      camera.position.set(
              camera.position.x + (pj.getPosx() - camera.position.x) * Gdx.graphics.getDeltaTime(),
              camera.position.y + (pj.getPosy() - camera.position.y) * Gdx.graphics.getDeltaTime(),
              0);
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

    batch.draw(sprite_back, 0, 0);

    batch.draw(pj.getAnimaciones().get(pj.getCurrentState()).getKeyFrame(stateTime, true), pj.getPosx(), pj.getPosy());
    batch.draw(pja.getAnimaciones().get(pja.getCurrentState()).getKeyFrame(stateTime, true), pja.getPosx(), pja.getPosy());

    layout.setText(fontw, DatabaseDataManager.getInstance().getUser());
    fontw.draw(batch, DatabaseDataManager.getInstance().getUser(), MyGame.WIDTH - 50 - layout.width, 50);

    fontw.draw(batch, texts[0], 50, 50);
    fontw.draw(batch, texts[1], 50, 50 + 35 * 1);

    for (int i = 0; i < 6; i++) {
      fontw.draw(batch, texts[i + 2], 100, 750 + 35 * i);
    }

    batch.end();
  }

  @Override
  public void resize(int i, int i1) {
    //la formula de abajo se ha conseguido haciendo pruebas hasta que funcionara...
    float ratiox = new Float(Math.sqrt((float) i / (float) MyGame.WIDTH));
    float ratioy = new Float(Math.sqrt((float) i1 / (float) MyGame.HEIGHT));
    Rectangle aux;

    if (MyGame.DEBUG_MODE) {
      System.out.println("i: " + i + ", il: " + i1 + "ratio(" + ratiox + "," + ratioy + ")");
    }

    //when resizing the window we have to change the collider boxes' dimensions
    for (int x = 0; x < boxes.length; x++) {
      aux = boxes[x];
      aux.set(aux.x * ratiox,
              aux.y * ratioy,
              aux.width * ratiox,
              aux.height * ratioy);
      if (MyGame.DEBUG_MODE) {
        System.out.println("box " + x + ":" + aux.x + "," + aux.y + "," + aux.width + "," + aux.height);
      }
    }

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
