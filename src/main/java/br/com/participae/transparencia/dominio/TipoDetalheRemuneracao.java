package br.com.participae.transparencia.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Esta classe implementa tipos de detalhes de remuneração de servidores
 * municipais.
 * 
 * @author Leandro Luque
 * @since fev/2018
 */
@Entity
@Table(name = "_tipo_det_rem")
public class TipoDetalheRemuneracao extends EntidadeNomeada implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	@javax.persistence.Id
	@SequenceGenerator(name = "seq_tipo_det_rem", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_det_rem")
	public Long getId() {
		return super.getId();
	}

}