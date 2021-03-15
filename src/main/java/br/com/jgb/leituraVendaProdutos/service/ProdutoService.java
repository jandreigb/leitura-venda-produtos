package br.com.jgb.leituraVendaProdutos.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.jgb.leituraVendaProdutos.dto.CalculoVendaProdutoLojistas;
import br.com.jgb.leituraVendaProdutos.entity.ArquivoImportacao;
import br.com.jgb.leituraVendaProdutos.entity.Produto;
import br.com.jgb.leituraVendaProdutos.exception.BadRequestException;
import br.com.jgb.leituraVendaProdutos.repository.ProdutoRepository;

/**
 * Regras de negócio para os produtos
 *
 */
@Service
public class ProdutoService {

	Logger logger = LoggerFactory.getLogger(ProdutoService.class);

	private ProdutoRepository produtoRepository;
	private ArquivoImportacaoService arquivoImportacaoService;

	public ProdutoService(ProdutoRepository produtoRepository, ArquivoImportacaoService arquivoImportacaoService) {
		this.produtoRepository = produtoRepository;
		this.arquivoImportacaoService = arquivoImportacaoService;
	}

	/**
	 * Salva uma lista de produtos
	 * 
	 * @param produtos
	 * @param nomeArquivo
	 */
	public void salvar(List<Produto> produtos, String nomeArquivo) {

		logger.trace("-> Início da leitura do arquivo: " + nomeArquivo);

		// não pode ter produtos repetidos na lista
		List<Produto> listaNaoDuplicada = produtos.stream().distinct().collect(Collectors.toList());
		long totalRegistros = listaNaoDuplicada.size();
		long indiceSalvo = 0;

		logger.trace("--> Total de registros do arquivo " + nomeArquivo + ": " + totalRegistros);

		// verifica se o arquivo já foi lido para continuar
		ArquivoImportacao arquivoImportacao = arquivoImportacaoService.buscaPorNome(nomeArquivo);

		if (arquivoImportacao == null) {
			arquivoImportacao = new ArquivoImportacao(nomeArquivo, 0L);
		}

		long qtdeRegistrosLidos = arquivoImportacao.getRecordsRead();

		for (Produto produto : listaNaoDuplicada) {

			indiceSalvo++;

			// precisa dar continuidade de onde parou no arquivo
			if (indiceSalvo > qtdeRegistrosLidos) {

				logger.trace("--> Salvando produto do arquivo " + nomeArquivo + ", " + indiceSalvo + " de "
						+ totalRegistros + " : " + produto);
				produtoRepository.save(produto);

				logger.trace("--> Salvando arquivo de importação: " + arquivoImportacao);
				arquivoImportacao.setRecordsRead(indiceSalvo);
				arquivoImportacaoService.save(arquivoImportacao);

			} else {

				logger.trace("--> Produto não foi salvo, pois já tem leitura para esse índice. " + nomeArquivo + ", "
						+ indiceSalvo + " de " + totalRegistros + " : " + produto);
			}
		}

		logger.trace("-> Fim da leirura do arquivo: " + nomeArquivo);
	}

	/**
	 * Calcula a venda de um produtos para lojistas
	 * 
	 * @param produto
	 * @param quantidadeLojas
	 * @return
	 * @throws Exception
	 */
	public CalculoVendaProdutoLojistas calcularVendaProdutoParaLojistas(String produto, Integer quantidadeLojas)
			throws Exception {

		// validações
		if (quantidadeLojas == null || quantidadeLojas <= 0) {
			throw new BadRequestException("Quantidade de lojas inválida!");
		}

		if (produto == null || produto.equals("")) {
			throw new BadRequestException("Produto inválido!");
		}

		// busca produto
		List<Produto> produtos = produtoRepository.buscaPorNome(produto);

		if (produto == null || produtos.size() == 0) {
			throw new BadRequestException("Produto não encontrado!");
		}

		CalculoVendaProdutoLojistas calculoVendaProdutoLojistas = new CalculoVendaProdutoLojistas(produtos,
				quantidadeLojas);

		// cada produto deve ser divido cada lojista
		if (produtos != null && produtos.size() > 0) {

			for (Produto p : produtos) {

				// quantidade do produto para cada lojista
				Long quantidadePorLojista = new BigDecimal(p.getQuantity())
						.divide(new BigDecimal(quantidadeLojas), 0, RoundingMode.HALF_DOWN).longValue();

				// precisa ir diminuindo do total, pois a divisão pode não ser exata
				Long totalDistribuido = 0L;

				for (int i = 1; i <= quantidadeLojas; i++) {

					totalDistribuido = totalDistribuido + quantidadePorLojista;

					// é a última distribuição, precisa pegar o restante desconsiderando o que foi
					// adicionado no total
					if (p.getQuantity() <= totalDistribuido) {
						quantidadePorLojista = p.getQuantity() - (totalDistribuido - quantidadePorLojista);
					}

					calculoVendaProdutoLojistas.adicionarProdutoLogista(i, p.getProduct(),
							new BigDecimal(p.getPriceValue()), quantidadePorLojista);
				}
			}
		}

		return calculoVendaProdutoLojistas;
	}
}