/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

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
  String ip;
  Personaje pj;
  public Personaje pj2;
  long timeBetween = (1000 * 3) / 60;

  public SocketDataManager(Personaje aje, String ip) {
    pj = aje;
    this.ip = ip;
    lastInstance = this;
  }

  public void connect() {

    try {
      Socket cs = new Socket(ip, 27827);
      in = new DataInputStream(cs.getInputStream());
      out = new DataOutputStream(cs.getOutputStream());

      main = in.readBoolean();
      sendPlayerInfoInit();
      getPlayerInfoInit();
      do {
        sendPlayerInfo();
        getPlayerInfo();
        Thread.sleep(timeBetween);
        //Thread.sleep(200); //test lag
      } while (true); //TODO stop this

    } catch (IOException ex) {
    } catch (InterruptedException ex) {
    }
  }

  public void sendPlayerInfo() throws IOException {
    out.writeFloat(pj.getPosx());
    out.writeFloat(pj.getPosy());
    out.writeInt(pj.getCurrentState().ordinal());
    out.writeInt(pj.getvDir());
    System.out.println("sent: " + pj.getvDir());
  }

  public void sendPlayerInfoInit() throws IOException {
    out.writeInt(pj.getClase().ordinal());
    out.writeFloat(pj.getPosx());
    out.writeFloat(pj.getPosy());
    out.writeInt(pj.getMapa());
  }

  public void getPlayerInfo() throws IOException {
    synchronized (pj2) {
      pj2.setPosx(in.readFloat());
      pj2.setPosy(in.readFloat());
      pj2.setCurrentState(Entidad.Estado.values()[in.readInt()]);
      pj2.setvDir(in.readInt());
      System.out.println("got: " + pj2.getvDir());
    }
  }

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
}
