package br.com.participae.transparencia.to;

import java.util.ArrayList;
import java.util.List;

import br.com.participae.transparencia.dominio.Cargo;
import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;

public class FiltrosTO {

	private double pisoSalario;
	private double tetoSalario;
	private String ultimaReferencia;
	private List<String> referencias = new ArrayList<>();
	private List<TipoDetalheRemuneracao> tiposRendimentos = new ArrayList<>();
	private List<TipoDetalheRemuneracao> tiposDescontos = new ArrayList<>();
	private List<Cargo> cargos = new ArrayList<>();

	public double getPisoSalario() {
		return pisoSalario;
	}

	public void setPisoSalario(double pisoSalario) {
		this.pisoSalario = pisoSalario;
	}

	public double getTetoSalario() {
		return tetoSalario;
	}

	public void setTetoSalario(double tetoSalario) {
		this.tetoSalario = tetoSalario;
	}

	public String getUltimaReferencia() {
		return ultimaReferencia;
	}

	public void setUltimaReferencia(String ultimaReferencia) {
		this.ultimaReferencia = ultimaReferencia;
	}

	public List<String> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<String> referencias) {
		this.referencias = referencias;
	}

	public List<TipoDetalheRemuneracao> getTiposRendimentos() {
		return tiposRendimentos;
	}

	public void setTiposRendimentos(List<TipoDetalheRemuneracao> tiposReceitas) {
		this.tiposRendimentos = tiposReceitas;
	}

	public List<TipoDetalheRemuneracao> getTiposDescontos() {
		return tiposDescontos;
	}

	public void setTiposDescontos(List<TipoDetalheRemuneracao> tiposDespesas) {
		this.tiposDescontos = tiposDespesas;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

}
