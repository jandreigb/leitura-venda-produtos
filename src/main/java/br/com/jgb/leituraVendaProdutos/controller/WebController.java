package br.com.jgb.leituraVendaProdutos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.leituraVendaProdutos.dto.CalculoVendaProdutoLojistas;
import br.com.jgb.leituraVendaProdutos.entity.ArquivoImportacao;
import br.com.jgb.leituraVendaProdutos.entity.Produto;
import br.com.jgb.leituraVendaProdutos.repository.ArquivoImportacaoRepository;
import br.com.jgb.leituraVendaProdutos.repository.ProdutoRepository;
import br.com.jgb.leituraVendaProdutos.service.ProdutoService;

@RestController
public class WebController {

	@Autowired
	private ArquivoImportacaoRepository arquivoImportacaoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping("/")
	public String index() {
		return "Web Service funcionando!";
	}

	/**
	 * Lista arquivos importados com os produtos
	 * 
	 * @return
	 */
	@GetMapping("/arquivos-importacao")
	List<ArquivoImportacao> arquivosImportacao() {
		return (List<ArquivoImportacao>) arquivoImportacaoRepository.findAll();
	}

	/**
	 * Remove os dados importados
	 */
	@DeleteMapping("/arquivos-importacao")
	void removerArquivosImportacao() {
		arquivoImportacaoRepository.deleteAll();
		produtoRepository.deleteAll();
	}

	/**
	 * Lista os produtos pelo nome
	 * 
	 * @param nome
	 * @return
	 */
	@GetMapping("/produto/{nome}")
	List<Produto> produtosPorNome(@PathVariable String nome) {
		return (List<Produto>) produtoRepository.buscaPorNome(nome);
	}

	/**
	 * Calcula a venda de um produtos para lojistas
	 * 
	 * @param produto
	 * @param quantidadeLojas
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/calculo-venda/{produto}/{quantidadeLojas}")
	CalculoVendaProdutoLojistas calularVendaProdutoLojistas(@PathVariable String produto,
			@PathVariable Integer quantidadeLojas) throws Exception {

		return produtoService.calcularVendaProdutoParaLojistas(produto, quantidadeLojas);
	}
}