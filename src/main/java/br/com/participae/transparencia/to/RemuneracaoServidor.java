package br.com.participae.transparencia.to;

import java.util.HashSet;
import java.util.Set;

/**
 * Esta classe implementa detalhes da remuneracao de servidores publicos.
 *
 * @author Leandro Luque
 * @version 1.0
 * @since fev/2018
 */
public class RemuneracaoServidor {

	private String referencia;
	private String cargo;
	private String nome;
	private Set<MovimentacaoServidor> rendimentos = new HashSet<>();
	private Set<MovimentacaoServidor> descontos = new HashSet<>();
	private Set<MovimentacaoServidor> totais = new HashSet<>();
	private Set<MovimentacaoServidor> outros = new HashSet<>();
	private String salario;

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<MovimentacaoServidor> getRendimentos() {
		return rendimentos;
	}

	public void setRendimentos(Set<MovimentacaoServidor> rendimentos) {
		this.rendimentos = rendimentos;
	}

	public Set<MovimentacaoServidor> getDescontos() {
		return descontos;
	}

	public void setDescontos(Set<MovimentacaoServidor> descontos) {
		this.descontos = descontos;
	}

	public Set<MovimentacaoServidor> getTotais() {
		return totais;
	}

	public void setTotais(Set<MovimentacaoServidor> totais) {
		this.totais = totais;
	}

	public Set<MovimentacaoServidor> getOutros() {
		return outros;
	}

	public void setOutros(Set<MovimentacaoServidor> outros) {
		this.outros = outros;
	}

	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}

	public Double getTotalBruto() {
		for (MovimentacaoServidor total : this.totais) {
			if (total.getNome().trim().equalsIgnoreCase("TOTAL VENCIM/TO") ||
					total.getNome().trim().equalsIgnoreCase("Total Proventos")) {
				return Double.valueOf(total.getValor().replace(".", "").replace(",", "."));
			}
		}
		return 0d;
	}

	public Double getTotalLiquido() {
		for (MovimentacaoServidor total : this.totais) {
			if (total.getNome().trim().equalsIgnoreCase("TOTAL LIQUIDO")) {
				return Double.valueOf(total.getValor().replace(".", "").replace(",", "."));
			} else {
				double totalBruto = getTotalBruto();
				double totalDesconto = getTotalBruto();
				return totalBruto - totalDesconto;
			}
		}
		return 0d;
	}

	public Double getTotalDesconto() {
		for (MovimentacaoServidor total : this.totais) {
			if (total.getNome().trim().equalsIgnoreCase("TOTAL DESCONTOS") ||
					total.getNome().trim().equalsIgnoreCase("Total Descontos")) {
				return Double.valueOf(total.getValor().replace(".", "").replace(",", "."));
			}
		}
		return 0d;
	}

	public String toString() {
		return "RemuneracaoServidor [referencia=" + referencia + ", cargo=" + cargo + ", nome=" + nome
				+ ", rendimentos=" + rendimentos + ", descontos=" + descontos + ", totais=" + totais + ", outros="
				+ outros + ", salario=" + salario + "]";
	}

}
