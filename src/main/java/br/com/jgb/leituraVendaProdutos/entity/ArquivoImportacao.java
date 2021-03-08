package br.com.jgb.leituraVendaProdutos.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entidade referente a importação dos dados pelo arquivo
 */
@Entity
public class ArquivoImportacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	/**
	 * Quantidade de registros lidos
	 */
	private Long recordsRead;

	public ArquivoImportacao() {
		super();
	}

	public ArquivoImportacao(String name, Long recordsRead) {
		super();
		this.name = name;
		this.recordsRead = recordsRead;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRecordsRead() {
		return recordsRead;
	}

	public void setRecordsRead(Long recordsRead) {
		this.recordsRead = recordsRead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((recordsRead == null) ? 0 : recordsRead.hashCode());
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
		ArquivoImportacao other = (ArquivoImportacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (recordsRead == null) {
			if (other.recordsRead != null)
				return false;
		} else if (!recordsRead.equals(other.recordsRead))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ArquivoImportacao [id=" + id + ", name=" + name + ", recordsRead=" + recordsRead + "]";
	}
}