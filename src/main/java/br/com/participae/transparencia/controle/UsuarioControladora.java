package br.com.participae.transparencia.controle;

import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.participae.transparencia.dominio.Newsletter;
import br.com.participae.transparencia.repositorio.FilterCriteria;
import br.com.participae.transparencia.servico.ServicoCargo;
import br.com.participae.transparencia.servico.ServicoNewsletter;
import br.com.participae.transparencia.servico.ServicoPesquisa;
import br.com.participae.transparencia.servico.ServicoRemuneracaoServidor;
import br.com.participae.transparencia.servico.ServicoTipoDetalheRemuneracao;
import br.com.participae.transparencia.to.EntradaResultado;
import br.com.participae.transparencia.to.FiltrosTO;
import br.com.participae.transparencia.to.LinhaTO;
import br.com.participae.transparencia.to.PesquisaTO;
import br.com.participae.transparencia.to.ResultadoPesquisa;
import br.com.participae.transparencia.to.TabelaTO;

@Controller
public class UsuarioControladora {

	private ServicoTipoDetalheRemuneracao servicoTipoDetalheRemuneracao;
	private ServicoCargo servicoCargo;
	private ServicoRemuneracaoServidor servicoRemuneracaoServidor;
	private ServicoPesquisa servicoPesquisa;
	private ServicoNewsletter servicoNewsletter;

	public UsuarioControladora(ServicoTipoDetalheRemuneracao servicoTipoDetalheRemuneracao, ServicoCargo servicoCargo,
			ServicoRemuneracaoServidor servicoRemuneracaoServidor, ServicoPesquisa servicoPesquisa,
			ServicoNewsletter servicoNewsletter) {
		super();
		this.servicoTipoDetalheRemuneracao = servicoTipoDetalheRemuneracao;
		this.servicoCargo = servicoCargo;
		this.servicoRemuneracaoServidor = servicoRemuneracaoServidor;
		this.servicoPesquisa = servicoPesquisa;
		this.servicoNewsletter = servicoNewsletter;
	}

	@RequestMapping("/")
	public ModelAndView index(PesquisaTO pesquisa) {
		ModelAndView modelAndView = new ModelAndView("index");

		modelAndView.addObject("pesquisa", pesquisa);

		String referencia = pesquisa.getReferencia();
		if (referencia == null) {
			referencia = servicoRemuneracaoServidor.ultimaReferencia();
		}
		modelAndView.addObject("filtros", getFiltros(referencia));

		return modelAndView;
	}

	@RequestMapping("/newsletter")
	public @ResponseBody String cadastrarNewsletter(String email) {
		try {
			servicoNewsletter.salvar(new Newsletter(email));
			return "OK";
		} catch (Exception erro) {
			return "ERRO";
		}
	}

	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(PesquisaTO pesquisa, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("resultados");

		ResultadoPesquisa resultado = servicoPesquisa.pesquisar(pesquisa);
		modelAndView.addObject("resultado", resultado);

		String referencia = pesquisa.getReferencia();
		if (referencia == null) {
			referencia = servicoRemuneracaoServidor.ultimaReferencia();
		}
		modelAndView.addObject("filtros", getFiltros(referencia));

		return modelAndView;
	}

	private FiltrosTO getFiltros(String referencia) {
		FiltrosTO filtrosTO = new FiltrosTO();
		filtrosTO.setCargos(servicoCargo.todos());
		filtrosTO.setReferencias(servicoRemuneracaoServidor.referencias());
		filtrosTO.setPisoSalario(servicoRemuneracaoServidor.pisoSalarial(referencia));
		filtrosTO.setTetoSalario(servicoRemuneracaoServidor.tetoSalarial(referencia));
		filtrosTO.setTiposRendimentos(servicoTipoDetalheRemuneracao.rendimentos());
		filtrosTO.setTiposDescontos(servicoTipoDetalheRemuneracao.descontos());
		return filtrosTO;
	}

	private static String[] fields = { "ser.nome", "ser.nome", "rem.cargo.nome", "rem.totalBruto" };

	@RequestMapping(value = "/get", produces = "application/json; charset=UTF-8")
	public @ResponseBody TabelaTO<LinhaTO> getSalarios(PesquisaTO pesquisa,
			@RequestParam(name = "draw", required = false) Integer draw,
			@RequestParam(name = "start", required = false, defaultValue = "0") Integer startRow,
			@RequestParam(name = "length", required = false, defaultValue = "10") Integer numberOfRows,
			@RequestParam(name = "search[value]", required = false, defaultValue = "") String filter,
			@RequestParam(name = "order[0][column]", required = false, defaultValue = "1") Integer columnToOrder,
			@RequestParam(name = "order[0][dir]", required = false, defaultValue = "asc") String columnOrder) {

		// Ordering.
		FilterCriteria filterCriteria = new FilterCriteria();
		if (columnToOrder < fields.length) {
			String fieldName = fields[columnToOrder];
			filterCriteria.addOrderBy(fieldName);
			filterCriteria.setOrder(
					columnOrder.equals("asc") ? FilterCriteria.Order.ASCENDING : FilterCriteria.Order.DESCENDING);
		}

		// Filtering.
		if (filter != null && !filter.equals("")) {
			filterCriteria.setFilter(filter);
		}

		// Search.
		Page<EntradaResultado> entradasResultado = null;
		filterCriteria.setFilterBy(Arrays.asList(fields));
		filterCriteria.setInitialRow(startRow); // page number
		filterCriteria.setNumberOfRows(numberOfRows); // page size
		entradasResultado = servicoPesquisa.pesquisar(pesquisa, filterCriteria);

		// Generate a JSON.
		TabelaTO<LinhaTO> tabelaResultado = new TabelaTO<>();
		tabelaResultado.setDraw(draw);
		tabelaResultado.setRecordsTotal(servicoPesquisa.pesquisar(pesquisa).getQuantidade());
		tabelaResultado.setRecordsFiltered(servicoPesquisa.contar(pesquisa, filterCriteria).intValue());

		for (EntradaResultado entrada : entradasResultado) {
			LinhaTO linhaTO = new LinhaTO();
			linhaTO.setId(entrada.getId());
			linhaTO.setNome(entrada.getNome());
			linhaTO.setCargo(entrada.getCargo());
			linhaTO.setDemonstrativo(entrada.getDemonstrativo());
			linhaTO.setSalario(entrada.getSalario());
			tabelaResultado.addEntry(linhaTO);
		}

		return tabelaResultado;
	}

}
