package br.com.participae.transparencia.to;

import java.util.ArrayList;
import java.util.List;

public class TabelaTO<T> {

	private Integer recordsTotal;
	private Integer recordsFiltered;
	private Integer draw;
	private List<T> data = new ArrayList<T>();

	public void addEntry(T teamEntry) {
		this.data.add(teamEntry);
	}

	public List<T> getData() {
		return data;
	}

	public Integer getDraw() {
		return draw;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

}
