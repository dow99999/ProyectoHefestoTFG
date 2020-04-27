/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author diego
 */
class GameScreen implements Screen{

    MyGame m_game;
    OrthographicCamera camera;
    SpriteBatch batch;
    
    public GameScreen(MyGame g){
        m_game = g;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1920, 1080);
        
        batch = new SpriteBatch();
        
    }
    
    @Override
    public void show() {
        
    }

    public void processUserInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(-5, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(5, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(0, 5);

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(0, -5);
            
        if(MyGame.DEBUG_MODE)
            System.out.println("CameraPointing(" + camera.position.x + ", " + camera.position.y + ")");
    }
    
    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        processUserInput();
        
        camera.update();
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        batch.draw(Assets.sprite_back, 0, 0);
        
        Assets.font.draw(batch, String.valueOf(System.currentTimeMillis() / 1000), 100, 900);
        
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
        
    }
    
}
