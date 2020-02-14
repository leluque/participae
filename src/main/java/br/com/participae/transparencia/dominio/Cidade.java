package br.com.participae.transparencia.dominio;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "_cidade")
public class Cidade extends EntidadeNomeada implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sigla;
	private String uf;

	@Override
	@javax.persistence.Id
	@SequenceGenerator(name = "seq_cidade", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cidade")
	public Long getId() {
		return super.getId();
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "sigla")
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "uf")
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}