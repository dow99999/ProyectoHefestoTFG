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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author diego
 */
public class Assets {
    
    private static Map<String, BitmapFont> fonts;
    
    public static void init(){
        fonts = new HashMap<>();
    }
    

    /**
     * Funcion que devuelve una animacion
     * @param path String directorio donde buscar
     * @param filename String prefijo del set de imagenes de la animacion
     * @param num int numero de fotogramas
     * @param ext String extension de cada fotograma (debe ser la misma para todos los fotogramas)
     * @param space float tiempo entre fotogramas
     * @param flip boolean si hay que voltear la imagen horizontalmente o no
     * @return Animation<TextureRegion> la animacion
     */
    public static Animation<TextureRegion> getAnimation(String path, String filename, int num, String ext, float space, boolean flip){
      TextureRegion[] tr = new TextureRegion[num];
      Sprite auxs;
      
      for(int i = 0; i < num; i++){
        auxs = new Sprite(new Texture(Gdx.files.internal(path + filename + "_" + i + "." + ext)));
        auxs.flip(flip, true);
        tr[i] = new TextureRegion(auxs);
      }
      
      return new Animation<>(space, tr);
    }
    
    /**
     * Funcion que devuelve un sprite a partir de su direccion
     * @param path Strig direccion en la que se encuentra la imagen
     * @return Sprite el sprite de la imagen
     */
    public static Sprite getSprite(String path){
        Texture auxt;
        Sprite auxs;
        
        auxt = new Texture(Gdx.files.internal(path));
        auxt.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        auxs = new Sprite(auxt);
        auxs.flip(false, true);
        
        return auxs;
    }
    
    /**
     * Metodo para anadir una nueva fuente a las fuentes disponibles en assets
     * 
     * @param name String nombre de la fuente con la que se identificara
     * @param size int amano de la fuente
     * @param color Color color de la letra
     * @param path String directorio dentro de los assets del proyecto en el que esta 
     * el archivo de fuente(.ttf, .otf)
     */
    public static void addFont(String name, int size, Color color, String path){
        BitmapFont aux;
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = size;
        
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        aux = gen.generateFont(param);
        aux.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        aux.setColor(color);
        aux.getData().setScale(1, -1);
        
        fonts.put(name, aux);
    }
    
    /**
     * Funcion para recuperar una fuente de la lista de fuentes
     * 
     * @param name String nombre de la fuente
     * @return BitmapFont la fuente
     */
    public static BitmapFont getFont(String name){
        return fonts.get(name);
    }
}
