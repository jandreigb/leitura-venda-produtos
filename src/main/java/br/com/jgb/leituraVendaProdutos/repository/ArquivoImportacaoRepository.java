package br.com.jgb.leituraVendaProdutos.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.jgb.leituraVendaProdutos.entity.ArquivoImportacao;

/**
 * Repositório de dados para os arquicos de importação
 */
public interface ArquivoImportacaoRepository extends CrudRepository<ArquivoImportacao, Long> {

	@Query("SELECT a FROM ArquivoImportacao a WHERE a.name = :nomeArquivo")
	ArquivoImportacao buscaPorNome(@Param("nomeArquivo") String nomeArquivo);
}