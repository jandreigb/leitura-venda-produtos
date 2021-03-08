package br.com.jgb.leituraVendaProdutos.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.jgb.leituraVendaProdutos.entity.Produto;

/**
 * Objeto para representar o cálulo de venda do porduto para logistas
 */
public class CalculoVendaProdutoLojistas {

	/**
	 * Lista de produtos importados dos arquivos
	 */
	private List<Produto> produtosImportados;
	/**
	 * quantidade total dos produtos importados
	 */
	private Long quantidadeTotal;
	/**
	 * valor total dos produtos importados
	 */
	private BigDecimal valorTotal;
	/**
	 * preço médio dis produtos importados
	 */
	private BigDecimal precoMedio;
	/**
	 * divisão de venda entre os lojistas
	 */
	private List<VendaProdutosLojista> divisaoLojistas;

	public CalculoVendaProdutoLojistas(List<Produto> produtos, Integer quantidadeLojistas) {
		this.produtosImportados = produtos;

		// cria registros para cada lojista
		divisaoLojistas = new ArrayList<VendaProdutosLojista>();

		if (quantidadeLojistas != null && quantidadeLojistas > 0) {
			for (int i = 1; i <= quantidadeLojistas; i++) {

				divisaoLojistas.add(new VendaProdutosLojista(i));
			}
		}
	}

	/**
	 * Recupera objeto de venda do produto do logista pelo índice do lojista
	 * 
	 * @param index
	 * @return
	 */
	@JsonIgnore
	public VendaProdutosLojista getVendaProdutosLojistaIndice(int index) {

		return this.divisaoLojistas.stream().filter(dl -> dl.getNomeLojista().equals(dl.getNomePadrao(index)))
				.findFirst().get();
	}

	/**
	 * Adiciona produto ao logista
	 * 
	 * @param indiceLojista
	 * @param produto
	 * @param preco
	 * @param quantidade
	 */
	@JsonIgnore
	public void adicionarProdutoLogista(int indiceLojista, String produto, BigDecimal preco, Long quantidade) {

		VendaProdutosLojista vpl = this.divisaoLojistas.stream()
				.filter(dl -> dl.getNomeLojista().equals(dl.getNomePadrao(indiceLojista))).findFirst().get();

		if (vpl != null) {
			vpl.adicionaProduto(produto, preco, quantidade);
		}
	}

	/**
	 * Calculo pelos produtos
	 * 
	 * @return
	 */
	public Long getQuantidadeTotal() {

		if (produtosImportados != null) {
			quantidadeTotal = this.produtosImportados.stream().mapToLong(x -> x.getQuantity()).sum();
		}

		return quantidadeTotal;
	}

	/**
	 * Calculo pelos produtos
	 * 
	 * @return
	 */
	public BigDecimal getValorTotal() {

		if (produtosImportados != null) {
			Double valor = this.produtosImportados.stream().mapToDouble(x -> x.getTotalValue().doubleValue()).sum();
			valorTotal = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);
		}

		return valorTotal;
	}

	/**
	 * Calculo pelos produtos
	 * 
	 * @return
	 */
	public BigDecimal getPrecoMedio() {

		if (produtosImportados != null) {
			precoMedio = getValorTotal().divide(new BigDecimal(getQuantidadeTotal()), 2, RoundingMode.HALF_UP)
					.setScale(2);
		}

		return precoMedio;
	}

	public List<Produto> getProdutosImportados() {
		return produtosImportados;
	}

	public void setProdutosImportados(List<Produto> produtosImportados) {
		this.produtosImportados = produtosImportados;
	}

	public List<VendaProdutosLojista> getDivisaoLojistas() {
		return divisaoLojistas;
	}

	public void setDivisaoLojistas(List<VendaProdutosLojista> divisaoLojistas) {
		this.divisaoLojistas = divisaoLojistas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((divisaoLojistas == null) ? 0 : divisaoLojistas.hashCode());
		result = prime * result + ((precoMedio == null) ? 0 : precoMedio.hashCode());
		result = prime * result + ((produtosImportados == null) ? 0 : produtosImportados.hashCode());
		result = prime * result + ((quantidadeTotal == null) ? 0 : quantidadeTotal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculoVendaProdutoLojistas other = (CalculoVendaProdutoLojistas) obj;
		if (divisaoLojistas == null) {
			if (other.divisaoLojistas != null)
				return false;
		} else if (!divisaoLojistas.equals(other.divisaoLojistas))
			return false;
		if (precoMedio == null) {
			if (other.precoMedio != null)
				return false;
		} else if (!precoMedio.equals(other.precoMedio))
			return false;
		if (produtosImportados == null) {
			if (other.produtosImportados != null)
				return false;
		} else if (!produtosImportados.equals(other.produtosImportados))
			return false;
		if (quantidadeTotal == null) {
			if (other.quantidadeTotal != null)
				return false;
		} else if (!quantidadeTotal.equals(other.quantidadeTotal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalculoVendaProdutoLojistas [produtosImportados=" + produtosImportados + ", quantidadeTotal="
				+ quantidadeTotal + ", precoMedio=" + precoMedio + ", divisaoLojistas=" + divisaoLojistas + "]";
	}
}