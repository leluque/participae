package br.com.participae.transparencia.to;

public class EntradaResultado {

	private Long id;
	private String nome;
	private String cargo;
	private double minimo;
	private double maximo;
	private double media;
	private double salario;
	private double mediana;
	private String demonstrativo;

	public EntradaResultado() {
		super();
	}
	
	public EntradaResultado(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public EntradaResultado(Long id, String nome, String cargo, double salario, String demonstrativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.salario = salario;
		this.demonstrativo = demonstrativo;
	}

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

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public double getMediana() {
		return mediana;
	}

	public void setMediana(double mediana) {
		this.mediana = mediana;
	}

	public String getDemonstrativo() {
		return demonstrativo;
	}

	public void setDemonstrativo(String demonstrativo) {
		this.demonstrativo = demonstrativo;
	}

}
