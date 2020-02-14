package br.com.participae.transparencia.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.repositorio.FilterCriteria.Order;
import br.com.participae.transparencia.to.EntradaResultado;
import br.com.participae.transparencia.to.PesquisaTO;
import br.com.participae.transparencia.to.ResultadoPesquisa;

@Service
public class PesquisaDAOImpl implements PesquisaDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public ResultadoPesquisa pesquisar(PesquisaTO pesquisa) {
		StringBuilder jpql = new StringBuilder(
				"SELECT DISTINCT new br.com.participae.mogi.to.EntradaResultado(ser.id, ser.nome, rem.cargo.nome, rem.totalBruto, rem.demonstrativo) FROM RemuneracaoServidor rem LEFT JOIN rem.servidor ser LEFT JOIN rem.detalhes det");

		// Define o mes de referencia.
		StringBuilder selection = new StringBuilder(" WHERE 1=1");
		selection.append(" AND rem.referencia=:referencia");

		List<EntradaResultado> registros = null;
		boolean parametroTexto = false;
		if (pesquisa.getTexto() != null && !pesquisa.getTexto().trim().isEmpty()) {
			selection.append(" AND LOWER(ser.nome) like LOWER(:texto) OR LOWER(rem.cargo.nome) like LOWER(:texto)");
			parametroTexto = true;
		}

		boolean parametroFaixaSalarial = false;
		if (pesquisa.getPisoSalarial() != null && pesquisa.getTetoSalarial() != null) {
			parametroFaixaSalarial = true;
			selection.append(" AND rem.totalBruto BETWEEN :piso AND :teto");
		}

		if (pesquisa.getCargos() != null && !pesquisa.getCargos().isEmpty()) {
			selection.append(" AND rem.cargo.id IN (");
			selection.append(pesquisa.getCargos().get(0).getId());
			for (int i = 1; i < pesquisa.getCargos().size(); i++) {
				selection.append(", ");
				selection.append(pesquisa.getCargos().get(i).getId());
			}
			selection.append(")");
		}

		boolean parametrosTipoItens = false;
		List<String> tiposItens = new ArrayList<>();
		if (pesquisa.getValorItemFolha() != null && !pesquisa.getValorItemFolha().isEmpty()) {
			parametrosTipoItens = true;
			selection.append(" AND (det.tipo.nome like :tipoItem0");
			tiposItens.add(pesquisa.getValorItemFolha().get(0).getNome());
			for (int i = 1; i < pesquisa.getCargos().size(); i++) {
				selection.append(" OR det.tipo.nome like :tipoItem" + i);
				tiposItens.add(pesquisa.getValorItemFolha().get(i).getNome());
			}
			selection.append(")");
		}

		String[] partes = null;
		if (pesquisa.getCondicao() != null && (pesquisa.getCondicao().contains("<") || pesquisa.getCondicao().contains(">"))) {
			if (pesquisa.getCondicao().contains("<")) {
				partes = pesquisa.getCondicao().split("<");
			} else {
				partes = pesquisa.getCondicao().split(">");
			}
			jpql.append(", DetalheRemuneracao esqdet, DetalheRemuneracao dirdet");
			selection.append(" AND esqdet.remuneracao.id=rem.id AND esqdet.tipo.id=:idTipoEsquerda");
			selection.append(" AND dirdet.remuneracao.id=rem.id AND dirdet.tipo.id=:idTipoDireita");
			selection.append(" AND esqdet.valor");
			if (pesquisa.getCondicao().contains("<")) {
				selection.append("<");
			} else {
				selection.append(">");
			}
			selection.append("dirdet.valor");
		}

		selection.append(" ORDER BY rem.totalBruto DESC");

