/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 *
 * @author diego
 */
public class Assets {
    
    public static Texture texture_back;
    public static Sprite sprite_back;
    
    public static BitmapFont font;
    
    public static void load(){
        texture_back = new Texture(Gdx.files.internal("images/bg-re.png"));
        texture_back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite_back = new Sprite(texture_back);
        sprite_back.flip(false, true);
        
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 32;
        
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelart.otf"));
        font = gen.generateFont(param);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.setColor(Color.WHITE);
        font.getData().setScale(1, -1);
    }
}
