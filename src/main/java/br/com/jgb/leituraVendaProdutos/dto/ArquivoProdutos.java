package br.com.jgb.leituraVendaProdutos.dto;

import java.util.List;

import br.com.jgb.leituraVendaProdutos.entity.Produto;

/**
 * Mapeamento referente ao arquivo de produtos
 *
 */
public class ArquivoProdutos {

	private List<Produto> data;

	public List<Produto> getData() {
		return data;
	}

	public void setData(List<Produto> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ArquivoProdutos [data=" + data + "]";
	}
}
