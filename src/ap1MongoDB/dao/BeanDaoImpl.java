package ap1MongoDB.dao;

import java.util.ArrayList;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import ap1MongoDB.beans.BeanFotograma;
import ap1MongoDB.beans.ListaFotogramas;

public class BeanDaoImpl implements BeanDao{
	
	private MongoClient mongoClient;
	
	private MongoDatabase db;
	
	public BeanDaoImpl(String host, Integer puerto) throws Exception, MongoException, MongoSocketOpenException{
		this.mongoClient = new MongoClient(host, puerto);
	}

	@Override
	public void getConexion(String db) throws Exception, MongoException, MongoSocketOpenException{
		this.db = mongoClient.getDatabase(db);	
	}
	
	@Override
	public void closeConexion() throws Exception, MongoException{
		this.mongoClient.close();
	}

	@Override
	public ListaFotogramas getTodosFotogramas() throws Exception, MongoException{
		ListaFotogramas listaFotogramas = new ListaFotogramas();
		MongoCollection<Document> collection = db.getCollection("fotogramas");
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				BeanFotograma beanFotograma = BeanFotograma.toBeanFotograma(doc);
				listaFotogramas.add(beanFotograma);
			}
		} finally {
			cursor.close();
		}
		return listaFotogramas;
	}

	@Override
	public ArrayList<String> getDirectores() throws Exception, MongoException{
		ArrayList<String> listaDirectores = new ArrayList<String>();
		MongoCollection<Document> collection = db.getCollection("fotogramas");
	    MongoCursor<String> cursor = collection.distinct("director", String.class).iterator();
	    try {
	    	while(cursor.hasNext()) {
	    		listaDirectores.add(cursor.next());
	    	}
	    } finally {
				cursor.close();
			}
	    /*for(int x=0;x<listaDirectores.size();x++) {
	        System.out.println(listaDirectores.get(x));
	      }*/
		return listaDirectores;
	}

	@Override
	public ListaFotogramas getFotDirector(String director) throws Exception, MongoException{
		ListaFotogramas listaFotogramas = new ListaFotogramas();
		MongoCollection<Document> collection = db.getCollection("fotogramas");
		Document findQuery = new Document("director", director);
		MongoCursor<Document> cursor = collection.find(findQuery).iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				BeanFotograma beanFotograma = BeanFotograma.toBeanFotograma(doc);
				listaFotogramas.add(beanFotograma);
			}
		} finally {
			cursor.close();
		}
		return listaFotogramas;
	}

	@Override
	public void insertFotograma(BeanFotograma beanFotograma) throws Exception, MongoException{
		MongoCollection<Document> collection = db.getCollection("fotogramas");
		collection.insertOne(beanFotograma.toDocumentBeanFotograma());
	}

}
