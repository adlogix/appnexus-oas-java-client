package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

/**
 * Maintains the state of modified attributes and also contains a persistent ID.
 * This abstract class can be extended by domain classes which has a persistent
 * Id and has to maintain the state of modified attributes.
 * 
 */
@Getter
public abstract class StatefulDomainWithId extends StatefulDomain {

	private String id;

	/**
	 * Default Constructor. The Id will be considered as a persistent attribute
	 * which will not be modified.
	 */
	public StatefulDomainWithId() {
		addPersistentAttribute("id");
	}

	/**
	 * Setter for Id. The Id cannot be set if its already initialized
	 */
	public void setId(String id) {
		if (!StringUtils.isEmpty(this.id)) {
			throw new IllegalStateException("Id cannot be modified");
		}
		this.id = id;
	}

}
