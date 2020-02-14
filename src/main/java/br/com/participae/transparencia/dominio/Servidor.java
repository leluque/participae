package br.com.participae.transparencia.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.participae.transparencia.dominio.DetalheRemuneracao.Natureza;
import br.com.participae.transparencia.to.MovimentacaoServidor;
import br.com.participae.transparencia.to.NovosRegistros;

/**
 * Esta classe implementa servidores municipais.
 * 
 * @author Leandro Luque
 * @since fev/2018
 */
@Entity
@Table(name = "_servidor")
public class Servidor extends EntidadeNomeada implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private List<RemuneracaoServidor> remuneracoes = new ArrayList<>();
	private Cidade cidade;
	private TipoServidor tipo;
	private String tipoAdicional;

	@Override
	@javax.persistence.Id
	@SequenceGenerator(name = "seq_servidor", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servidor")
	public Long getId() {
		return super.getId();
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Access(AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "servidor")
	public List<RemuneracaoServidor> getRemuneracoes() {
		return remuneracoes;
	}

	public void setRemuneracoes(List<RemuneracaoServidor> remuneracoes) {
		this.remuneracoes = remuneracoes;
	}

	public RemuneracaoServidor getRemuneracao(String referencia) {
		for (RemuneracaoServidor remuneracaoServidor : this.remuneracoes) {
			if (remuneracaoServidor.getReferencia().equalsIgnoreCase(referencia.trim())) {
				return remuneracaoServidor;
			}
		}
		return null;
	}

	@Access(AccessType.PROPERTY)
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	public TipoServidor getTipo() {
		return tipo;
	}

	public void setTipo(TipoServidor tipo) {
		this.tipo = tipo;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "tipo_adicional")
	public String getTipoAdicional() {
		return tipoAdicional;
	}

	public void setTipoAdicional(String tipoAdicional) {
		this.tipoAdicional = tipoAdicional;
	}

	public void addRemuneracao(RemuneracaoServidor remuneracaoServidor) {
		this.remuneracoes.add(remuneracaoServidor);
		remuneracaoServidor.setServidor(this);
	}

	public NovosRegistros preencherCom(Map<String, Cargo> cargos, Map<String, TipoDetalheRemuneracao> tipos,
			br.com.participae.transparencia.to.RemuneracaoServidor remuneracaoServidor) {
		NovosRegistros novosRegistros = new NovosRegistros();

		RemuneracaoServidor novaRemuneracao = getRemuneracao(remuneracaoServidor.getReferencia());
		if (novaRemuneracao == null) {
			novaRemuneracao = new RemuneracaoServidor();
			this.addRemuneracao(novaRemuneracao);
		}

		novaRemuneracao.setReferencia(remuneracaoServidor.getReferencia());
		novaRemuneracao.setServidor(this);
		novaRemuneracao.setTotalBruto(remuneracaoServidor.getTotalBruto());
		novaRemuneracao.setTotalLiquido(remuneracaoServidor.getTotalLiquido());
		novaRemuneracao.setTotalDesconto(remuneracaoServidor.getTotalDesconto());
		novaRemuneracao.setHabilitado(true);
		novaRemuneracao.setUltimaAtualizacao(new Date());

		String nomeCargo = remuneracaoServidor.getCargo().trim().toUpperCase();
		Cargo cargo = cargos.get(nomeCargo);
		if (cargo == null) {
			cargo = new Cargo();
			cargo.setNome(nomeCargo);
			cargo.setDataCriacao(new Date());
			cargo.setUltimaAtualizacao(new Date());
			cargo.setHabilitado(true);
			novosRegistros.addCargo(cargo);
		}
		novaRemuneracao.setCargo(cargo);

		// Preenche os detalhes.
		for (MovimentacaoServidor rendimento : remuneracaoServidor.getRendimentos()) {
			// System.out.println("Vou passar por um rendimento");
			if (!novaRemuneracao.temDetalheComTipo(rendimento.getNome())) {
				// System.out.println(" adicionando...");
				TipoDetalheRemuneracao tipo = getTipo(tipos, rendimento.getNome(), novosRegistros);
				DetalheRemuneracao detalheRemuneracao = new DetalheRemuneracao();
				detalheRemuneracao.setDataCriacao(new Date());
				detalheRemuneracao.setTipo(tipo);
				detalheRemuneracao.setNatureza(Natureza.RENDIMENTO);
				detalheRemuneracao
						.setValor(Double.valueOf(rendimento.getValor().trim().replace(".", "").replace(",", ".")));
				novaRemuneracao.addDetalhe(detalheRemuneracao);
			} else {
				// System.out.println(" Já tem.");
			}
		}

		for (MovimentacaoServidor desconto : remuneracaoServidor.getDescontos()) {
			// System.out.println("Vou passar por um desconto");
			if (!novaRemuneracao.temDetalheComTipo(desconto.getNome())) {
				// System.out.println(" adicionando...");
				TipoDetalheRemuneracao tipo = getTipo(tipos, desconto.getNome(), novosRegistros);
				DetalheRemuneracao detalheRemuneracao = new DetalheRemuneracao();
				detalheRemuneracao.setDataCriacao(new Date());
				detalheRemuneracao.setTipo(tipo);
				detalheRemuneracao.setNatureza(Natureza.DESCONTO);
				detalheRemuneracao
						.setValor(Double.valueOf(desconto.getValor().trim().replace(".", "").replace(",", ".")));
				novaRemuneracao.addDetalhe(detalheRemuneracao);
			} else {
				// System.out.println(" Já tem.");
			}
		}

		for (MovimentacaoServidor outro : remuneracaoServidor.getOutros()) {
			if (!novaRemuneracao.temDetalheComTipo(outro.getNome())) {
				TipoDetalheRemuneracao tipo = getTipo(tipos, outro.getNome(), novosRegistros);
				DetalheRemuneracao detalheRemuneracao = new DetalheRemuneracao();
				detalheRemuneracao.setDataCriacao(new Date());
				detalheRemuneracao.setTipo(tipo);
				detalheRemuneracao.setNatureza(Natureza.OUTRO);
				detalheRemuneracao.setValor(Double.valueOf(outro.getValor().trim().replace(".", "").replace(",", ".")));
				novaRemuneracao.addDetalhe(detalheRemuneracao);
			}
		}

		novosRegistros.setRemuneracao(novaRemuneracao);

		return novosRegistros;
	}

	private TipoDetalheRemuneracao getTipo(Map<String, TipoDetalheRemuneracao> tipos, String nome,
			NovosRegistros novosRegistros) {
		TipoDetalheRemuneracao tipo = tipos.get(nome);
		if (tipo == null) {
			tipo = new TipoDetalheRemuneracao();
			tipo.setNome(nome);
			tipo.setDataCriacao(new Date());
			tipo.setUltimaAtualizacao(new Date());
			tipo.setHabilitado(true);
			novosRegistros.addTipo(tipo);
		}
		return tipo;
	}

}