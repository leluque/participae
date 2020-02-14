package br.com.participae.transparencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.participae.transparencia.dominio.Cidade;

public interface CidadeDAO extends JpaRepository<Cidade, Long> {

	public Cidade findByNome(String nome);

	public Cidade findBySigla(String sigla);

}
