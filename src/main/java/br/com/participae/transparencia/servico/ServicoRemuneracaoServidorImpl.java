package br.com.participae.transparencia.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.RemuneracaoServidor;
import br.com.participae.transparencia.repositorio.RemuneracaoServidorDAO;

@Service
public class ServicoRemuneracaoServidorImpl implements ServicoRemuneracaoServidor {

	private RemuneracaoServidorDAO remuneracaoServidorDAO;

	@Autowired
	public ServicoRemuneracaoServidorImpl(RemuneracaoServidorDAO remuneracaoServidorDAO) {
		this.remuneracaoServidorDAO = remuneracaoServidorDAO;
	}

	@Override
	public Double tetoSalarial(String referencia) {
		return this.remuneracaoServidorDAO.tetoSalarial(referencia);
	}

	@Override
	public Double pisoSalarial(String referencia) {
		return this.remuneracaoServidorDAO.pisoSalarial(referencia);
	}

	@Override
	public String ultimaReferencia() {
		Pageable pageable = new PageRequest(0, 1);

		Page<String> ultimaReferencia = this.remuneracaoServidorDAO.referencias(pageable);
		return ultimaReferencia.getContent().get(0);
	}
	
	public void atualizar(RemuneracaoServidor remuneracaoServidor) {
		this.remuneracaoServidorDAO.saveAndFlush(remuneracaoServidor);
	}

	@Override
	public List<String> referencias() {
		return this.remuneracaoServidorDAO.referencias();
	}

}
