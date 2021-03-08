package br.com.jgb.leituraVendaProdutos.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Entidade do produto
 *
 */
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String product;
	private Long quantity;

	/**
	 * Valor recuperado no arquivo de produtos em formato String
	 */
	@Transient
	private String price;

	private Double priceValue;
	private String type;
	private String industry;
	private String origin;

	public Produto() {
		super();
	}

	/**
	 * Valor total da campra
	 * 
	 * @return
	 */
	@Transient
	public BigDecimal getTotalValue() {

		if (this.priceValue == null || this.quantity == null) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(this.priceValue).multiply(new BigDecimal(this.quantity)).setScale(2,
				RoundingMode.HALF_UP);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {

		this.price = price;

		// quando o preço é definido em String, deve ser convertido para o BigDecimal
		if (this.price != null && !this.price.equals("")) {

			this.priceValue = Double.parseDouble(this.price.replace("$", ""));
		}
	}

	public Double getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(Double priceValue) {
		this.priceValue = priceValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((industry == null) ? 0 : industry.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((priceValue == null) ? 0 : priceValue.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (priceValue == null) {
			if (other.priceValue != null)
				return false;
		} else if (!priceValue.equals(other.priceValue))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", product=" + product + ", quantity=" + quantity + ", price=" + price
				+ ", priceValue=" + priceValue + ", type=" + type + ", industry=" + industry + ", origin=" + origin
				+ "]";
	}
}