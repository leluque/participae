package br.com.participae.transparencia.to;

import java.util.ArrayList;
import java.util.List;

import br.com.participae.transparencia.dominio.Cargo;
import br.com.participae.transparencia.dominio.RemuneracaoServidor;
import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;

/**
 * Esta classe armazena novos cargos e tipos criados no cadastro de um servidor.
 * Ela e usada para que, apos o preenchimento de um servidor, os novos cargos e
 * tipos sejam cadastrados no banco de dados.
 * 
 * @author Leandro Luque
 * @since fev/2018
 */
public class NovosRegistros {

	private List<Cargo> cargos = new ArrayList<>();
	private List<TipoDetalheRemuneracao> tipos = new ArrayList<>();
	private RemuneracaoServidor remuneracao;

	public void addCargo(Cargo cargo) {
		this.cargos.add(cargo);
	}

	public void addTipo(TipoDetalheRemuneracao tipo) {
		this.tipos.add(tipo);
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public List<TipoDetalheRemuneracao> getTipos() {
		return tipos;
	}

	public RemuneracaoServidor getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(RemuneracaoServidor remuneracao) {
		this.remuneracao = remuneracao;
	}

}
