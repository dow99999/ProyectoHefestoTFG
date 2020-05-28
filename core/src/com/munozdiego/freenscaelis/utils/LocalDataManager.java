/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munozdiego.freenscaelis.utils;

import com.munozdiego.freenscaelis.models.POJO.PersonajeXML;
import com.munozdiego.freenscaelis.models.Personaje;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diego
 */
@XmlRootElement(name = "freenscaelis")
@XmlAccessorType(XmlAccessType.NONE)
public class LocalDataManager implements Serializable {

  public static LocalDataManager instance;

  public static LocalDataManager getInstance() {
    if (instance == null) {
      instance = new LocalDataManager();
    }

    return instance;
  }

  @XmlElementWrapper(name = "characters")
  @XmlElement(name = "character")
  private PersonajeXML[] personajes;

  public LocalDataManager() {

  }

  /**
   * Method to save the user's characters
   * @param pjs Personaje[] the user's characters
   */
  public void savePlayerData(Personaje[] pjs) {
    personajes = new PersonajeXML[pjs.length];

    for (int i = 0; i < personajes.length; i++) {
      personajes[i] = new PersonajeXML(pjs[i]);
    }

    try {
      JAXBContext context = JAXBContext.newInstance(LocalDataManager.class);
      Marshaller mar = context.createMarshaller();
      mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      mar.marshal(this, new FileWriter("data/gamedata.dat"));
    } catch (JAXBException ex) {
      System.out.println("j1" + ex);
    } catch (IOException ex) {
      System.out.println("io1" + ex);
    }
  }

  /**
   * Method to retrieve the user's characters
   * @return Personaje[] the user's characters
   */
  public Personaje[] retrievePlayerData() {
    Personaje[] aux = null;

    try {
      JAXBContext context = JAXBContext.newInstance(LocalDataManager.class);
      Unmarshaller mar = context.createUnmarshaller();
      personajes = ((LocalDataManager) mar.unmarshal(new FileInputStream("data/gamedata.dat"))).personajes;
      aux = new Personaje[personajes.length];
    } catch (JAXBException ex) {
      System.out.println("j2" + ex);
    } catch (FileNotFoundException ex) {
      System.out.println("io2" + ex);
      aux = new Personaje[3]; //si no existe el fichero devolveremos un array vacio
    }

    if(personajes != null){
      for (int i = 0; i < aux.length; i++) {
        aux[i] = personajes[i].getPersonaje();
      }
    }

    return aux;
  }
}
