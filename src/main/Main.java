/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.json.JSONException;
import org.json.JSONObject;

import view.VentanaFX;

/**
 *
 * @author camilo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        VentanaFX ventanaFX=new VentanaFX();   
        ventanaFX.launch(VentanaFX.class, args);	
     
    }
    
}
