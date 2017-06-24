package ap1MongoDB.dao;

import java.util.ArrayList;

import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;

import ap1MongoDB.beans.BeanFotograma;
import ap1MongoDB.beans.ListaFotogramas;

public interface BeanDao {
	
	public void getConexion(String BD) throws Exception, MongoException, MongoSocketOpenException;
	
	public void closeConexion() throws Exception, MongoException;
	
	public ListaFotogramas getTodosFotogramas() throws Exception, MongoException;
	
	public ArrayList<String> getDirectores() throws Exception, MongoException;
	
	public ListaFotogramas getFotDirector(String director) throws Exception, MongoException;
	
	public void insertFotograma(BeanFotograma beanFotograma) throws Exception, MongoException;

}
