/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.munozdiego.freenscaelis.Assets;
import com.munozdiego.freenscaelis.MyGame;

/**
 *
 * @author diego
 */
public class LoginScreen implements Screen{

    MyGame m_game;
    OrthographicCamera camera;
    SpriteBatch batch;

    BitmapFont fontw;
    BitmapFont fontb;

    Sprite sprite_back;
    
    public LoginScreen(MyGame g){
        m_game = g;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1920, 1080);
        
        batch = new SpriteBatch();
        fontw = Assets.getFont("pixel32w");
        fontb = Assets.getFont("pixel32b");
        
        sprite_back = Assets.getSprite("images/bg-blur.png");
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button){
                //TODO
                return true;
            }
        });
        
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        
        batch.draw(sprite_back, 0, 0);
        
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
        
    }

    @Override
    public void dispose() {
        
    }
    
}
