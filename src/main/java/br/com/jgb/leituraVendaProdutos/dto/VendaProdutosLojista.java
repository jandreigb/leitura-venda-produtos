package br.com.jgb.leituraVendaProdutos.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VendaProdutosLojista {

	private final String NOME_PADRAO_LOGISTA = "Lojista";

	private String nomeLojista;
	private Long quantidade;
	private BigDecimal valor;
	private BigDecimal precoMedio;
	private List<VendaProduto> produtos;

	/**
	 * Criação com nome padrão do lojista
	 * 
	 * @param index
	 */
	public VendaProdutosLojista(int index) {
		this.nomeLojista = getNomePadrao(index);
		this.produtos = new ArrayList<VendaProduto>();
	}

	/**
	 * Adiciona produto
	 * 
	 * @param produto
	 * @param preco
	 * @param quantidade
	 */
	@JsonIgnore
	public void adicionaProduto(String produto, BigDecimal preco, Long quantidade) {
		this.produtos.add(new VendaProduto(produto, quantidade, preco));
	}

	/**
	 * Recupera nome padrão do lojista pelo índice
	 * 
	 * @param index
	 * @return
	 */
	public String getNomePadrao(int index) {
		return NOME_PADRAO_LOGISTA + " " + index;
	}

	public String getNomeLojista() {
		return nomeLojista;
	}

	public void setNomeLojista(String nomeLojista) {
		this.nomeLojista = nomeLojista;
	}

	/**
	 * Calculo pelos produtos
	 * 
	 * @return
	 */
	public Long getQuantidade() {

		if (produtos != null) {
			quantidade = this.produtos.stream().mapToLong(x -> x.getQuantidade()).sum();
		}

		return quantidade;
	}

	/**
	 * Calculo pelas produtos
	 * 
	 * @return
	 */
	public BigDecimal getValor() {

		if (produtos != null) {
			Double valorD = this.produtos.stream().mapToDouble(x -> x.getValor().doubleValue()).sum();
			valor = new BigDecimal(valorD).setScale(2, RoundingMode.HALF_UP);
		}

		return valor;
	}

	/**
	 * Calcula pelos produtos
	 * 
	 * @return
	 */
	public BigDecimal getPrecoMedio() {

		if (produtos != null) {
			precoMedio = getValor().divide(new BigDecimal(getQuantidade()), 2, RoundingMode.HALF_UP).setScale(2);
		}

		return precoMedio;
	}

	public List<VendaProduto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<VendaProduto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeLojista == null) ? 0 : nomeLojista.hashCode());
		result = prime * result + ((precoMedio == null) ? 0 : precoMedio.hashCode());
		result = prime * result + ((produtos == null) ? 0 : produtos.hashCode());
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
		VendaProdutosLojista other = (VendaProdutosLojista) obj;
		if (nomeLojista == null) {
			if (other.nomeLojista != null)
				return false;
		} else if (!nomeLojista.equals(other.nomeLojista))
			return false;
		if (precoMedio == null) {
			if (other.precoMedio != null)
				return false;
		} else if (!precoMedio.equals(other.precoMedio))
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
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
		return "VendaProdutosLojista [nomeLojista=" + nomeLojista + ", quantidade=" + quantidade + ", valor=" + valor
				+ ", precoMedio=" + precoMedio + ", produtos=" + produtos + "]";
	}
}