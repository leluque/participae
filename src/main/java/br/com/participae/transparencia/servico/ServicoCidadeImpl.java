package br.com.participae.transparencia.servico;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.Cidade;
import br.com.participae.transparencia.repositorio.CidadeDAO;

@Service
public class ServicoCidadeImpl implements ServicoCidade {

	private CidadeDAO cidadeDAO;

	public ServicoCidadeImpl(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	@Override
	public List<Cidade> cidades() {
		return cidadeDAO.findAll();
	}

	@Override
	public Cidade pesquisarPorSigla(String sigla) {
		return cidadeDAO.findBySigla(sigla);
	}

	@Override
	public void salvar(Cidade cidade) {
		cidadeDAO.save(cidade);
	}

}
