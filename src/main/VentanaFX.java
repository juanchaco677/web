/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author camilo
 */
public class VentanaFX extends Application  {
	@FXML
	private WebView webView;
	@FXML
	private HBox hBoxContainer;
	@FXML
	private HBox hBoxRegistradura;
	@FXML
	private HBox hBoxPersona;
	public VentanaFX(){

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			crearVentana(primaryStage,"https://wsp.registraduria.gov.co/censo/consultar/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void crearVentana(Stage stage,String url) throws IOException {
		@SuppressWarnings("deprecation")
		URL rutaFxml = new File("src/main/dashboard.fxml").toURL();
		Parent root = FXMLLoader.load(rutaFxml);
		webView=(WebView)root.lookup("#web");
		if(webView != null){
			webView.getEngine().load(url);
		}else{
			System.out.println("NULL");
		}
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("DASHBOARD");
//		stage.setFullScreen(true);
		stage.setMaximized(true);
		stage.show();
	
		hBoxContainer=(HBox)root.lookup("#container");
		hBoxRegistradura=(HBox)root.lookup("#registraduria");
		hBoxPersona=(HBox)root.lookup("#persona");

		hBoxContainer.setPrefWidth(stage.getWidth()-145);
		
		hBoxRegistradura.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				hBoxContainer.setVisible(true);
			}
		});

		hBoxPersona.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				hBoxContainer.setVisible(false);
			}
		});

	}

}
