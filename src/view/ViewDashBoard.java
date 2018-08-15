package view;

import java.net.URISyntaxException;

import org.w3c.dom.Document;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import model.Ciudad;
import model.Departamento;
import model.Localizacion;
import model.Mesa;
import model.PuntoVotacion;
import model.Usuario;
import service.UsuarioService;
import util.Registro;

public class ViewDashBoard extends Pane{
	private Parent root;
	
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
	private Registro registro;
	@FXML
	private JFXTextField nombreCompleto;
	@FXML
	private JFXTextField celular;
	@FXML
	private JFXButton btnGRegistraduriaIndividual;

	public ViewDashBoard(Parent root){
		this.root=root;
		init();
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(){
		//objetos campos dashboard
		dashboard=(Pane)root.lookup("#dashboard");
		hBoxContainer=(HBox)root.lookup("#container");
		hBoxRegistradura=(HBox)root.lookup("#registraduria");
		hBoxPersona=(HBox)root.lookup("#persona");
		hBoxCuerpo=(HBox)root.lookup("#cuerpo");
		dialogo=(Pane)root.lookup("#dialogo");
		//objetos campos de login
		
		nombreCompleto=(JFXTextField)root.lookup("#nombreCompleto");
		celular=(JFXTextField)root.lookup("#celular");

		btnGRegistraduriaIndividual=(JFXButton) root.lookup("#btnGRegistraduriaIndividual");
	

		
		

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
	
	public Parent getRoot() {
		return root;
	}
	public void setRoot(Parent root) {
		this.root = root;
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
		if(localizacion != null && localizacion.length > 0){
		    registro.getCampos().put("localizacion",localizacion[0].split("&p=")[1]);	
		    registro.getCampos().put("localizacion",localizacion[1]);
		}
		return registro;
	}
	public WebView getWebView() {
		return webView;
	}
	public void setWebView(WebView webView) {
		this.webView = webView;
	}
	public HBox gethBoxContainer() {
		return hBoxContainer;
	}
	public void sethBoxContainer(HBox hBoxContainer) {
		this.hBoxContainer = hBoxContainer;
	}
	public HBox gethBoxRegistradura() {
		return hBoxRegistradura;
	}
	public void sethBoxRegistradura(HBox hBoxRegistradura) {
		this.hBoxRegistradura = hBoxRegistradura;
	}
	public HBox gethBoxPersona() {
		return hBoxPersona;
	}
	public void sethBoxPersona(HBox hBoxPersona) {
		this.hBoxPersona = hBoxPersona;
	}
	public HBox gethBoxCuerpo() {
		return hBoxCuerpo;
	}
	public void sethBoxCuerpo(HBox hBoxCuerpo) {
		this.hBoxCuerpo = hBoxCuerpo;
	}
	public Pane getDialogo() {
		return dialogo;
	}
	public void setDialogo(Pane dialogo) {
		this.dialogo = dialogo;
	}
	public Pane getDashboard() {
		return dashboard;
	}
	public void setDashboard(Pane dashboard) {
		this.dashboard = dashboard;
	}
	public Registro getRegistro() {
		return registro;
	}
	public void setRegistro(Registro registro) {
		this.registro = registro;
	}
	public JFXTextField getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(JFXTextField nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public JFXTextField getCelular() {
		return celular;
	}
	public void setCelular(JFXTextField celular) {
		this.celular = celular;
	}
	public JFXButton getBtnGRegistraduriaIndividual() {
		return btnGRegistraduriaIndividual;
	}
	public void setBtnGRegistraduriaIndividual(JFXButton btnGRegistraduriaIndividual) {
		this.btnGRegistraduriaIndividual = btnGRegistraduriaIndividual;
	}
	
	
}
