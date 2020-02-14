package br.com.participae.transparencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.participae.transparencia.dominio.Servidor;

public interface ServidorDAO extends JpaRepository<Servidor, Long>, JpaSpecificationExecutor<Servidor> {

	public Servidor findByCodigo(String codigo);
	public Servidor findByNome(String nome);

}
