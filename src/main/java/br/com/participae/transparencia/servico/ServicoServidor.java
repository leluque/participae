package br.com.participae.transparencia.servico;

import java.util.List;

import br.com.participae.transparencia.dominio.Servidor;

public interface ServicoServidor {

	public Servidor localizarPorCodigo(String codigo);

	public void salvar(Servidor servidor);

	public void atualizar(Servidor servidor);

	public List<Servidor> todos();
	
}
