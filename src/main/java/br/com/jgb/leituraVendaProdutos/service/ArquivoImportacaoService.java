package br.com.jgb.leituraVendaProdutos.service;

import org.springframework.stereotype.Service;

import br.com.jgb.leituraVendaProdutos.entity.ArquivoImportacao;
import br.com.jgb.leituraVendaProdutos.repository.ArquivoImportacaoRepository;

/**
 * Regras de negócio para os arquivos de importação
 *
 */
@Service
public class ArquivoImportacaoService {

	private ArquivoImportacaoRepository arquivoImportacaoRepository;

	public ArquivoImportacaoService(ArquivoImportacaoRepository arquivoImportacaoRepository) {
		this.arquivoImportacaoRepository = arquivoImportacaoRepository;
	}

	/**
	 * Busca por nome
	 * 
	 * @param nomeArquivo
	 */
	public ArquivoImportacao buscaPorNome(String nomeArquivo) {

		return arquivoImportacaoRepository.buscaPorNome(nomeArquivo);
	}

	/**
	 * Salva arquivo de importação/atualiza índice de registros lidos
	 * 
	 * @param arquivoImportacao
	 */
	public void save(ArquivoImportacao arquivoImportacao) {
		arquivoImportacaoRepository.save(arquivoImportacao);
	}
}