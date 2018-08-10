/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Usuario;
import service.UsuarioService;

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
	@FXML
	private HBox hBoxCuerpo;
	@FXML
	private Pane dialogo;	
	@FXML
	private Pane dashboard;	
	@FXML
	private VBox login;
	@FXML
	private JFXTextField correo;
	@FXML
	private JFXPasswordField contrasena;

	@FXML
	private JFXButton btnIngresar;


	private Usuario authUser;
	
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
		stage.setTitle("Software Valerian");
		stage.show();

		//objetos campos dashboard
		hBoxContainer=(HBox)root.lookup("#container");
		hBoxRegistradura=(HBox)root.lookup("#registraduria");
		hBoxPersona=(HBox)root.lookup("#persona");
		hBoxCuerpo=(HBox)root.lookup("#cuerpo");
		dialogo=(Pane)root.lookup("#dialogo");
		//objetos campos de login
		correo=(JFXTextField)root.lookup("#correo");
		contrasena=(JFXPasswordField) root.lookup("#contrasena");
		btnIngresar=(JFXButton) root.lookup("#btnIngresar");
		login=(VBox)root.lookup("#login");
		dashboard=(Pane)root.lookup("#dashboard");

		btnIngresar.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				UsuarioService usuarioService=new UsuarioService();
				try {
					Registro registro=usuarioService.sesion(new Usuario(correo.getText().toString(),contrasena.getText().toString()));
					if(registro!=null && (boolean)registro.getCampos().get("success")) {
						JSONObject json=new JSONObject(registro.getCampos().get("usuario"));
						authUser=new Usuario(json);
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
		hBoxRegistradura.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
//				hBoxContainer.setVisible(true);
				hBoxCuerpo.setVisible(true);
//				dialogo.setVisible(true);
				hBoxCuerpo.toFront();
			}
		});

		hBoxPersona.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				hBoxContainer.setVisible(false);
				dialogo.setVisible(false);
				hBoxCuerpo.setVisible(false);
			}
		});

	}

}
