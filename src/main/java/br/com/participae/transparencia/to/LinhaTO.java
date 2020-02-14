package br.com.participae.transparencia.to;

public class LinhaTO {

	private Long id;
	private String nome;
	private String cargo;
	private Double salario;
	private String demonstrativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getDemonstrativo() {
		return demonstrativo;
	}

	public void setDemonstrativo(String demonstrativo) {
		this.demonstrativo = demonstrativo;
	}

}
