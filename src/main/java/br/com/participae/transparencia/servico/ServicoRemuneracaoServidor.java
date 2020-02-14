package br.com.participae.transparencia.servico;

import java.util.List;

import br.com.participae.transparencia.dominio.RemuneracaoServidor;

public interface ServicoRemuneracaoServidor {

	public Double tetoSalarial(String referencia);
	public Double pisoSalarial(String referencia);
	public String ultimaReferencia();
	public List<String> referencias();
	public void atualizar(RemuneracaoServidor remuneracaoServidor);
}
