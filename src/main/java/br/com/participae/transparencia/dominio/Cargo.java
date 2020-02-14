package br.com.participae.transparencia.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Esta classe implementa cargos de servidores municipais.
 * 
 * @author Leandro Luque
 * @since fev/2018
 */
@Entity
@Table(name = "_cargo")
public class Cargo extends EntidadeNomeada implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	@javax.persistence.Id
	@SequenceGenerator(name = "seq_cargo", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cargo")
	public Long getId() {
		return super.getId();
	}

}