package ap1MongoDB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;

import ap1MongoDB.beans.ListaFotogramas;
import ap1MongoDB.dao.BeanDaoImpl;

public class ListarFotogramas extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Fuente de datos.
	 */
	private String BD;
	
	/**
	 * Objeto encapsula toda la información a nivel de aplicación.
	 */
	private ServletContext sc;
	
	public ListarFotogramas(){
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
		System.out.println("Petición para ver todos los fotogramas.");
		if(sc.getAttribute("appOperativa").equals("true")){
			System.out.println("Base de datos correcta: " + BD);
			boolean estado = true;
			String json = null;
			BeanDaoImpl beanDaoImpl = null;
			ListaFotogramas listaFotogramas = new ListaFotogramas();
			try {
				beanDaoImpl = new BeanDaoImpl("localhost", 27017);
				beanDaoImpl.getConexion(BD);
				listaFotogramas = beanDaoImpl.getTodosFotogramas();
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
			if(estado) {
				json = new Gson().toJson(listaFotogramas);
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
