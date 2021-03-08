package br.com.jgb.leituraVendaProdutos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.jgb.leituraVendaProdutos.entity.Produto;

/**
 * Repositório de dados para os produtos
 */
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

	@Query("SELECT p FROM Produto p WHERE p.product = :nome")
	List<Produto> buscaPorNome(@Param("nome") String nome);
}