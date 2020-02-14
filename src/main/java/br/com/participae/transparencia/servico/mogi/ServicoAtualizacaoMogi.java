package br.com.participae.transparencia.servico.mogi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.participae.transparencia.dominio.Cidade;
import br.com.participae.transparencia.servico.ServicoAtualizacao;
import br.com.participae.transparencia.servico.ServicoCidade;
import br.com.participae.transparencia.to.EntradaServidorCSV;

public class ServicoAtualizacaoMogi extends ServicoAtualizacao {

	private ServicoCidade servicoCidade;

	public ServicoAtualizacaoMogi(ServicoCidade servicoCidade) {
		this.servicoCidade = servicoCidade;
	}

	/**
	 * Retorna os servidores constantes no arquivo CSV disponibilizado pela
	 * prefeitura.
	 * 
	 * @param csv
	 *            O conteudo do arquivo CSV.
	 * @return Uma array de entradas de servidores.
	 * @throws IOException
	 *             Caso algum erro ocorra.
	 */
	protected EntradaServidorCSV[] getServidores(String csv) throws IOException {
		List<EntradaServidorCSV> retorno = new ArrayList<>();
		// Le o conteudo do arquivo csv com as informacoes dos servidores.
		BufferedReader in = new BufferedReader(new StringReader(csv));
		String linha;
		while ((linha = in.readLine()) != null) {
			String[] partes = linha.split(";");
			retorno.add(new EntradaServidorCSV(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim()));
		}
		return retorno.toArray(new EntradaServidorCSV[retorno.size()]);
	}

	/**
	 * Retorna a cidade relacionada a este servico de atualizacao.
	 * 
	 * @return A cidade relacionada a este servico de atualizacao.
	 */
	protected Cidade getCidade() {
		Cidade cidade = servicoCidade.pesquisarPorSigla("MC-SP");
		if (cidade == null) {
			cidade = new Cidade();
			cidade.setNome("Mogi das Cruzes");
			cidade.setUf("SP");
			cidade.setSigla("MC-SP");
			cidade.setDataCriacao(new Date());
			cidade.setUltimaAtualizacao(new Date());
			cidade.setHabilitado(true);
			servicoCidade.salvar(cidade);
		}
		return cidade;
	}

}
