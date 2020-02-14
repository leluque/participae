package br.com.participae.transparencia.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;
import br.com.participae.transparencia.dominio.DetalheRemuneracao.Natureza;
import br.com.participae.transparencia.repositorio.TipoDetalheRemuneracaoDAO;

@Service
public class ServicoTipoDetalheRemuneracaoImpl implements ServicoTipoDetalheRemuneracao {

	private TipoDetalheRemuneracaoDAO tipoDetalheRemuneracaoDAO;

	@Autowired
	public ServicoTipoDetalheRemuneracaoImpl(TipoDetalheRemuneracaoDAO tipoDetalheRemuneracaoDAO) {
		this.tipoDetalheRemuneracaoDAO = tipoDetalheRemuneracaoDAO;
	}

	@Override
	public List<TipoDetalheRemuneracao> todos() {
		return this.tipoDetalheRemuneracaoDAO.findAll();
	}

	@Override
	public List<TipoDetalheRemuneracao> rendimentos() {
		return this.tipoDetalheRemuneracaoDAO.porNatureza(Natureza.RENDIMENTO);
	}

	@Override
	public List<TipoDetalheRemuneracao> descontos() {
		return this.tipoDetalheRemuneracaoDAO.porNatureza(Natureza.DESCONTO);
	}

	@Override
	public void salvar(TipoDetalheRemuneracao tipo) {
		this.tipoDetalheRemuneracaoDAO.save(tipo);
	}

}
