package br.com.participae.transparencia.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "_newsletter")
public class Newsletter extends EntidadeNomeada implements Serializable {

	private static final long serialVersionUID = 1L;

	public Newsletter() {
	}

	public Newsletter(String name) {
		super(name);
	}

	@Override
	@javax.persistence.Id
	@SequenceGenerator(name = "seq_newsletter", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_newsletter")
	public Long getId() {
		return super.getId();
	}

}