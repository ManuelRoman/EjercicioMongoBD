package ap1MongoDB.beans;

import java.util.ArrayList;

import org.bson.Document;

/**
 * Encapsula el concepto de fotograma
 */
public class BeanFotograma {

	/**
	 * Información del nombre del archivo
	 */
	private String archivo;

	/**
	 * Información del título de la película
	 */
	private String titulo;

	/**
	 * Información del año de la película
	 */
	private Integer year;

	/**
	 * Información del nombre del director
	 */
	private String director;

	/**
	 * Información del género de la película
	 */
	private String genero;

	/**
	 * Constructor sin parámetros
	 */
	public BeanFotograma() {
		this.archivo = "";
		this.titulo = "";
		this.year = 0;
		this.director = "";
		this.genero = "";
	}

	/**
	 * Constructor con parámetros
	 * @param archivo
	 * @param titulo
	 * @param year
	 * @param director
	 * @param genero
	 */
	public BeanFotograma(String archivo, String titulo, Integer year, String director, String genero) {
		this.archivo = archivo;
		this.titulo = titulo;
		this.year = year;
		this.director = director;
		this.genero = genero;
	}
	
	public BeanFotograma(String titulo, Integer year, String director, String genero) {
		this.archivo = "";
		this.titulo = titulo;
		this.year = year;
		this.director = director;
		this.genero = genero;
	}
	
	public String toString(){
		return "Datos del fotograma: " + this.titulo + this.year + this.director + this.genero + this.archivo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Document toDocumentBeanFotograma() {
		Document documentBeanFotograma = new Document();		
		documentBeanFotograma.append("archivo", this.archivo);
		documentBeanFotograma.append("titPelicula", this.titulo);
		documentBeanFotograma.append("anyoEstreno", this.year);
		documentBeanFotograma.append("director", this.director);
		documentBeanFotograma.append("genero", this.genero);		
		return documentBeanFotograma;
	}
	
	public static BeanFotograma toBeanFotograma(Document doc) {
		BeanFotograma beanFotograma = null;
		if (doc!=null)
			beanFotograma = new BeanFotograma(doc.getString("archivo"),doc.getString("titPelicula"),doc.getInteger("anyoEstreno"),doc.getString("director"),doc.getString("genero"));
		return beanFotograma;
	}
}
