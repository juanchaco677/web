/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import view.VentanaPrincipal;

/**
 *
 * @author camilo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("static-access")
	public static void main(String[] args) {
        // TODO code application logic here
        
        VentanaPrincipal ventanaFX=new VentanaPrincipal();   
        ventanaFX.launch(VentanaPrincipal.class, args);	
     
    }
    
}
