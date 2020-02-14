package br.com.participae.transparencia.dominio;

import java.util.Date;

/**
 * This interface defines the common behavior of domain entities.
 *
 * Development History:
 *
 * 26/04/2016 - First version developed by Leandro Luque
 * (leandro.luque@gmail.com).
 */
public interface Entidade {

	/**
	 * Gets the entity unique id.
	 *
	 * @return The entity unique id.
	 */
	public Long getId();

	/**
	 * Set the entity unique id.
	 *
	 * @param id
	 *            The entity unique id.
	 */
	public void setId(Long id);

	/**
	 * Gets the entity creation date.
	 *
	 * @return The entity creation date.
	 */
	public Date getDataCriacao();

	/**
	 * Sets the entity creation date.
	 *
	 * @param creationDate
	 *            The entity creation date.
	 */
	public void setDataCriacao(Date creationDate);

	/**
	 * Gets the entity last update date.
	 *
	 * @return The entity last update date.
	 */
	public Date getUltimaAtualizacao();

	/**
	 * Sets the entity last update date.
	 *
	 * @param lastUpdate
	 *            The entity last update date.
	 */
	public void setUltimaAtualizacao(Date lastUpdate);

	/**
	 * Is the entity enabled?
	 *
	 * @return true, if yes. false, otherwise.
	 */
	public Boolean isHabilitado();

	/**
	 * Defines whether the entity is enabled.
	 *
	 * @param enabled
	 *            true, if yes. false, otherwise.
	 */
	public void setHabilitado(Boolean enabled);

	/**
	 * Enable the entity.
	 */
	public void habilitar();

	/**
	 * Disable the entity.
	 */
	public void desabilitar();

} // End of class.
