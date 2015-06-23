package eu.adlogix.appnexus.oas.client.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import eu.adlogix.appnexus.oas.client.exceptions.OasClientSideException;

/**
 * Maintains the state of modified attributes.
 * 
 */
public abstract class StatefulDomain {

	private Set<String> modifiedAttributes;
			
	protected StatefulDomain() {
		modifiedAttributes = new HashSet<String>();
	}

	/**
	 * Sets the attribute as modified.
	 * 
	 * @param attribute
	 *            - name of the attribute
	 */
	protected void setModifiedFlag(String attribute) {
		modifiedAttributes.add(attribute);
	}
	
	/**
	 * Resets the modified flags.The object will be considered as a non-modified
	 * object after calling this method.
	 * 
	 */
	protected void resetModifiedFlags() {
		modifiedAttributes.clear();
	}

	/**
	 * Returns a new object with only the modified attribute values.
	 * 
	 */
	protected <T extends StatefulDomain> T getObjectWithModifiedAttributes() {

		try {

			@SuppressWarnings("unchecked")
			T updatedObject = (T) (this.getClass().newInstance());

			for (String attributeName : modifiedAttributes) {
				Object value = PropertyUtils.getProperty(this, attributeName);
				PropertyUtils.setProperty(updatedObject, attributeName, value);
			}

			return updatedObject;

		} catch (Exception e) {
			throw new OasClientSideException("Error in accessing/setting attibutes of object of class:"
					+ this.getClass().getName(), e);
		}
	}

}
