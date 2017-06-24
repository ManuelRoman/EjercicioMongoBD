package ap1MongoDB.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;

import ap1MongoDB.beans.BeanFotograma;
import ap1MongoDB.dao.BeanDaoImpl;

@MultipartConfig
public class InsertarFotogramas extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Fuente de datos.
	 */
	private String BD;
	
	/**
	 * Objeto encapsula toda la información a nivel de aplicación.
	 */
	private ServletContext sc;
	
	public InsertarFotogramas(){
		super();
	}
	
	/**
	 * Inicializa el servlet, y le proporciona un objeto, ServletConfig con
	 * información de nivel de aplicación sobre el contexto de datos que rodea
	 * al servlet en el contenedor web.
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// Imprescindible llamar a super.init(config) para tener acceso a la
		// configuración del servlet a nivel de contenedor web.
		super.init(config);
		// En este punto se procedería a obtener las referencias a las fuentes
		// de datos de la aplicación.
		sc = config.getServletContext();
		BD = sc.getInitParameter("nombreBD");
		sc.setAttribute("BD", BD);
		sc.setAttribute("appOperativa", "true");
		if (this.BD == null){
			System.out.println("la base de datos es null.");
			sc.setAttribute("appOperativa", "false");
		}
	}
	
	/**
	 * Prepara el servlet para su eliminación.
	 */
	public void destroy() {
		// Elimina el datasource del ámbito de aplicación, liberando todos los
		// recursos que tuviera asignados.
		sc.removeAttribute("BD");
		sc.removeAttribute("appOperativa");
		// Elimina el ámbito de aplicación.
		sc = null;
	}

	/**
	 * Procesamiento de la petición en modo GET. Se reenvía al método doPost()
	 * @param request Petición
	 * @param response Respuesta
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	/**
	 * Procesamiento de la petición en modo POST
	 * @param request Petición
	 * @param response Respuesta
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Petición para guardar un fotograma.");
		if(sc.getAttribute("appOperativa").equals("true")){
			boolean estado = true;
			String json = null;
			System.out.println("Base de datos correcta: " + BD);
			Part filePart = request.getPart("archivo");
			//Obtenemos el nombre del archivo
			String nombreArchivo = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream is = filePart.getInputStream();
			//Establecemos la ruta y nombre archivo donde vamos a guardarlo
			String archivoFinal = this.getServletContext().getRealPath("fotogramas/"+nombreArchivo);
			FileOutputStream os = null;
            try {
            	os = new FileOutputStream (archivoFinal);
            	// Copiamos del archivo subido al definitivo
            	int ch = is.read();
            	while (ch != -1) {
            		os.write(ch);
            		ch = is.read();
            	}
            } catch (FileNotFoundException e){
            	estado = false;
				e.printStackTrace();
            } catch (IOException e){
            	estado = false;
				e.printStackTrace();
            } catch (Exception e) {
				estado = false;
				e.printStackTrace();
			} finally {
				os.close();
			}
			BeanFotograma beanFotograma = new BeanFotograma(nombreArchivo, request.getParameter("titPelicula"), Integer.parseInt(request.getParameter("estreno")), request.getParameter("nomDirector"), request.getParameter("genero"));
			System.out.println(beanFotograma);
			BeanDaoImpl beanDaoImpl = null;
			try {
				beanDaoImpl = new BeanDaoImpl("localhost", 27017);
				beanDaoImpl.getConexion(BD);
				beanDaoImpl.insertFotograma(beanFotograma);	
			} catch (MongoSocketOpenException e) {
				estado = false;
				e.printStackTrace();
			} catch (MongoException e) {
				estado = false;
				e.printStackTrace();
			} catch (Exception e) {
				estado = false;
				e.printStackTrace();
			} finally {
				try {
					beanDaoImpl.closeConexion();
				} catch (MongoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			if (estado){
				json = new Gson().toJson("Fotograma guardado.");
			} else {
				json = new Gson().toJson("Se ha producido un error, intentelo más tarde.");
			}
			out.write(json);
			out.close();
			System.out.println("json a enviar: "+json.toString());
		} else{
			System.out.println("Hay un problema con la base datos.");
		}
	}
}
