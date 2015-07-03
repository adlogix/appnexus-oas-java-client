package eu.adlogix.appnexus.oas.client.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Maintains the state of modified attributes.
 * 
 */
public abstract class StatefulDomain {

	private final Set<String> modifiedAttributes = new HashSet<String>();
	private final Set<String> persistentAttributes = new HashSet<String>();

	/**
	 * Adds a modified attribute.
	 * 
	 * @param attribute
	 *            - name of the attribute
	 */
	protected void addModifiedAttribute(String attribute) {
		modifiedAttributes.add(attribute);
	}

	/**
	 * Adds a persistent attribute.
	 * 
	 * @param attribute
	 *            - name of the attribute
	 */
	protected void addPersistentAttribute(String attribute) {
		persistentAttributes.add(attribute);
	}

	/**
	 * Checks if an attribute is modified
	 * 
	 */
	protected boolean isModified(String attribute) {
		return modifiedAttributes.contains(attribute);
	}

	/**
	 * Resets the modified attributes.The object will be considered as a
	 * unmodified object after calling this method.
	 * 
	 */
	public void resetModifiedAttributes() {
		modifiedAttributes.clear();
	}

	/**
	 * Returns a {@link Set} of modified and persistent attributes
	 * 
	 * @return
	 */
	public Set<String> getModifiedAndPersistentAttributes() {
		Set<String> attributes = new HashSet<String>(modifiedAttributes);
		attributes.addAll(persistentAttributes);
		return attributes;
	}
}
