package br.com.participae.transparencia.to;

import java.util.ArrayList;
import java.util.List;

import br.com.participae.transparencia.dominio.Cargo;
import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;

public class PesquisaTO {

	private String referencia;
	private String texto;
	private String faixaSalarial;
	private Double pisoSalarial;
	private Double tetoSalarial;
	private Boolean tenhaCargo;
	private String condicao;
	private List<Cargo> cargos = new ArrayList<>();
	private Boolean tenhaTipoItem;
	private List<TipoDetalheRemuneracao> valorItemFolha = new ArrayList<>();
	private int startRow;
	private int length;
	private String filter;
	private int columnToOrder;
	private String columnOrder;

	public enum Agrupamento {
		CARGO, FAIXA_SALARIAL
	}

	private Agrupamento agrupamento;
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Double getPisoSalarial() {
		return pisoSalarial;
	}

	public void setPisoSalarial(Double pisoSalarial) {
		this.pisoSalarial = pisoSalarial;
	}

	public Double getTetoSalarial() {
		return tetoSalarial;
	}

	public void setTetoSalarial(Double tetoSalarial) {
		this.tetoSalarial = tetoSalarial;
	}

	public Boolean getTenhaCargo() {
		return tenhaCargo;
	}

	public void setTenhaCargo(Boolean tenhaCargo) {
		this.tenhaCargo = tenhaCargo;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Boolean getTenhaTipoItem() {
		return tenhaTipoItem;
	}

	public void setTenhaTipoItem(Boolean tenhaTipoItem) {
		this.tenhaTipoItem = tenhaTipoItem;
	}

	public List<TipoDetalheRemuneracao> getValorItemFolha() {
		return valorItemFolha;
	}

	public void setValorItemFolha(List<TipoDetalheRemuneracao> tiposItens) {
		this.valorItemFolha = tiposItens;
	}

	public Agrupamento getAgrupamento() {
		return agrupamento;
	}

	public void setAgrupamento(Agrupamento agrupamento) {
		this.agrupamento = agrupamento;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String regra) {
		this.condicao = regra;
	}

	public String getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(String faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
		if (this.faixaSalarial != null) {
			String[] partes = this.faixaSalarial.split(";");
			try {
				this.setPisoSalarial(Double.valueOf(partes[0]));
				this.setTetoSalarial(Double.valueOf(partes[1]));
			} catch (Exception erro) {

			}
		}
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public int getColumnToOrder() {
		return columnToOrder;
	}

	public void setColumnToOrder(int columnToOrder) {
		this.columnToOrder = columnToOrder;
	}

	public String getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(String columnOrder) {
		this.columnOrder = columnOrder;
	}

}
