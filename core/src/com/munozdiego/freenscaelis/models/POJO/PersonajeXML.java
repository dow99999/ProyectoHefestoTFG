/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models.POJO;

import com.munozdiego.freenscaelis.models.Personaje;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diego
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class PersonajeXML implements Serializable {

  @XmlElement()
  String name;
  @XmlElement()
  Personaje.Clase clase;
  @XmlElementWrapper(name = "stats")
  @XmlElement(name = "stat")
  int[] stats;
  @XmlElementWrapper(name = "inventory")
  @XmlElement(name = "item")
  ItemXML[] inventory;
  @XmlElement()
  int posx;
  @XmlElement()
  int posy;
  @XmlElement()
  int map;

  public Personaje getPersonaje() {
    Personaje aux = new Personaje();

    aux.setName(name);
    aux.setClase(clase);
    aux.setStats(stats);
    aux.setInventario(ItemXML.parseItemXMLArray(inventory));
    aux.setPosx(posx);
    aux.setPosy(posx);
    //TODO mapa

    return name == null ? null : aux;
  }

  public PersonajeXML() {

  }

  public PersonajeXML(Personaje p) {
    if (p != null) {
      name = p.getName();
      clase = p.getClase();
      stats = p.getStats();
      inventory = ItemXML.parseItemArray(p.getInventario());
      posx = (int) p.getPosx();
      posy = (int) p.getPosy();
      //TODO Map
    }
  }

  public PersonajeXML(String name, Personaje.Clase clase, int[] stats, ItemXML[] inventory, int posx, int posy, int map) {
    this.name = name;
    this.clase = clase;
    this.stats = stats;
    this.inventory = inventory;
    this.posx = posx;
    this.posy = posy;
    this.map = map;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Personaje.Clase getClase() {
    return clase;
  }

  public void setClase(Personaje.Clase clase) {
    this.clase = clase;
  }

  public int[] getStats() {
    return stats;
  }

  public void setStats(int[] stats) {
    this.stats = stats;
  }

  public ItemXML[] getInventory() {
    return inventory;
  }

  public void setInventory(ItemXML[] inventory) {
    this.inventory = inventory;
  }

  public int getPosx() {
    return posx;
  }

  public void setPosx(int posx) {
    this.posx = posx;
  }

  public int getPosy() {
    return posy;
  }

  public void setPosy(int posy) {
    this.posy = posy;
  }

  public int getMap() {
    return map;
  }

  public void setMap(int map) {
    this.map = map;
  }

}
