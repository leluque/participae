package br.com.participae.transparencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;
import br.com.participae.transparencia.dominio.DetalheRemuneracao.Natureza;

public interface TipoDetalheRemuneracaoDAO extends JpaRepository<TipoDetalheRemuneracao, Long> {

	public TipoDetalheRemuneracao findByNome(String nome);
	
	@Query("SELECT DISTINCT tip FROM DetalheRemuneracao det LEFT JOIN det.tipo tip WHERE det.natureza=:natureza ORDER BY tip.nome")
	public List<TipoDetalheRemuneracao> porNatureza(@Param("natureza") Natureza natureza);

}
