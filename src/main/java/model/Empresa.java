package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "geradorId", sequenceName = "empresa_sequence")
@Indexed
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geradorId")
	private Long id;
	
	@Field(index=Index.YES, analyze=Analyze.YES,store=Store.YES)
	private String cnpj;
	
	@Field(index=Index.YES, analyze=Analyze.YES,store=Store.YES)
	private String nomeFantasia;

	private String razaoSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}
