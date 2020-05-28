/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.models.POJO;

import com.munozdiego.freenscaelis.models.Item;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class NOT implemented in the aplication
 * @author diego
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ItemXML implements Serializable {

  @XmlElement
  int id;
  @XmlElement
  String name;
  @XmlElement
  int att;
  @XmlElement
  int def;

  public static ItemXML[] parseItemArray(Item[] items) {
    ItemXML[] aux = new ItemXML[items.length];

    for (int i = 0; i < aux.length; i++) {
      aux[i] = new ItemXML(items[i]);
    }

    return aux;
  }

  public static Item[] parseItemXMLArray(ItemXML[] items) {
    Item[] aux = new Item[items == null ? 3 : items.length];

    if(items != null){ 
      for (int i = 0; i < aux.length; i++) {
        if(items[i] != null)
          aux[i] = items[i].getItem();
      }
    }

    return aux;
  }

  public Item getItem() {
    Item aux = new Item();

    aux.setId(id);
    aux.setName(name);
    aux.setAtt(att);
    aux.setDef(def);

    return aux;
  }

  public ItemXML() {

  }

  public ItemXML(Item i) {
    if (i != null) {
      id = i.getId();
      name = i.getName();
      att = i.getAtt();
      def = i.getDef();
    }
  }

  public ItemXML(int id, String name, int att, int def) {
    this.id = id;
    this.name = name;
    this.att = att;
    this.def = def;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAtt() {
    return att;
  }

  public void setAtt(int att) {
    this.att = att;
  }

  public int getDef() {
    return def;
  }

  public void setDef(int def) {
    this.def = def;
  }

}
