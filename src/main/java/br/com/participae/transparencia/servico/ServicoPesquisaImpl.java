package br.com.participae.transparencia.servico;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.repositorio.FilterCriteria;
import br.com.participae.transparencia.repositorio.PesquisaDAO;
import br.com.participae.transparencia.to.EntradaResultado;
import br.com.participae.transparencia.to.PesquisaTO;
import br.com.participae.transparencia.to.ResultadoPesquisa;

@Service
public class ServicoPesquisaImpl implements ServicoPesquisa {

	private PesquisaDAO pesquisaDAO;
	private static int quantasDivisoes = 4;

	@Autowired
	public ServicoPesquisaImpl(PesquisaDAO pesquisaDAO) {
		this.pesquisaDAO = pesquisaDAO;
	}

	public ResultadoPesquisa pesquisar(PesquisaTO pesquisa) {
		ResultadoPesquisa resultado = pesquisaDAO.pesquisar(pesquisa);

		// **************************************
		// Calcula a quantidade de funcionários por faixa de salário.
		if (!resultado.getRegistros().isEmpty()) {
			double tamanhoDivisao = Math.floor((resultado.getMaximo() - resultado.getMinimo()) / quantasDivisoes);
			if (tamanhoDivisao == 0) {
				double totalPago = 0;
				for (int i = 0; i < resultado.getRegistros().size(); i++) {
					EntradaResultado entrada = resultado.getRegistros().get(i);
					totalPago += entrada.getSalario();
				}
				resultado.setTotalGasto(totalPago);
			} else {
				Map<Integer, Integer> quantidadePorSubfaixa = new HashMap<>(quantasDivisoes);
				double valorAtual = resultado.getMinimo();
				Map<Integer, Double> limiteSubfaixa = new HashMap<>(quantasDivisoes);
				// O maior salário nunca ficará dentro da última faixa [,). Por isso, é usada
				// uma divisão a mais.
				for (int i = 0; i < quantasDivisoes + 1; i++) {
					valorAtual += tamanhoDivisao;
					quantidadePorSubfaixa.put(i, 0);
					limiteSubfaixa.put(i, valorAtual);
				}

				double fracaoSalario30FuncionariosMaisBemPagos = 0;
				double totalPago = 0;
				int limiteDivisao = (int) (resultado.getRegistros().size() * 0.1);
				for (int i = 0; i < limiteDivisao && i < resultado.getRegistros().size(); i++) {
					EntradaResultado entrada = resultado.getRegistros().get(i);
					int posicao = (int) Math.floor((entrada.getSalario() - resultado.getMinimo()) / tamanhoDivisao);
					quantidadePorSubfaixa.put(posicao, quantidadePorSubfaixa.get(posicao) + 1);
					fracaoSalario30FuncionariosMaisBemPagos += entrada.getSalario();
					totalPago += entrada.getSalario();
				}
				for (int i = limiteDivisao; i < resultado.getRegistros().size() - limiteDivisao; i++) {
					EntradaResultado entrada = resultado.getRegistros().get(i);
					int posicao = (int) Math.floor((entrada.getSalario() - resultado.getMinimo()) / tamanhoDivisao);
					quantidadePorSubfaixa.put(posicao, quantidadePorSubfaixa.get(posicao) + 1);
					totalPago += entrada.getSalario();
				}
				for (int i = resultado.getRegistros().size() - limiteDivisao; i < resultado.getRegistros()
						.size(); i++) {
					EntradaResultado entrada = resultado.getRegistros().get(i);
					int posicao = (int) Math.floor((entrada.getSalario() - resultado.getMinimo()) / tamanhoDivisao);
					quantidadePorSubfaixa.put(posicao, quantidadePorSubfaixa.get(posicao) + 1);
					totalPago += entrada.getSalario();
				}
				resultado.setTotalGasto(totalPago);
				resultado.setFracaoSalario30FuncionariosMaisBemPagos(
						100 * fracaoSalario30FuncionariosMaisBemPagos / totalPago);
				// O maior salário nunca ficará dentro da última faixa [,). Por isso, ele é
				// adicionado à penúltima faixa e removido em seguida.
				quantidadePorSubfaixa.put(quantasDivisoes - 1, quantidadePorSubfaixa.get(quantasDivisoes - 1) + 1);
				quantidadePorSubfaixa.remove(quantasDivisoes);
				limiteSubfaixa.remove(quantasDivisoes);

				for (int i = 0; i < quantasDivisoes; i++) {
					resultado.addLimiteSubfaixa(limiteSubfaixa.get(i));
					resultado.addQuantidadeNaSubfaixa(quantidadePorSubfaixa.get(i));
				}
			}
		}
		// **************************************

		return resultado;
	}

	@Override
	public Page<EntradaResultado> pesquisar(PesquisaTO pesquisa, FilterCriteria filtro) {
		return this.pesquisaDAO.pesquisar(pesquisa, filtro);
	}

	@Override
	public Long contar(PesquisaTO pesquisa, FilterCriteria filtro) {
		return this.pesquisaDAO.contar(pesquisa, filtro);
	}
	
	

	
	
}
