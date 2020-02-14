package br.com.participae.transparencia.servico;

import org.springframework.data.domain.Page;

import br.com.participae.transparencia.repositorio.FilterCriteria;
import br.com.participae.transparencia.to.EntradaResultado;
import br.com.participae.transparencia.to.PesquisaTO;
import br.com.participae.transparencia.to.ResultadoPesquisa;

public interface ServicoPesquisa {

	public ResultadoPesquisa pesquisar(PesquisaTO pesquisa);

	public Page<EntradaResultado> pesquisar(PesquisaTO pesquisa, FilterCriteria filtro);
	
	public Long contar(PesquisaTO pesquisa, FilterCriteria filtro);
	
}
