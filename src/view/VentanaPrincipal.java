/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author camilo
 */
public class VentanaPrincipal extends Application  {

	private ViewDashBoard viewDashBoard;
	private ViewLogin viewLogin;
	@FXML
	private Pane dialogo;	
	@FXML
	private Label testoMensaje;
	@FXML
	private HBox hBoxCuerpo;
	private Thread hilo;
	public VentanaPrincipal(){

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			crearVentana(primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void crearVentana(Stage stage) throws IOException {
		@SuppressWarnings("deprecation")
		URL rutaFxml = new File("src/main/dashboard.fxml").toURL();
		Parent root = FXMLLoader.load(rutaFxml);
	
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Software Valerian");
		stage.show();
		dialogo=(Pane)root.lookup("#dialogo");
		testoMensaje=(Label)root.lookup("#testoMensaje");
		hBoxCuerpo=(HBox)root.lookup("#cuerpo");
		viewDashBoard=new ViewDashBoard(root,dialogo,testoMensaje,hBoxCuerpo);
//		hilo=new Thread(viewDashBoard);
//		hilo.start();
		viewLogin=new ViewLogin(root,viewDashBoard,dialogo,testoMensaje,hBoxCuerpo);


		
	}

	public ViewDashBoard getViewDashBoard() {
		return viewDashBoard;
	}

	public void setViewDashBoard(ViewDashBoard viewDashBoard) {
		this.viewDashBoard = viewDashBoard;
	}

	public ViewLogin getViewLogin() {
		return viewLogin;
	}

	public void setViewLogin(ViewLogin viewLogin) {
		this.viewLogin = viewLogin;
	}

	


}
