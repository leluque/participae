package br.com.participae.transparencia.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * This class has a default implementation for some methods of the entity
 * interface.
 *
 * Development History:
 *
 * 26/04/2016 - First version developed by Leandro Luque
 * (leandro.luque@gmail.com).
 */
@MappedSuperclass
public abstract class EntidadeImpl implements Entidade, Serializable {

	/**
	 * The class version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new entity.
	 */
	public EntidadeImpl() {
		this.dataCriacao = new Date();
		this.ultimaAtualizacao = new Date();
	}

	/**
	 * The unique entity id.
	 */
	protected Long id;

	/**
	 * The entity creation date.
	 */
	protected Date dataCriacao;

	/**
	 * The entity last update date.
	 */
	protected Date ultimaAtualizacao;

	/**
	 * Is the entity enabled?
	 */
	protected Boolean enabled = true;

	@Override
	@Id
	@NotNull
	@Column(unique = true)
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Access(AccessType.PROPERTY)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	@Override
	public void setDataCriacao(Date creationDate) {
		this.dataCriacao = creationDate;
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	@Access(AccessType.PROPERTY)
	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	@Override
	public void setUltimaAtualizacao(Date lastUpdate) {
		this.ultimaAtualizacao = lastUpdate;
	}

	@Override
	@NotNull
	@Column
	public Boolean isHabilitado() {
		return enabled;
	}

	@Override
	public void setHabilitado(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void habilitar() {
		this.enabled = true;
	}

	@Override
	public void desabilitar() {
		this.enabled = false;
	}

	@Override
	public int hashCode() {
		int hash = id != null ? id.hashCode() : 0;
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (!getClass().getName().equals(other.getClass().getName())) {
			return false;
		}
		Entidade otherEntity = (Entidade) other;
		return Objects.equals(getId(), otherEntity.getId());
	}

} // End of class.
