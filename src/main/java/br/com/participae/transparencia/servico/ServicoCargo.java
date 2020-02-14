package br.com.participae.transparencia.servico;

import java.util.List;

import br.com.participae.transparencia.dominio.Cargo;

public interface ServicoCargo {

	public List<String> nomes();
	
	public List<Cargo> todos();
	
	public void salvar(Cargo cargo);
	
}
