package br.com.jgb.leituraVendaProdutos.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Registro de venda de produtos
 *
 */
public class VendaProduto {

	private String produto;
	private Long quantidade;
	private BigDecimal preco;
	private BigDecimal valor;

	public VendaProduto(String produto, Long quantidade, BigDecimal preco) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {

		if (preco != null) {
			preco.setScale(2, RoundingMode.HALF_UP);
		}

		this.preco = preco;
	}

	/**
	 * Calculo do valor (quantidade X preço)
	 * 
	 * @return
	 */
	public BigDecimal getValor() {

		if (quantidade != null && preco != null) {
			valor = new BigDecimal(quantidade).multiply(preco).setScale(2, RoundingMode.HALF_UP);
		}

		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		VendaProduto other = (VendaProduto) obj;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VendaProduto [produto=" + produto + ", quantidade=" + quantidade + ", preco=" + preco + ", valor="
				+ valor + "]";
	}
}