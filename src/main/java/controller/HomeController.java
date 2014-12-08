package controller;

import java.util.List;

import model.Empresa;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import dao.PseudoDao;

@Resource
public class HomeController {
	
	private final PseudoDao pseudoDao;
	
	public HomeController(PseudoDao pseudoDao) {
		this.pseudoDao = pseudoDao;
	}	
	
	@Path("/")
	public void home(){		
		this.pseudoDao.inserir();
		this.pseudoDao.indexarDados();
		List<Empresa> empresas = this.pseudoDao.buscar("JPA");
		
		for(Empresa e : empresas){
			System.out.println("CNPJ: " + e.getCnpj());
			System.out.println("Nome Fantasia: " + e.getNomeFantasia());
		}
	}

}
