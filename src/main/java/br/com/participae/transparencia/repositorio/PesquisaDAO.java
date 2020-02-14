package br.com.participae.transparencia.repositorio;

import org.springframework.data.domain.Page;

import br.com.participae.transparencia.to.EntradaResultado;
import br.com.participae.transparencia.to.PesquisaTO;
import br.com.participae.transparencia.to.ResultadoPesquisa;

public interface PesquisaDAO {

	public ResultadoPesquisa pesquisar(PesquisaTO pesquisa);

	public Page<EntradaResultado> pesquisar(PesquisaTO pesquisa, FilterCriteria filtro);
	public Long contar(PesquisaTO pesquisa, FilterCriteria filtro);
}
