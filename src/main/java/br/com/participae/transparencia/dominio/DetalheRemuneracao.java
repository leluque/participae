package br.com.participae.transparencia.dominio;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Esta classe implementa detalhes de remuneracao de servidores municipais.
 *
 * @author Leandro Luque
 * @version 1.0
 * @since fev/2018
 */
@Entity
@Table(name = "_detalhe_rem")
public class DetalheRemuneracao extends EntidadeImpl {

	private static final long serialVersionUID = 1L;

	private Double valor;
	private TipoDetalheRemuneracao tipo;

	public enum Natureza {
		RENDIMENTO, DESCONTO, OUTRO
	};

	private Natureza natureza;
	private RemuneracaoServidor remuneracao;

	@Access(AccessType.PROPERTY)
	@Column(name = "valor")
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Access(AccessType.PROPERTY)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo")
	public TipoDetalheRemuneracao getTipo() {
		return tipo;
	}

	public void setTipo(TipoDetalheRemuneracao tipo) {
		this.tipo = tipo;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "natureza")
	@Enumerated(EnumType.STRING)
	public Natureza getNatureza() {
		return natureza;
	}

	public void setNatureza(Natureza natureza) {
		this.natureza = natureza;
	}

	@Access(AccessType.PROPERTY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "remuneracao")
	public RemuneracaoServidor getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(RemuneracaoServidor remuneracao) {
		this.remuneracao = remuneracao;
	}

}