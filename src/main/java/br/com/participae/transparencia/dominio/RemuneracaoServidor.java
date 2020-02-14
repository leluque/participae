package br.com.participae.transparencia.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Esta classe implementa detalhes da remuneracao de servidores publicos.
 *
 * @author Leandro Luque
 * @version 1.0
 * @since fev/2018
 */
@Entity
@Table(name = "_rem_servidor")
public class RemuneracaoServidor extends EntidadeImpl {

	private static final long serialVersionUID = 1L;

	private String referencia;
	private List<DetalheRemuneracao> detalhes = new ArrayList<>();
	private Double totalBruto;
	private Double totalLiquido;
	private Double totalDesconto;
	private Cargo cargo;
	private Servidor servidor;
	private String demonstrativo;

	@Override
	@javax.persistence.Id
	@SequenceGenerator(name = "seq_rem_servidor", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rem_servidor")
	public Long getId() {
		return super.getId();
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "referencia")
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Access(AccessType.PROPERTY)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "cargo")
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Access(AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "remuneracao")
	public List<DetalheRemuneracao> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(List<DetalheRemuneracao> detalhes) {
		this.detalhes = detalhes;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "total_bruto")
	public Double getTotalBruto() {
		return totalBruto;
	}

	public void setTotalBruto(Double salario) {
		this.totalBruto = salario;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "total_liquido")
	public Double getTotalLiquido() {
		return totalLiquido;
	}

	public void setTotalLiquido(Double totalLiquido) {
		this.totalLiquido = totalLiquido;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "total_desconto")
	public Double getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(Double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "servidor")
	public Servidor getServidor() {
		return servidor;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "demonstrativo", columnDefinition = "TEXT")
	public String getDemonstrativo() {
		return demonstrativo;
	}

	public void setDemonstrativo(String demonstrativo) {
		this.demonstrativo = demonstrativo;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public void addDetalhe(DetalheRemuneracao detalheRemuneracao) {
		this.detalhes.add(detalheRemuneracao);
		detalheRemuneracao.setRemuneracao(this);
	}

	public boolean temDetalheComTipo(String tipo) {
		for (DetalheRemuneracao detalheRemuneracao : this.detalhes) {
			if (detalheRemuneracao.getTipo().getNome().equalsIgnoreCase(tipo.trim())) {
				return true;
			}
		}
		return false;
	}

}
