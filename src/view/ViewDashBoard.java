package view;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import model.Ciudad;
import model.Departamento;
import model.Localizacion;
import model.Mesa;
import model.PuntoVotacion;
import model.Usuario;
import service.UsuarioService;
import util.Registro;
import util.Request;
import util.ValerianUtil;

public class ViewDashBoard extends Pane {
	private Parent root;

	@FXML
	private WebView webView;
	@FXML
	private Label testoMensaje;
	@FXML
	private HBox hBoxContainer;
	@FXML
	private HBox hBoxRegistradura;
	@FXML
	private HBox hBoxCuerpo;
	@FXML
	private HBox hBoxMasivo;
	@FXML
	private VBox formMasivo;
	@FXML
	private VBox formRegistraduria;	
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
	@FXML
	private JFXButton btnIniciarMasivo;	
	@FXML
	private JFXButton btnPararMasivo;	
	private Thread backgroundThread;

	private Usuario authUser;
	private static final String URL="https://wsp.registraduria.gov.co/censo/consultar/";
	public ViewDashBoard(Parent root,Pane dialogo,Label testoMensaje,HBox hBoxCuerpo){
		this.hBoxCuerpo=hBoxCuerpo;
		this.testoMensaje=testoMensaje;
		this.dialogo=dialogo;
		this.root=root;
		init();

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(){
		//objetos campos dashboard
		dashboard=(Pane)root.lookup("#dashboard");
		hBoxContainer=(HBox)root.lookup("#container");
		hBoxRegistradura=(HBox)root.lookup("#registraduria");
		hBoxMasivo=(HBox)root.lookup("#masivo");
		formRegistraduria=(VBox)root.lookup("#formRegistraduria");
		formMasivo=(VBox)root.lookup("#formMasivo");
		//objetos campos de login
		nombreCompleto=(JFXTextField)root.lookup("#nombreCompleto");
		celular=(JFXTextField)root.lookup("#celular");
		btnGRegistraduriaIndividual=(JFXButton) root.lookup("#btnGRegistraduriaIndividual");		
		btnIniciarMasivo=(JFXButton) root.lookup("#btnIniciarMasivo");
		btnPararMasivo=(JFXButton) root.lookup("#btnPararMasivo");

		webView=(WebView)root.lookup("#web");
		if(webView != null){
			webView.getEngine().load(URL);
		}else{
			System.out.println("NULL");
		}

		btnGRegistraduriaIndividual.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				webView.getEngine().executeScript("document.forms['form'].submit()");
				hBoxCuerpo.setVisible(true);
				
				registro=capturarDatosRegistraduria("");
				if(registro !=null && registro.getCampos() !=null){

					Departamento departamento=new Departamento(registro.getCampos().get("departamento").toString());
					Ciudad ciudad=new Ciudad(registro.getCampos().get("ciudad").toString(),departamento);
					Localizacion localizacion=new Localizacion(new Double(registro.getCampos().get("latitud").toString()),new Double(registro.getCampos().get("longitud").toString()), registro.getCampos().get("direccion").toString(), ciudad);
					PuntoVotacion punto =new PuntoVotacion(registro.getCampos().get("puesto").toString(), localizacion);
					Mesa mesa=new Mesa(Integer.parseInt(registro.getCampos().get("mesa").toString()), punto);
					Usuario usuario=new Usuario(nombreCompleto.getText(), celular.getText(), mesa);
					usuario.setReferido(authUser !=null?authUser.getReferido():null);
					usuario.setCandidato(authUser !=null?authUser.getCandidato():null);
					usuario.setCedula(registro.getCampos().get("cedula").toString());
					enviarDatosRegistraduria(usuario,1);
				}
			}
		});

		btnIniciarMasivo.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				startTask();
			}

		});
		btnPararMasivo.setOnMouseClicked(new EventHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void handle(Event arg0) {
				if(backgroundThread != null) {
					backgroundThread.suspend();
				
				}
			}

		});

		//cambio por menu
		hBoxRegistradura.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				formMasivo.setVisible(false);
				formRegistraduria.setVisible(true);
			}
		});

		hBoxMasivo.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {				
				formRegistraduria.setVisible(false);
				formMasivo.setVisible(true);
				//@
			}
		});
	}

	private void enviarDatosRegistraduria(Usuario usuario,int opcion) {

		UsuarioService usuarioService=new UsuarioService();
		try {
			Registro registro=null;
			if(opcion==1) {
				registro=usuarioService.crearUsuario(usuario,authUser!=null?authUser.getToken():"");
			}else {
				registro=usuarioService.actualizarUsuario(usuario,authUser!=null?authUser.getToken():"");
			}
			if(registro !=null && registro.getCampos()!=null){
				String mensaje=registro.getCampos().get("data").toString();
				hBoxCuerpo.setVisible(false);
				dialogo.setVisible(true);
				if(Boolean.valueOf(registro.getCampos().get("success").toString())){
					hBoxCuerpo.setVisible(false);							
					testoMensaje.setText(mensaje);
					webView.getEngine().executeScript("document.getElementById(\"nuip\").value = \"\";");
					registro=null;								
				}else{							
					testoMensaje.setText(mensaje);
					registro=null;							
				}
				desaparecerDialogo(3000);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		registro=null;

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


	private Registro capturarDatosRegistraduria(String cedula){
		Registro registro=new Registro();
		Document document=webView.getEngine().getDocument();
		if(document.getElementsByTagName("td").getLength()>0){
	
			registro.getCampos().put("cedula","".equals(cedula)?document.getElementsByTagName("td").item(0).getTextContent():cedula);
			registro.getCampos().put("departamento",document.getElementsByTagName("td").item(1).getTextContent());
			registro.getCampos().put("ciudad",document.getElementsByTagName("td").item(2).getTextContent());
			registro.getCampos().put("puesto",document.getElementsByTagName("td").item(3).getTextContent());
			registro.getCampos().put("direccion",document.getElementsByTagName("td").item(4).getTextContent());
			registro.getCampos().put("mesa",document.getElementsByTagName("td").item(5).getTextContent());
			Element hrefA=null;
			if(document.getElementsByTagName("td").item(7)!=null){
				hrefA=(Element)document.getElementsByTagName("td").item(7).getChildNodes().item(0);
				String [] localizacion=hrefA.getAttribute("href").split(",");		
				if(localizacion != null && localizacion.length > 0){
					registro.getCampos().put("longitud",localizacion[4].split("&p=")[1]);	
					registro.getCampos().put("latitud",localizacion[5]);
					return registro;
				}
			}else{
				try {
					Map<String,Object>parametros=new HashMap<>();	
					String address=registro.getCampos().get("ciudad").toString().replace("D.C.","")
							.replace(".","").trim().toLowerCase()+" "+registro.getCampos().get("puesto").toString().toLowerCase()
							+" "+
							registro.getCampos().get("direccion").toString();
					parametros.put("address", address);
					parametros.put("key", "AIzaSyAEYzgkz7vRYKNIINybhqWsxDUF2ZkUbNc");
					Registro registroAux=Request.getGoogle("https://maps.googleapis.com/maps/api/geocode/json", parametros);
					if(registroAux !=null && "OK".equals(registroAux.getCampos().get("status").toString())){						
						String cadena=registroAux.getCampos().get("results").toString();
						String resultado=encontrarCadena(cadena,"\"formatted_address\": \"","\"types\":").replace(":","");
						registro.getCampos().put("direccion",resultado.replace("\",","").replace("\"","").replace("}","").trim());
						resultado=encontrarCadena(cadena,"\"lng\":","\"lat\":");
						registro.getCampos().put("longitud",resultado.replace(",","").trim());
						resultado=encontrarCadena(cadena,"\"lat\":","\"northeast\"");						
						registro.getCampos().put("latitud",resultado.replace("},", "").trim());
						return registro;
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return null;	
	}
	private String encontrarCadena(String cadena,String cadenaInicial, String cadenaFinal) {		
		int numInicial=cadena.indexOf(cadenaInicial)+cadenaInicial.length();
		int numFinal=cadena.indexOf(cadenaFinal);		
		return cadena.substring(numInicial,numFinal).trim();
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
		return hBoxMasivo;
	}
	public void sethBoxPersona(HBox hBoxPersona) {
		this.hBoxMasivo = hBoxPersona;
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
	public Usuario getAuthUser() {
		return authUser;
	}
	public void setAuthUser(Usuario authUser) {
		this.authUser = authUser;
	}

	public Parent getRoot() {
		return root;
	}
	public void setRoot(Parent root) {
		this.root = root;
	}
	/**
	 * ejecuta la tarea programa de masivo
	 */
	private void tareaProgramadaMasivo() {
		// TODO Auto-generated method stub
		UsuarioService usuarioService=new UsuarioService();
		try {
			System.out.println(authUser.getType());
			System.out.println("metodo tareprogramada"+authUser.getType());
			int idCandidato="S".equals(authUser.getType())?authUser.getId():authUser.getCandidato().getId();
			List<Registro> listaUsuario=usuarioService.consultaMasivoPersona(10, authUser.getToken(),idCandidato);		
		

			for (Registro registroUsuario : listaUsuario) {
				
				webView.getEngine().executeScript("document.getElementById(\"nuip\").value='"+registroUsuario.getCampos().get("nit").toString()+"';");
				webView.getEngine().executeScript("document.forms['form'].submit()");
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				registro=capturarDatosRegistraduria(registroUsuario.getCampos().get("nit").toString());
				Departamento departamento=new Departamento(registro.getCampos().get("departamento").toString());
				Ciudad ciudad=new Ciudad(registro.getCampos().get("ciudad").toString(),departamento);
				Localizacion localizacion=new Localizacion(new Double(registro.getCampos().get("latitud").toString()),new Double(registro.getCampos().get("longitud").toString()), registro.getCampos().get("direccion").toString(), ciudad);
				PuntoVotacion punto =new PuntoVotacion(registro.getCampos().get("puesto").toString(), localizacion);
				Mesa mesa=new Mesa(Integer.parseInt(registro.getCampos().get("mesa").toString()), punto);
				Usuario usuario=new Usuario(nombreCompleto.getText(), celular.getText(), mesa);
				Usuario referido=new Usuario(ValerianUtil.validarRegistro(registroUsuario, "id_referido")?0:Integer.parseInt(registroUsuario.getCampos().get("id_referido").toString()));
				Usuario candidato=authUser.getCandidato();
				usuario.setReferido(referido);
				usuario.setCandidato(candidato);
				usuario.setCedula(registro.getCampos().get("cedula").toString());
				enviarDatosRegistraduria(usuario,0);

			}


		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startTask()

	{
		// Create a Runnable
		Runnable task = new Runnable()
		{

			public void run()

			{			
				tareaProgramadaMasivo();				
			}

		};
		// Run the task in a background thread
		backgroundThread = new Thread(task);
		// Terminate the running thread if the application eits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();

	}


}
