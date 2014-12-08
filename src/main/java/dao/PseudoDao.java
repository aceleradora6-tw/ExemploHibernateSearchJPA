package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Empresa;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
@Component
@SessionScoped
public class PseudoDao {
	
	private static EntityManagerFactory factory;
	private EntityManager manager;
	
	public PseudoDao(){
		if(factory == null){
			factory = Persistence
					.createEntityManagerFactory("empresa");			
		}
		
		manager = factory.createEntityManager();
	}

	public void inserir() {
		Empresa e = new Empresa();
		e.setCnpj("1234748");
		e.setNomeFantasia("Persistencia via JPA.");
		e.setRazaoSocial("Persistência via JPA.");
		
		if(!manager.isOpen()){
			manager = factory.createEntityManager();
		}
		
		manager.getTransaction().begin();
		manager.persist(e);
		manager.getTransaction().commit();
		System.out.println("Empresa inserida.");
		System.out.println("Empresa ID: " + e.getId());

		manager.close();
	}
	
	public void indexarDados(){
		
		if(!manager.isOpen()){
			manager = factory.createEntityManager();
		}
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(manager);
		try{
			fullTextEntityManager.createIndexer().startAndWait();
		}catch(InterruptedException ex){
			System.out.println("Erro ao indexar: "+ ex.getMessage());
		}
		
		manager.close();
		
		System.out.println("Indexação completada.");
	}
	
	public List<Empresa> buscar(String busca){
		if(!manager.isOpen()){
			manager = factory.createEntityManager();
		}
		
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(manager);
		manager.getTransaction().begin();
		
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Empresa.class).get();
		org.apache.lucene.search.Query luceneQuery = qb
				.keyword()
				.onField("nomeFantasia")
				.matching(busca)
				.createQuery();
		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Empresa.class);
		
		List<Empresa> resultadoBusca = jpaQuery.getResultList();
				
		manager.getTransaction().commit();
		manager.close();
		
		return resultadoBusca;
	}
}
