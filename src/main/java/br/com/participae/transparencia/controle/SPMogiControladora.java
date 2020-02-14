package br.com.participae.transparencia.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.participae.transparencia.erro.ErroCarregamentoDadosExistentes;
import br.com.participae.transparencia.erro.ErroCarregandoNovosDados;
import br.com.participae.transparencia.servico.mogi.ServicoAtualizacaoCamara;
import br.com.participae.transparencia.servico.mogi.ServicoAtualizacaoPrefeitura;
import br.com.participae.transparencia.servico.mogi.ServicoAtualizacaoSEMAE;

@Controller
@RequestMapping("/admin/mogi")
public class SPMogiControladora {

	private ServicoAtualizacaoPrefeitura servicoAtualizacao;
	private ServicoAtualizacaoSEMAE servicoAtualizacaoSEMAE;
	private ServicoAtualizacaoCamara servicoAtualizacaoCamara;

	public SPMogiControladora(ServicoAtualizacaoPrefeitura servicoAtualizacao,
			ServicoAtualizacaoSEMAE servicoAtualizacaoSEMAE, ServicoAtualizacaoCamara servicoAtualizacaoCamara) {
		super();
		this.servicoAtualizacao = servicoAtualizacao;
		this.servicoAtualizacaoSEMAE = servicoAtualizacaoSEMAE;
		this.servicoAtualizacaoCamara = servicoAtualizacaoCamara;
	}

	@RequestMapping("/atualizarPrefeitura")
	public ModelAndView atualizarRemuneracao() {
		try {
			this.servicoAtualizacao.atualizarRemuneracao();
		} catch (ErroCarregamentoDadosExistentes | ErroCarregandoNovosDados e) {
			e.printStackTrace();
			return new ModelAndView("/admin/erroAtualizacao");
		}

		return new ModelAndView("/admin/remuneracaoAtualizada");
	}

	@RequestMapping("/atualizarSEMAE")
	public ModelAndView atualizarSEMAE() {
		try {
			this.servicoAtualizacaoSEMAE.atualizarRemuneracao();
		} catch (ErroCarregamentoDadosExistentes | ErroCarregandoNovosDados e) {
			e.printStackTrace();
			return new ModelAndView("/admin/erroAtualizacao");
		}

		return new ModelAndView("/admin/remuneracaoAtualizada");
	}

	@RequestMapping("/atualizarCamara")
	public ModelAndView atualizarCamara() {
		try {
			this.servicoAtualizacaoCamara.atualizarRemuneracao();
		} catch (ErroCarregamentoDadosExistentes | ErroCarregandoNovosDados e) {
			e.printStackTrace();
			return new ModelAndView("/admin/erroAtualizacao");
		}

		return new ModelAndView("/admin/remuneracaoAtualizada");
	}

}
