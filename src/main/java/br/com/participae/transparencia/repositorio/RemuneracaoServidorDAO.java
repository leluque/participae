package br.com.participae.transparencia.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.participae.transparencia.dominio.RemuneracaoServidor;

public interface RemuneracaoServidorDAO extends JpaRepository<RemuneracaoServidor, Long> {

	@Query("SELECT COALESCE(MAX(rem.totalBruto), 0) FROM RemuneracaoServidor rem WHERE rem.referencia=:referencia")
	public Double tetoSalarial(@Param("referencia") String referencia);
	@Query("SELECT COALESCE(MIN(rem.totalBruto), 0) FROM RemuneracaoServidor rem WHERE rem.referencia=:referencia")
	public Double pisoSalarial(@Param("referencia") String referencia);
	@Query("SELECT DISTINCT rem.referencia FROM RemuneracaoServidor rem")
	public Page<String> referencias(Pageable pageable);
	@Query("SELECT DISTINCT rem.referencia FROM RemuneracaoServidor rem")
	public List<String> referencias();

}
