package br.com.participae.transparencia.servico.mogi;

import br.com.participae.transparencia.erro.ErroCarregamentoDadosExistentes;
import br.com.participae.transparencia.erro.ErroCarregandoNovosDados;

public interface ServicoAtualizacaoPrefeitura {

	public long atualizarRemuneracao() throws ErroCarregamentoDadosExistentes, ErroCarregandoNovosDados;

}
