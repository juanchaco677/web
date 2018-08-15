package view;

import java.net.URISyntaxException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import model.Usuario;
import service.UsuarioService;
import util.Registro;

public class ViewLogin {
	private Usuario authUser;
	private ViewDashBoard viewDashBoard;
	private Parent root;
	@FXML
	private VBox login;	
	@FXML
	private JFXTextField correo;
	@FXML
	private JFXPasswordField contrasena;

	@FXML
	private JFXButton btnIngresar;	
	
	public ViewLogin(Parent root,ViewDashBoard viewDashBoard){
		
		this.root=root;
		this.viewDashBoard=viewDashBoard;
		init();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(){
		login=(VBox)root.lookup("#login");
		correo=(JFXTextField)root.lookup("#correo");
		contrasena=(JFXPasswordField) root.lookup("#contrasena");
		btnIngresar=(JFXButton) root.lookup("#btnIngresar");
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
							authUser.setToken(registro.getCampos().get("token").toString());
							login.setVisible(false);
							viewDashBoard.getDashboard().setVisible(true);
							viewDashBoard.setAuthUser(authUser);
						}
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
	public Parent getRoot() {
		return root;
	}
	public void setRoot(Parent root) {
		this.root = root;
	}

	public Usuario getAuthUser() {
		return authUser;
	}

	public void setAuthUser(Usuario authUser) {
		this.authUser = authUser;
	}

	public ViewDashBoard getViewDashBoard() {
		return viewDashBoard;
	}

	public void setViewDashBoard(ViewDashBoard viewDashBoard) {
		this.viewDashBoard = viewDashBoard;
	}

	public VBox getLogin() {
		return login;
	}

	public void setLogin(VBox login) {
		this.login = login;
	}

	public JFXTextField getCorreo() {
		return correo;
	}

	public void setCorreo(JFXTextField correo) {
		this.correo = correo;
	}

	public JFXPasswordField getContrasena() {
		return contrasena;
	}

	public void setContrasena(JFXPasswordField contrasena) {
		this.contrasena = contrasena;
	}

	public JFXButton getBtnIngresar() {
		return btnIngresar;
	}

	public void setBtnIngresar(JFXButton btnIngresar) {
		this.btnIngresar = btnIngresar;
	}
	
	
	
}
