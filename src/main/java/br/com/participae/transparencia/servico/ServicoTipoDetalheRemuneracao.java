package br.com.participae.transparencia.servico;

import java.util.List;

import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;

public interface ServicoTipoDetalheRemuneracao {

	public List<TipoDetalheRemuneracao> todos();
	public List<TipoDetalheRemuneracao> rendimentos();
	public List<TipoDetalheRemuneracao> descontos();
	
	public void salvar(TipoDetalheRemuneracao tipo);
	
}
