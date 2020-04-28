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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.munozdiego.freenscaelis.Assets;
import com.munozdiego.freenscaelis.DatabaseDataManager;
import com.munozdiego.freenscaelis.MyGame;

/**
 *
 * @author diego
 */
public class MenuScreen implements Screen {

    MyGame m_game;
    OrthographicCamera camera;
    SpriteBatch batch;

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
    final Rectangle[] boxes = new Rectangle[texts.length];

    public MenuScreen(MyGame g) {
        m_game = g;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1920, 1080);
        
        batch = new SpriteBatch();
        fontw = Assets.getFont("pixel32w");
        fontb = Assets.getFont("pixel32b");

        GlyphLayout layout = new GlyphLayout();

        for (int i = texts.length - 1; i >= 0; --i) {
            boxes[i] = new Rectangle();
        }

        layout.setText(fontw, texts[0]);
        boxes[0].set(50, 50, layout.width, layout.height*-1);
        layout.setText(fontw, texts[1]);
        boxes[1].set(50, 50 + 35, layout.width, layout.height*-1);

        for (int i = 0; i < 6; i++) {
            layout.setText(fontw, texts[i + 2]);
            boxes[i + 2].set(100, 750 + 35 * i, layout.width, layout.height*-1);
        }
        
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(MyGame.DEBUG_MODE)
                    System.out.println("Touch(" + screenX + "," + screenY + ")");
                
                if (boxes[0].contains(screenX, screenY)) {
                    System.out.println(texts[0]);
                } else {
                    if (boxes[1].contains(screenX, screenY)) {
                        System.out.println(texts[1]);

                    } else {
                        if (boxes[2].contains(screenX, screenY)) {
                            System.out.println(texts[2]);

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
        
        sprite_back = Assets.getSprite("images/bg-re.png");
    }

    @Override
    public void show() {

    }

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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (MyGame.DEBUG_MODE) {
            //System.out.println("CameraPointing(" + camera.position.x + ", " + camera.position.y + ")");
        }
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        processUserInput();

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();

        batch.draw(sprite_back, 0, 0);

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
        float ratiox = new Float(Math.sqrt((float)i/(float)MyGame.WIDTH));
        float ratioy = new Float(Math.sqrt((float)i1/(float)MyGame.HEIGHT));
        Rectangle aux;
        
        if(MyGame.DEBUG_MODE)
            System.out.println("i: " + i + ", il: " + i1 + "ratio(" + ratiox + "," + ratioy + ")");
        
        for(int x = 0; x < boxes.length; x++){
            aux = boxes[x];
            aux.set(aux.x*ratiox,
                    aux.y*ratioy,
                    aux.width*ratiox,
                    aux.height*ratioy);
            if(MyGame.DEBUG_MODE){
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
