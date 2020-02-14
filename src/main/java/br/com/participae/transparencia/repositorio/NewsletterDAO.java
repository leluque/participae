package br.com.participae.transparencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.participae.transparencia.dominio.Newsletter;

public interface NewsletterDAO extends JpaRepository<Newsletter, Long> {

}