		TypedQuery<EntradaResultado> consulta = entityManager.createQuery(jpql.toString() + selection.toString(),
				EntradaResultado.class);
		consulta.setParameter("referencia", pesquisa.getReferencia());
		if (parametroTexto) {
			consulta.setParameter("texto", "%" + pesquisa.getTexto() + "%");
		}
		if (parametroFaixaSalarial) {
			consulta.setParameter("piso", pesquisa.getPisoSalarial());
			consulta.setParameter("teto", pesquisa.getTetoSalarial());
		}
		if (parametrosTipoItens) {
			for (int i = 0; i < tiposItens.size(); i++) {
				consulta.setParameter("tipoItem" + i, "%" + tiposItens.get(i) + "%");
			}
		}
		if(partes != null) {
			consulta.setParameter("idTipoEsquerda", Long.valueOf(partes[0]));
			consulta.setParameter("idTipoDireita", Long.valueOf(partes[1]));
		}

		registros = consulta.getResultList();

		ResultadoPesquisa resultado = new ResultadoPesquisa();
		resultado.setRegistros(registros);

		return resultado;
	}

	@Override
	public Long contar(PesquisaTO pesquisa, FilterCriteria filtro) {
		StringBuilder jpql = new StringBuilder(
				"SELECT count(DISTINCT ser.id) FROM RemuneracaoServidor rem LEFT JOIN rem.servidor ser LEFT JOIN rem.detalhes det");

		// Define o mes de referencia.
		StringBuilder selection = new StringBuilder(" WHERE 1=1");
		selection.append(" AND rem.referencia=:referencia");

		List<EntradaResultado> registros = null;
		boolean parametroTexto = false;
		if (pesquisa.getTexto() != null && !pesquisa.getTexto().trim().isEmpty()) {
			selection.append(" AND (LOWER(ser.nome) like LOWER(:texto) OR LOWER(rem.cargo.nome) like LOWER(:texto))");
			parametroTexto = true;
		}
		if(filtro.getFilterValue() != null) {
			selection.append(" AND (LOWER(ser.nome) like LOWER(:filtro) OR LOWER(rem.cargo.nome) like LOWER(:filtro))");	
		}

		boolean parametroFaixaSalarial = false;
		if (pesquisa.getPisoSalarial() != null && pesquisa.getTetoSalarial() != null) {
			parametroFaixaSalarial = true;
			selection.append(" AND rem.totalBruto BETWEEN :piso AND :teto");
		}

		if (pesquisa.getCargos() != null && !pesquisa.getCargos().isEmpty()) {
			selection.append(" AND rem.cargo.id IN (");
			selection.append(pesquisa.getCargos().get(0).getId());
			for (int i = 1; i < pesquisa.getCargos().size(); i++) {
				selection.append(", ");
				selection.append(pesquisa.getCargos().get(i).getId());
			}
			selection.append(")");
		}

		boolean parametrosTipoItens = false;
		List<String> tiposItens = new ArrayList<>();
		if (pesquisa.getValorItemFolha() != null && !pesquisa.getValorItemFolha().isEmpty()) {
			parametrosTipoItens = true;
			if(filtro.getFilterValue() != null) {
				selection.append(" AND LOWER(det.tipo.nome) like LOWER(:filtro)");				
			}
			selection.append(" AND (det.tipo.nome like :tipoItem0");
			tiposItens.add(pesquisa.getValorItemFolha().get(0).getNome());
			for (int i = 1; i < pesquisa.getCargos().size(); i++) {
				selection.append(" OR det.tipo.nome like :tipoItem" + i);
				tiposItens.add(pesquisa.getValorItemFolha().get(i).getNome());
			}
			selection.append(")");
		}

		String[] partes = null;
		if (pesquisa.getCondicao() != null && (pesquisa.getCondicao().contains("<") || pesquisa.getCondicao().contains(">"))) {
			if (pesquisa.getCondicao().contains("<")) {
				partes = pesquisa.getCondicao().split("<");
			} else {
				partes = pesquisa.getCondicao().split(">");
			}
			jpql.append(", DetalheRemuneracao esqdet, DetalheRemuneracao dirdet");
			selection.append(" AND esqdet.remuneracao.id=rem.id AND esqdet.tipo.id=:idTipoEsquerda");
			selection.append(" AND dirdet.remuneracao.id=rem.id AND dirdet.tipo.id=:idTipoDireita");
			selection.append(" AND esqdet.valor");
			if (pesquisa.getCondicao().contains("<")) {
				selection.append("<");
			} else {
				selection.append(">");
			}
			selection.append("dirdet.valor");
		}

/*		if(filtro.getOrderBy() != null && !filtro.getOrderBy().isEmpty()) {
			selection.append(" ORDER BY ");
			for(int i = 0; i < filtro.getOrderBy().size()-1;i++) {
				selection.append(filtro.getOrderBy().get(i) + ", ");
			}
			selection.append(filtro.getOrderBy().get(filtro.getOrderBy().size()-1));
			if(filtro.getOrder() == Order.ASCENDING) {
				selection.append(" ASC");
			} else {
				selection.append(" DESC");
			}
		} else {
			selection.append(" ORDER BY rem.totalBruto DESC");
		}*/
		
		TypedQuery<Long> consulta = entityManager.createQuery(jpql.toString() + selection.toString(),
				Long.class);
		/*if(filtro.getInitialRow() != null) {
			consulta.setFirstResult(filtro.getInitialRow()); 
			
		}
		if(filtro.getNumberOfRows() != null && filtro.getNumberOfRows() != -1) {
			consulta.setMaxResults(filtro.getNumberOfRows());
		}*/
		
		consulta.setParameter("referencia", pesquisa.getReferencia());
		if (parametroTexto) {
			consulta.setParameter("texto", "%" + pesquisa.getTexto() + "%");
		}
		if(filtro.getFilterValue() != null) {
			consulta.setParameter("filtro", "%" + filtro.getFilterValue() + "%");
		}
		if (parametroFaixaSalarial) {
			consulta.setParameter("piso", pesquisa.getPisoSalarial());
			consulta.setParameter("teto", pesquisa.getTetoSalarial());
		}
		if (parametrosTipoItens) {
			for (int i = 0; i < tiposItens.size(); i++) {
				consulta.setParameter("tipoItem" + i, "%" + tiposItens.get(i) + "%");
			}
		}
		if(partes != null) {
			consulta.setParameter("idTipoEsquerda", Long.valueOf(partes[0]));
			consulta.setParameter("idTipoDireita", Long.valueOf(partes[1]));
		}

		return consulta.getSingleResult();
	}
	
	@Override
	public Page<EntradaResultado> pesquisar(PesquisaTO pesquisa, FilterCriteria filtro) {
		StringBuilder jpql = new StringBuilder(
				"SELECT DISTINCT new br.com.participae.mogi.to.EntradaResultado(ser.id, ser.nome, rem.cargo.nome, rem.totalBruto, rem.demonstrativo) FROM RemuneracaoServidor rem LEFT JOIN rem.servidor ser LEFT JOIN rem.detalhes det");

		// Define o mes de referencia.
		StringBuilder selection = new StringBuilder(" WHERE 1=1");
		selection.append(" AND rem.referencia=:referencia");

		List<EntradaResultado> registros = null;
		boolean parametroTexto = false;
		if (pesquisa.getTexto() != null && !pesquisa.getTexto().trim().isEmpty()) {
			selection.append(" AND (LOWER(ser.nome) like LOWER(:texto) OR LOWER(rem.cargo.nome) like LOWER(:texto))");
			parametroTexto = true;
		}
		if(filtro.getFilterValue() != null) {
			selection.append(" AND (LOWER(ser.nome) like LOWER(:filtro) OR LOWER(rem.cargo.nome) like LOWER(:filtro))");	
		}

		boolean parametroFaixaSalarial = false;
		if (pesquisa.getPisoSalarial() != null && pesquisa.getTetoSalarial() != null) {
			parametroFaixaSalarial = true;
			selection.append(" AND rem.totalBruto BETWEEN :piso AND :teto");
		}

		if (pesquisa.getCargos() != null && !pesquisa.getCargos().isEmpty()) {
			selection.append(" AND rem.cargo.id IN (");
			selection.append(pesquisa.getCargos().get(0).getId());
			for (int i = 1; i < pesquisa.getCargos().size(); i++) {
				selection.append(", ");
				selection.append(pesquisa.getCargos().get(i).getId());
			}
			selection.append(")");
		}

		boolean parametrosTipoItens = false;
		List<String> tiposItens = new ArrayList<>();
		if (pesquisa.getValorItemFolha() != null && !pesquisa.getValorItemFolha().isEmpty()) {
			parametrosTipoItens = true;
			if(filtro.getFilterValue() != null) {
				selection.append(" AND det.tipo.nome like :filtro");				
			}
			selection.append(" AND (det.tipo.nome like :tipoItem0");
			tiposItens.add(pesquisa.getValorItemFolha().get(0).getNome());
			for (int i = 1; i < pesquisa.getCargos().size(); i++) {
				selection.append(" OR det.tipo.nome like :tipoItem" + i);
				tiposItens.add(pesquisa.getValorItemFolha().get(i).getNome());
			}
			selection.append(")");
		}

		String[] partes = null;
		if (pesquisa.getCondicao() != null && (pesquisa.getCondicao().contains("<") || pesquisa.getCondicao().contains(">"))) {
			if (pesquisa.getCondicao().contains("<")) {
				partes = pesquisa.getCondicao().split("<");
			} else {
				partes = pesquisa.getCondicao().split(">");
			}
			jpql.append(", DetalheRemuneracao esqdet, DetalheRemuneracao dirdet");
			selection.append(" AND esqdet.remuneracao.id=rem.id AND esqdet.tipo.id=:idTipoEsquerda");
			selection.append(" AND dirdet.remuneracao.id=rem.id AND dirdet.tipo.id=:idTipoDireita");
			selection.append(" AND esqdet.valor");
			if (pesquisa.getCondicao().contains("<")) {
				selection.append("<");
			} else {
				selection.append(">");
			}
			selection.append("dirdet.valor");
		}

		if(filtro.getOrderBy() != null && !filtro.getOrderBy().isEmpty()) {
			selection.append(" ORDER BY ");
			for(int i = 0; i < filtro.getOrderBy().size()-1;i++) {
				selection.append(filtro.getOrderBy().get(i) + ", ");
			}
			selection.append(filtro.getOrderBy().get(filtro.getOrderBy().size()-1));
			if(filtro.getOrder() == Order.ASCENDING) {
				selection.append(" ASC");
			} else {
				selection.append(" DESC");
			}
		} else {
			selection.append(" ORDER BY rem.totalBruto DESC");
		}
		
		TypedQuery<EntradaResultado> consulta = entityManager.createQuery(jpql.toString() + selection.toString(),
				EntradaResultado.class);
		if(filtro.getInitialRow() != null) {
			consulta.setFirstResult(filtro.getInitialRow()); 
			
		}
		if(filtro.getNumberOfRows() != null && filtro.getNumberOfRows() != -1) {
			consulta.setMaxResults(filtro.getNumberOfRows());
		}
		
		consulta.setParameter("referencia", pesquisa.getReferencia());
		if (parametroTexto) {
			consulta.setParameter("texto", "%" + pesquisa.getTexto() + "%");
		}
		if(filtro.getFilterValue() != null) {
			consulta.setParameter("filtro", "%" + filtro.getFilterValue() + "%");
		}
		if (parametroFaixaSalarial) {
			consulta.setParameter("piso", pesquisa.getPisoSalarial());
			consulta.setParameter("teto", pesquisa.getTetoSalarial());
		}
		if (parametrosTipoItens) {
			for (int i = 0; i < tiposItens.size(); i++) {
				consulta.setParameter("tipoItem" + i, "%" + tiposItens.get(i) + "%");
			}
		}
		if(partes != null) {
			consulta.setParameter("idTipoEsquerda", Long.valueOf(partes[0]));
			consulta.setParameter("idTipoDireita", Long.valueOf(partes[1]));
		}

		registros = consulta.getResultList();
		
		Page<EntradaResultado> resultados = new PageImpl<>(registros);

		return resultados;
	}

}
