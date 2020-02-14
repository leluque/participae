package br.com.participae.transparencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.participae.transparencia.dominio.Cargo;

public interface CargoDAO extends JpaRepository<Cargo, Long> {

	@Query("SELECT car.nome FROM Cargo car")
	public List<String> nomes();
	
	public Cargo findByNome(String nome);
	
	public List<Cargo> findByOrderByNome();

}
