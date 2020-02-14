package br.com.participae.transparencia.servico;

import br.com.participae.transparencia.dominio.DetalheRemuneracao;
import br.com.participae.transparencia.dominio.RemuneracaoServidor;
import br.com.participae.transparencia.dominio.DetalheRemuneracao.Natureza;

public class ServicoAtualizacao {

	/**
	 * Retorna a folha de pagamento da remuneracao do servidor especificada.
	 * 
	 * @param remuneracao
	 *            A remuneracao do servidor.
	 * @return Uma String com a folha de pagamento do servidor.
	 */
	protected String gerarFolhaServidor(RemuneracaoServidor remuneracao) {
		StringBuilder folhaPagamento = new StringBuilder();
		folhaPagamento.append("Demonstrativo de Pagamento");
		folhaPagamento.append("\n");
		for (DetalheRemuneracao detalhe : remuneracao.getDetalhes()) {
			folhaPagamento.append(detalhe.getTipo().getNome());
			for (int i = detalhe.getTipo().getNome().length(); i < 40; i++) {
				folhaPagamento.append(" ");
			}
			if (detalhe.getNatureza() == Natureza.RENDIMENTO) {
				folhaPagamento.append(" + R$ ");
			} else if (detalhe.getNatureza() == Natureza.DESCONTO) {
				folhaPagamento.append(" - R$ ");
			} else if (detalhe.getNatureza() == Natureza.OUTRO) {
				folhaPagamento.append(" R$ ");
			}
			folhaPagamento.append(String.format("%.2f", detalhe.getValor()));
			folhaPagamento.append("\n");
		}
		return folhaPagamento.toString();
	}

}
