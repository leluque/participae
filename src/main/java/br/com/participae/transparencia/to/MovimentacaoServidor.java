package br.com.participae.transparencia.to;

/**
 * Esta classe implementa entradas de rendimento de servidores.
 *
 * @author Leandro Luque
 * @version 1.0
 * @since fev/2018
 */
public class MovimentacaoServidor {

	private String nome;
	private String valor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "MovimentacaoServidor [valor=" + valor + ", nome=" + nome + "]";
	}

}