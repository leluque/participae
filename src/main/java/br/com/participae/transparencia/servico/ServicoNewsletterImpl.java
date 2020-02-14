package br.com.participae.transparencia.servico;

import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.Newsletter;
import br.com.participae.transparencia.repositorio.NewsletterDAO;

@Service
public class ServicoNewsletterImpl implements ServicoNewsletter {

	private NewsletterDAO newsletterDAO;

	public ServicoNewsletterImpl(NewsletterDAO newsletterDAO) {
		super();
		this.newsletterDAO = newsletterDAO;
	}

	@Override
	public void salvar(Newsletter newsletter) {
		this.newsletterDAO.save(newsletter);
	}
	
}
