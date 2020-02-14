package br.com.participae.transparencia.dominio;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * This class implements entities that have name and description.
 *
 * Development History:
 *
 * 26/04/2016 - First version developed by Leandro Luque
 * (leandro.luque@gmail.com).
 */
@MappedSuperclass
public abstract class EntidadeNomeada extends EntidadeImpl {

	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The entity name.
	 */
	protected String nome;

	/**
	 * The entity description.
	 */
	protected String descricao;

	/**
	 * Creates a new named entity.
	 */
	public EntidadeNomeada() {
	}

	/**
	 * Creates a new named entity with the specified name.
	 *
	 * @param name
	 *            The entity name.
	 */
	public EntidadeNomeada(String name) {
		this.nome = name;
	}

	/**
	 * Creates a new named entity with the specified name and description.
	 *
	 * @param name
	 *            The entity name.
	 * @param description
	 *            The entity description.
	 */
	public EntidadeNomeada(String name, String description) {
		this.nome = name;
		this.descricao = description;
	}

	/**
	 * Gets the entity name.
	 *
	 * @return The entity name.
	 */
	@Column(name = "name")
	@NotNull
	@Access(AccessType.PROPERTY)
	public String getNome() {
		return nome;
	}

	/**
	 * Sets the entity name.
	 *
	 * @param name
	 *            The entity name.
	 */
	public void setNome(String name) {
		this.nome = name;
	}

	/**
	 * Gets the entity description.
	 *
	 * @return The entity description.
	 */
	@Column(name = "description")
	@Access(AccessType.PROPERTY)
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Sets the entity description.
	 *
	 * @param description
	 *            The entity description.
	 */
	public void setDescricao(String description) {
		this.descricao = description;
	}

} // End of class.
