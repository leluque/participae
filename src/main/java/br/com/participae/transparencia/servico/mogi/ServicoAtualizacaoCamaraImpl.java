package br.com.participae.transparencia.servico.mogi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.Cargo;
import br.com.participae.transparencia.dominio.TipoDetalheRemuneracao;
import br.com.participae.transparencia.erro.ErroCarregamentoDadosExistentes;
import br.com.participae.transparencia.erro.ErroCarregandoNovosDados;
import br.com.participae.transparencia.servico.ServicoCargo;
import br.com.participae.transparencia.servico.ServicoCidade;
import br.com.participae.transparencia.servico.ServicoTipoDetalheRemuneracao;

@Service
public class ServicoAtualizacaoCamaraImpl extends ServicoAtualizacaoMogi implements ServicoAtualizacaoCamara {

	private ServicoCargo servicoCargo;
	private ServicoTipoDetalheRemuneracao servicoTipoDetalheRemuneracao;
	private static String[] CARGOS = { "ASSESSOR ESPECIAL PARLAMENTAR", "ASSESSOR PARA ASSUNTOS POLITICO-LEGISLATIVOS",
			"ASSISTENTE PARLAMENTAR", "ATENDENTE DE GABINETE", "AUXILIAR DE APOIO ADMINISTRATIVO",
			"CHEFE DA ASSESSORIA LEGISLATIVA", "CHEFE DE DIVISÃO", "CONSULTOR DE ASSUNTOS GOVERNAMENTAIS",
			"DIRETOR DE DEPARTAMENTO", "ENCARREGADO DE SETOR", "ENGENHEIRO DE TELECOMUNICAÇÕES", "MOTORISTA",
			"OFICIAL LEGISLATIVO", "PRESIDENTE DA CAMARA", "PROCURADOR JURIDICO", "PROCURADOR JURIDICO CHEFE",
			"PROGRAMADOR DE COMPUTADOR", "REPORTER FOTOGRAFICO", "SECRETARIA GERAL LEGISLATIVO",
			"SECRETARIO GERAL ADMINISTRATIVO", "SUPLENTE VEREADOR", "TECNICO EM INFORMATICA", "TELEFONISTA",
			"TESOUREIRO", "VEREADOR", "VIGILANTE LEGISLATIVO" };
	private static String[] itens = { "VENCIMENTO BASE", "OUTROS VENCIMENTOS", "TOTAL BRUTO", "PREVIDENCIA", "IRRF",
			"OUTROS DESCONTOS", "TOTAL DE DESCONTOS", "TOTAL LIQUIDO" };
	private static String[] meses = { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov",
			"Dez" };
	private static String regexNumero = "[+-]?[0-9]*[.]?[0-9]+[,]?[0-9]*";
	private static String regexTexto = "[A-Za-z -/]*";
	private static String regexSeparador = "[ \t]?";
	private static String regex = String.format("(%s)%s(%s)%s(%s)%s(%s)%s(%s)%s(%s)%s(%s)%s(%s)%s(%s)%s(%s)", regexNumero,regexSeparador,
			regexTexto, regexSeparador, regexNumero, regexSeparador, regexNumero, regexSeparador, regexNumero, regexSeparador, regexNumero, regexSeparador, regexNumero, regexSeparador, regexNumero, regexSeparador, regexNumero,
			regexSeparador, regexNumero);

	public ServicoAtualizacaoCamaraImpl(ServicoCidade servicoCidade, ServicoCargo servicoCargo,
			ServicoTipoDetalheRemuneracao servicoTipoDetalheRemuneracao) {
		super(servicoCidade);
		this.servicoCargo = servicoCargo;
		this.servicoTipoDetalheRemuneracao = servicoTipoDetalheRemuneracao;
	}

	private String getConteudoArquivoPDF(File arquivo) throws FileNotFoundException, IOException {
		PDFParser parser = new PDFParser((RandomAccessRead) new RandomAccessFile(arquivo, "r"));
		parser.parse();
		COSDocument documentoEmMemoria = parser.getDocument();
		PDFTextStripper processadorDocumento = new PDFTextStripper();
		PDDocument documento = new PDDocument(documentoEmMemoria);
		return processadorDocumento.getText(documento);
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
			String conteudoArquivoPDF = getConteudoArquivoPDF(
					new ClassPathResource("dados/mc-sp/ultima_referencia.pdf").getFile());

			BufferedReader entrada = new BufferedReader(new StringReader(conteudoArquivoPDF));
			String linha;
			String referencia = null;
			while ((linha = entrada.readLine()) != null) {
				if (linha.startsWith("REFERENCIA ") && null == referencia) {
					referencia = linha.substring("REFERENCIA ".length());
					String[] partes = referencia.split("\\.");
					referencia = meses[Integer.valueOf(partes[0]) - 1] + "/" + partes[1];
				} else {
					// Procura linha por linha por aquelas que começam com dígitos.
					// Elas contêm dados de pagamento de algum funcionário.
					verify: if (Character.isDigit(linha.charAt(0))) {
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(linha);
						if (matcher.find()) {
							String nomeECargo = matcher.group(2);
							String nome = null;
							String cargoCamara = null;
							for(String cargo: CARGOS) {
								int posicao = nomeECargo.indexOf(cargo);
								if(posicao >= 0) {
									nome = nomeECargo.substring(0, posicao);
									cargoCamara = nomeECargo.substring(posicao);
									break;
								}
							}
							System.out.printf("%s ; %s ; %s ; %s\n", matcher.group(1), nome, cargoCamara, matcher.group(2));
						} else {
							linha += entrada.readLine();
							break verify;
						}
					}
				}
			}

			return 0;
		} catch (Exception erro) {
			throw new ErroCarregandoNovosDados("Ocorreu um erro ao tentar carregar os novos dados ou ao salva-los.",
					erro);
		}
	}

}
