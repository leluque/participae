package br.com.participae.transparencia.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.Servidor;
import br.com.participae.transparencia.repositorio.ServidorDAO;

@Service
public class ServicoServidorImpl implements ServicoServidor {

	private ServidorDAO servidorDAO;

	@Autowired
	public ServicoServidorImpl(ServidorDAO servidorDAO) {
		this.servidorDAO = servidorDAO;
	}

	@Override
	public Servidor localizarPorCodigo(String codigo) {
		return this.servidorDAO.findByCodigo(codigo);
	}

	@Override
	public void salvar(Servidor servidor) {
		this.servidorDAO.saveAndFlush(servidor);

	}

	@Override
	public void atualizar(Servidor servidor) {
		this.servidorDAO.saveAndFlush(servidor);
	}

	@Override
	public List<Servidor> todos() {
		return this.servidorDAO.findAll();
	}

}
