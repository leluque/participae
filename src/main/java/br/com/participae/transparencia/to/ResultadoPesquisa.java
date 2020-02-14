package br.com.participae.transparencia.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoPesquisa {

	private boolean agrupamento;
	private double minimo;
	private double maximo;
	private double media;
	private double mediana;
	private int quantidade;
	private List<EntradaResultado> registros = new ArrayList<>();
	private List<Double> limitesSubfaixas = new ArrayList<>();
	private List<Integer> quantidadesNasSubfaixas = new ArrayList<>();
	private Double fracaoSalario30FuncionariosMaisBemPagos;
	private Double totalGasto;
	private double moda;

	public Double getTotalGasto() {
		return totalGasto;
	}

	public void setTotalGasto(Double totalGasto) {
		this.totalGasto = totalGasto;
	}

	public Double getFracaoSalario30FuncionariosMaisBemPagos() {
		return fracaoSalario30FuncionariosMaisBemPagos;
	}

	public void setFracaoSalario30FuncionariosMaisBemPagos(Double fracaoSalario30FuncionariosMaisBemPagos) {
		this.fracaoSalario30FuncionariosMaisBemPagos = fracaoSalario30FuncionariosMaisBemPagos;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isAgrupamento() {
		return agrupamento;
	}

	public void setAgrupamento(boolean agrupamento) {
		this.agrupamento = agrupamento;
	}

	public double getMinimo() {
		return minimo;
	}

	public void setMinimo(double minimo) {
		this.minimo = minimo;
	}

	public double getMaximo() {
		return maximo;
	}

	public void setMaximo(double maximo) {
		this.maximo = maximo;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public double getMediana() {
		return mediana;
	}

	public void setMediana(double mediana) {
		this.mediana = mediana;
	}

	public List<EntradaResultado> getRegistros() {
		return registros;
	}

	public List<Double> getLimitesSubfaixas() {
		return limitesSubfaixas;
	}

	public void setLimitesSubfaixas(List<Double> limitesSubfaixas) {
		this.limitesSubfaixas = limitesSubfaixas;
	}

	public void addLimiteSubfaixa(Double limiteSubfaixa) {
		this.limitesSubfaixas.add(limiteSubfaixa);
	}

	public List<Integer> getQuantidadesNasSubfaixas() {
		return quantidadesNasSubfaixas;
	}

	public void setQuantidadesNasSubfaixas(List<Integer> quantidadeNasSubfaixas) {
		this.quantidadesNasSubfaixas = quantidadeNasSubfaixas;
	}

	public void addQuantidadeNaSubfaixa(Integer quantidadeNaSubfaixa) {
		this.quantidadesNasSubfaixas.add(quantidadeNaSubfaixa);
	}
	
	public double getModa() {
		return moda;
	}

	public void setModa(double moda) {
		this.moda = moda;
	}

	public void setRegistros(List<EntradaResultado> resultados) {
		this.registros = resultados;

		if (this.registros != null && !this.registros.isEmpty()) {
			this.quantidade = this.registros.size();

			Map<Double, Integer> moda = new HashMap<>();
			if (!this.agrupamento) {
				this.minimo = Double.MAX_VALUE;
				this.maximo = Double.MIN_VALUE;
				this.media = 0;
				for (EntradaResultado entrada : this.registros) {
					if (entrada.getSalario() < minimo) {
						this.minimo = entrada.getSalario();
					}
					if (entrada.getSalario() > maximo) {
						this.maximo = entrada.getSalario();
					}
					media += entrada.getSalario();
					Integer qtdeAtual = moda.get(entrada.getSalario());
					if (qtdeAtual == null) {
						qtdeAtual = 0;
					}
					moda.put(entrada.getSalario(), qtdeAtual + 1);
				}
				this.media /= this.registros.size();
				int posicaoMeio = (this.registros.size() / 2);
				if (this.registros.size() % 2 == 0) {
					this.mediana = (this.registros.get(posicaoMeio - 1).getSalario()
							+ this.registros.get(posicaoMeio).getSalario()) / 2;
				} else {
					this.mediana = this.registros.get(posicaoMeio).getSalario();
				}

				Map.Entry<Double, Integer> maiorOcorrencia = null;

				boolean temModa = false;
				for (Map.Entry<Double, Integer> entry : moda.entrySet()) {
					if (maiorOcorrencia == null) {
						maiorOcorrencia = entry;
					} else if(entry.getValue().compareTo(maiorOcorrencia.getValue()) > 0) {
						maiorOcorrencia = entry;
						temModa = true;
					} else if(entry.getValue().compareTo(maiorOcorrencia.getValue()) == 0) {
						temModa = false;
					}
				}
				if(temModa) {
					setModa(maiorOcorrencia.getKey());
				} else {
					setModa(-1);					
				}

			}
		}
	}

}
