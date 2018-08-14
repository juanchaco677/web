/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.w3c.dom.Document;

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
import model.Ciudad;
import model.Departamento;
import model.Localizacion;
import model.Mesa;
import model.PuntoVotacion;
import model.Usuario;
import service.UsuarioService;
import util.Registro;

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
	@FXML
	private JFXButton btnGRegistraduriaIndividual;
	private Registro registro;

	private Usuario authUser;
	@FXML
	private JFXTextField nombreCompleto;
	@FXML
	private JFXTextField celular;

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
		nombreCompleto=(JFXTextField)root.lookup("#nombreCompleto");
		celular=(JFXTextField)root.lookup("#celular");
		btnIngresar=(JFXButton) root.lookup("#btnIngresar");
		btnGRegistraduriaIndividual=(JFXButton) root.lookup("#btnGRegistraduriaIndividual");
		login=(VBox)root.lookup("#login");
		dashboard=(Pane)root.lookup("#dashboard");

		btnIngresar.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				UsuarioService usuarioService=new UsuarioService();
				try {
					Registro registro=usuarioService.sesion(new Usuario(correo.getText().toString(),contrasena.getText().toString()));
					if(registro!=null) {
						Registro registroUsuario=usuarioService.getUsuario(registro.getCampos().get("token").toString());
						if(registroUsuario!=null){
							authUser=new Usuario(registroUsuario);
							login.setVisible(false);
							dashboard.setVisible(true);
						}
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		btnGRegistraduriaIndividual.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				registro=capturarDatosRegistraduria();

				if(registro !=null && registro.getCampos() !=null){
					UsuarioService usuarioService=new UsuarioService();
					Departamento departamento=new Departamento(registro.getCampos().get("departamento").toString());
					Ciudad ciudad=new Ciudad(registro.getCampos().get("ciudad").toString(),departamento);
					Localizacion localizacion=new Localizacion(new Double(registro.getCampos().get("latitud").toString()),new Double(registro.getCampos().get("longitud").toString()), registro.getCampos().get("direccion").toString(), ciudad);
					PuntoVotacion punto =new PuntoVotacion(registro.getCampos().get("puesto").toString(), localizacion);
					Mesa mesa=new Mesa(Integer.parseInt(registro.getCampos().get("mesa").toString()), punto);
					Usuario usuario=new Usuario(nombreCompleto.getText(), celular.getText(), mesa);
					try {
						usuarioService.crearUsuario(usuario);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					registro=null;
				}
			
			}
		});

		hBoxRegistradura.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {

			}
		});

		hBoxPersona.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {

			}
		});

	}

	private Registro capturarDatosRegistraduria(){
		Registro registro=new Registro();
		Document document=webView.getEngine().getDocument();
		registro.getCampos().put("cedula",document.getElementsByTagName("td").item(0).getTextContent());
		registro.getCampos().put("departamento",document.getElementsByTagName("td").item(1).getTextContent());
		registro.getCampos().put("ciudad",document.getElementsByTagName("td").item(2).getTextContent());
		registro.getCampos().put("puesto",document.getElementsByTagName("td").item(3).getTextContent());
		registro.getCampos().put("direccion",document.getElementsByTagName("td").item(4).getTextContent());
		registro.getCampos().put("mesa",document.getElementsByTagName("td").item(5).getTextContent());
		String [] localizacion=document.getElementsByTagName("td").item(7).getTextContent().split(",");
		if(localizacion != null && !localizacion.length > 0){
		    registro.getCampos().put("localizacion",localizacion[4].split("&p=")[1]);	
		    registro.getCampos().put("localizacion",localizacion[5]);
		}
		return registro;
	}


}
