package br.com.jgb.leituraVendaProdutos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jgb.leituraVendaProdutos.dto.CalculoVendaProdutoLojistas;
import br.com.jgb.leituraVendaProdutos.entity.Produto;

/**
 * Testes para gerenciamento de produtos
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProdutoServiceTest {

	@Autowired
	private ProdutoService produtoService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Quantidade de lojas nulo
	 */
	@Test
	void testCalcularVendaProdutoParaLojistasQuantidadeLojasNull() {

		Exception exception = assertThrows(Exception.class, () -> {
			produtoService.calcularVendaProdutoParaLojistas("nome produto", null);
		});

		String expectedMessage = "Quantidade de lojas inválida!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Quantidade de lojas menor ou igual a zero
	 */
	@Test
	void testCalcularVendaProdutoParaLojistasQuantidadeLojasZero() {

		Exception exception = assertThrows(Exception.class, () -> {
			produtoService.calcularVendaProdutoParaLojistas("nome produto", 0);
		});

		String expectedMessage = "Quantidade de lojas inválida!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Produto inválido ou nulo
	 */
	@Test
	void testCalcularVendaProdutoParaLojistasProdutoInvalido() {

		Exception exception = assertThrows(Exception.class, () -> {
			produtoService.calcularVendaProdutoParaLojistas(null, 1);
		});

		String expectedMessage = "Produto inválido!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Produto inexistente
	 */
	@Test
	void testCalcularVendaProdutoParaLojistasProdutoNaoExiste() {

		Exception exception = assertThrows(Exception.class, () -> {
			produtoService.calcularVendaProdutoParaLojistas("NAO_EXISTE", 1);
		});

		String expectedMessage = "Produto não encontrado!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Informações do cálculo dos produtos
	 */
	@Test
	void testCalcularVendaProdutoParaLojistas() {

		CalculoVendaProdutoLojistas calculo;

		try {
			calculo = produtoService.calcularVendaProdutoParaLojistas("EMMS", 2);

		} catch (Exception e) {

			calculo = new CalculoVendaProdutoLojistas(new ArrayList<Produto>(), 0);
		}

		assertEquals(14, calculo.getProdutosImportados().size());
		assertEquals(730, calculo.getQuantidadeTotal());
		assertEquals(new BigDecimal(3767.15).setScale(2, RoundingMode.HALF_UP), calculo.getValorTotal());
		assertEquals(new BigDecimal(5.16).setScale(2, RoundingMode.HALF_UP), calculo.getPrecoMedio());
		assertEquals("Lojista 1", calculo.getDivisaoLojistas().get(0).getNomeLojista());
		assertEquals(361, calculo.getDivisaoLojistas().get(0).getQuantidade());
		assertEquals(new BigDecimal(1855.60).setScale(2, RoundingMode.HALF_UP),
				calculo.getDivisaoLojistas().get(0).getValor());
	}
}
