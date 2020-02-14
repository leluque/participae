package br.com.participae.transparencia.servico;

import java.util.List;

import br.com.participae.transparencia.dominio.Cidade;

public interface ServicoCidade {

	public List<Cidade> cidades();
	
	public Cidade pesquisarPorSigla(String sigla);
	
	public void salvar(Cidade cidade);
	
}
