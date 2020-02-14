package br.com.participae.transparencia.servico.mogi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.participae.transparencia.dominio.Cargo;
import br.com.participae.transparencia.dominio.Cidade;
import br.com.participae.transparencia.dominio.Servidor;
import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;
import br.com.participae.transparencia.dominio.TipoServidor;
import br.com.participae.transparencia.erro.ErroCarregamentoDadosExistentes;
import br.com.participae.transparencia.erro.ErroCarregandoNovosDados;
import br.com.participae.transparencia.servico.ServicoCargo;
import br.com.participae.transparencia.servico.ServicoCidade;
import br.com.participae.transparencia.servico.ServicoServidor;
import br.com.participae.transparencia.servico.ServicoTipoDetalheRemuneracao;
import br.com.participae.transparencia.to.EntradaServidorCSV;
import br.com.participae.transparencia.to.NovosRegistros;
import br.com.participae.transparencia.to.RemuneracaoServidor;
import br.com.participae.transparencia.util.HttpDownloadUtility;

@Service
public class ServicoAtualizacaoPrefeituraImpl extends ServicoAtualizacaoMogi implements ServicoAtualizacaoPrefeitura {

	private ServicoServidor servicoServidor;
	private ServicoCargo servicoCargo;
	private ServicoTipoDetalheRemuneracao servicoTipoDetalheRemuneracao;
	private static final String URL_GERAL = "http://www.licitacao.pmmc.com.br/Transparencia/vencimentos2?csv=true";
	private static final String URL_DETALHES = "http://www.licitacao.pmmc.com.br/Transparencia/detalhamento?rgf=";

	@Autowired
	public ServicoAtualizacaoPrefeituraImpl(ServicoCidade servicoCidade, ServicoServidor servicoServidor,
			ServicoCargo servicoCargo, ServicoTipoDetalheRemuneracao servicoTipoDetalheRemuneracao) {
		super(servicoCidade);
		this.servicoServidor = servicoServidor;
		this.servicoCargo = servicoCargo;
		this.servicoTipoDetalheRemuneracao = servicoTipoDetalheRemuneracao;
	}

	public long atualizarRemuneracao() throws ErroCarregamentoDadosExistentes, ErroCarregandoNovosDados {
		Map<String, Cargo> cargos = new HashMap<>();
		Map<String, TipoDetalheRemuneracao> tipos = new HashMap<>();

		// Tenta atualizar os dados locais.
		try {
			// Carrega os cargos do banco de dados.
			List<Cargo> listaCargos = this.servicoCargo.todos();
			for (Cargo cargo : listaCargos) {
				cargos.put(cargo.getNome(), cargo);
			}

			// Carrega os tipos de remuneracao do banco de dados.
			List<TipoDetalheRemuneracao> listaTipos = this.servicoTipoDetalheRemuneracao.todos();
			for (TipoDetalheRemuneracao tipoDetalheRemuneracao : listaTipos) {
				tipos.put(tipoDetalheRemuneracao.getNome(), tipoDetalheRemuneracao);
			}
		} catch (Exception erro) {
			throw new ErroCarregamentoDadosExistentes(
					"Ocorreu um erro ao tentar carregar os cargos e/ou os tipos ja cadastrados.", erro);
		}

		try {
			// Baixa lista de servidores com seu codigo, nome, funcao e salario.
			String csv = HttpDownloadUtility.downloadFile(URL_GERAL);

			// Recupera o codigo de cada servidor.
			long quantosNovos = 0;
			Cidade cidade = getCidade();
			EntradaServidorCSV[] entradasServidores = this.getServidores(csv);
			for (EntradaServidorCSV entradaServidor : entradasServidores) {
				String codigo = entradaServidor.getCodigo();
				// Procura um servidor com o codigo atual. Se nao encontrar, cria um novo.
				Servidor servidor = servicoServidor.localizarPorCodigo(entradaServidor.getCodigo());
				if (servidor == null) {
					quantosNovos++;
					servidor = new Servidor();
					servidor.setDataCriacao(new Date());
					servidor.setCodigo(entradaServidor.getCodigo());
					servidor.setNome(entradaServidor.getNome());
				}
				servidor.setTipo(TipoServidor.EXECUTIVO);
				servidor.setTipoAdicional("PREFEITURA");
				servidor.setCidade(cidade);

				// Baixa os detalhes de remuneracao do servidor.
				String detalhesServidor = HttpDownloadUtility.downloadFile(URL_DETALHES + codigo);

				RemuneracaoServidor remuneracaoServidor = new Gson().fromJson(detalhesServidor,
						RemuneracaoServidor.class);
				NovosRegistros novosRegistros = servidor.preencherCom(cargos, tipos, remuneracaoServidor);
				// Cadastra novos cargos e tipos.
				for (Cargo cargo : novosRegistros.getCargos()) {
					servicoCargo.salvar(cargo);
					cargos.put(cargo.getNome(), cargo);
				}
				for (TipoDetalheRemuneracao tipo : novosRegistros.getTipos()) {
					servicoTipoDetalheRemuneracao.salvar(tipo);
					tipos.put(tipo.getNome(), tipo);
				}
				// Salva o servidor.
				br.com.participae.transparencia.dominio.RemuneracaoServidor remuneracao = novosRegistros.getRemuneracao();
				remuneracao.setDemonstrativo(gerarFolhaServidor(remuneracao));
				servicoServidor.salvar(servidor);
			}

			return quantosNovos;
		} catch (Exception erro) {
			throw new ErroCarregandoNovosDados("Ocorreu um erro ao tentar carregar os novos dados ou ao salva-los.",
					erro);
		}
	}

}
