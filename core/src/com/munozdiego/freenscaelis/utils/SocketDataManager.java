/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import com.munozdiego.freenscaelis.MyGame;
import com.munozdiego.freenscaelis.models.Entidad;
import com.munozdiego.freenscaelis.models.Personaje;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author diego
 */
public class SocketDataManager extends Thread {

  public static SocketDataManager lastInstance;
  DataOutputStream out;
  DataInputStream in;
  public boolean main;
  private boolean running;
  String ip;
  Personaje pj;
  public Socket cs;
  public Personaje pj2;
  long timeBetween = (1000 * 5) / 60;

  public SocketDataManager(Personaje aje, String ip) {
    pj = aje;
    this.ip = ip;
    running = true;
    lastInstance = this;
  }

  public void connect() {

    try {
      cs = new Socket(ip, 27827);
      in = new DataInputStream(cs.getInputStream());
      out = new DataOutputStream(cs.getOutputStream());

      main = in.readBoolean();
      sendPlayerInfoInit();
      getPlayerInfoInit();
      do {
        sendPlayerInfo();
        getPlayerInfo();
        Thread.sleep(timeBetween);
        System.out.println("getting data through socket...");
        //Thread.sleep(200); //test lag
      } while (running); //TODO stop this

    } catch (IOException ex) {
    } catch (InterruptedException ex) {
    }
  }

  /**
   * Method that sends the player information
   * @throws IOException 
   */
  public void sendPlayerInfo() throws IOException {
    out.writeFloat(pj.getPosx());
    out.writeFloat(pj.getPosy());
    out.writeInt(pj.getCurrentState().ordinal());
    out.writeInt(pj.getvDir());
  }

  /**
   * Method that sends the initial player information
   * @throws IOException 
   */
  public void sendPlayerInfoInit() throws IOException {
    out.writeInt(pj.getClase().ordinal());
    out.writeFloat(pj.getPosx());
    out.writeFloat(pj.getPosy());
    out.writeInt(pj.getMapa());
  }

  /**
   * Method that retrieves the 2nd user's info
   * @throws IOException 
   */
  public void getPlayerInfo() throws IOException {
      pj2.setPosx(in.readFloat());
      pj2.setPosy(in.readFloat());
      pj2.setCurrentState(Entidad.Estado.values()[in.readInt()]);
      pj2.setvDir(in.readInt());
  }
  /**
   * * Method that retrieves the initial 2nd user's info
   * @throws IOException 
   */
  public void getPlayerInfoInit() throws IOException {
    pj2 = new Personaje();
    pj2.setClase(Personaje.Clase.values()[in.readInt()]);
    pj2.setPosx(in.readFloat());
    pj2.setPosy(in.readFloat());
    pj2.setMapa(in.readInt());
    synchronized (lastInstance) {
      lastInstance.notifyAll();
    }
  }

  @Override
  public void run() {
    connect();
  }
  
  @Override
  public void interrupt(){
    running = false;
    try {
      in.close();
      out.close();
      cs.close();
    } catch (IOException ex) {
      if(MyGame.DEBUG_MODE)
        System.out.println("InterruptError: " + ex);
    }
    super.interrupt();
  }
}
