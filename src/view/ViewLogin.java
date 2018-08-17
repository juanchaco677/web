package view;

import java.net.URISyntaxException;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Usuario;
import service.UsuarioService;
import util.Registro;
import util.ValerianUtil;

public class ViewLogin {
	private Usuario authUser;
	private ViewDashBoard viewDashBoard;
	private Parent root;
	@FXML
	private Label testoMensaje;
	@FXML
	private VBox login;	
	@FXML
	private JFXTextField correo;
	@FXML
	private JFXPasswordField contrasena;
	@FXML
	private Pane dialogo;	
	@FXML	
	private JFXButton btnIngresar;	
	@FXML
	private HBox hBoxCuerpo;
	
	public ViewLogin(Parent root,ViewDashBoard viewDashBoard,Pane dialogo,Label testoMensaje,HBox hBoxCuerpo){
		this.hBoxCuerpo=hBoxCuerpo;
		this.testoMensaje=testoMensaje;
		this.dialogo=dialogo;
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
				login.toBack();
				hBoxCuerpo.toBack();
				dialogo.toFront();
				hBoxCuerpo.setVisible(true);
				UsuarioService usuarioService=new UsuarioService();
				try {
					Registro registro=usuarioService.sesion(new Usuario(correo.getText().toString(),contrasena.getText().toString()));
					
					dialogo.setVisible(true);
					
					if(registro!=null && registro.getCampos() !=null) {
						if(!ValerianUtil.validarRegistro(registro, "token")) {
							Registro registroUsuario=usuarioService.getUsuario(registro.getCampos().get("token").toString());			
							
							if(registroUsuario!=null){
								registroUsuario=ValerianUtil.convertirARegistro(registroUsuario.getCampos().get("data").toString());
								authUser=new Usuario(registroUsuario);
								authUser.setToken(registro.getCampos().get("token").toString());
								login.setVisible(false);
								viewDashBoard.getDashboard().setVisible(true);
								viewDashBoard.setAuthUser(authUser);					
								testoMensaje.setText("Las credenciales del usuario son correctas.");						
							}else {	
								hBoxCuerpo.setVisible(false);
								testoMensaje.setText("Intente nuevamente ingresar las credenciales.");						
							}
						}else {
							hBoxCuerpo.setVisible(false);
							testoMensaje.setText("Intente nuevamente ingresar las credenciales.");
						}
						desaparecerDialogo(3000);
					}
				} catch (URISyntaxException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
	private void desaparecerDialogo(int segundos) {
		try {
			Thread.sleep(segundos);
			dialogo.setVisible(false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
