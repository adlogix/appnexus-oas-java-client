package eu.adlogix.appnexus.oas.client.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import eu.adlogix.appnexus.oas.client.exceptions.OasClientSideException;

/**
 * Manager class which handles operations related to {@link StatefulDomain}
 * 
 */
public class StatefulDomainManager {

	/**
	 * Checks if an {@link Object} is an instance of {@link StatefulDomain}
	 * 
	 * @param entity
	 * @return true if the object is an instance of {@link StatefulDomain},
	 *         false if the object is not an instance of {@link StatefulDomain}
	 * 
	 */
	public boolean isStatefulDomain(Object entity) {
		return StatefulDomain.class.isInstance(entity);
	}

	/**
	 * Returns a new {@link StatefulDomain} object with only the modified and
	 * persistent attribute values. (Persistent attributes are attributes such
	 * as ID)
	 * 
	 */
	public <T extends StatefulDomain> T getModifiedObject(T object) {

		try {

			@SuppressWarnings("unchecked")
			T updatedObject = (T) (object.getClass().newInstance());

				for (String attributeName : object.getModifiedAndPersistentAttributes()) {

					Object value = PropertyUtils.getProperty(object, attributeName);

					if (isStatefulDomain(value)) {
						setStatefulProperty(updatedObject, attributeName, value);
					} else if (value instanceof List<?>) {
						setListProperty(updatedObject, attributeName, value);
					} else {
						PropertyUtils.setProperty(updatedObject, attributeName, value);
					}
				}
			return updatedObject;

		} catch (Exception e) {
			throw new OasClientSideException("Error in getting modified object of class:" + object.getClass().getName(), e);
		}
	}

	private void setStatefulProperty(Object object, String attributeName, Object value)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

			PropertyUtils.setProperty(object, attributeName, (value == null) ? null
					: getModifiedObject((StatefulDomain) value));

	}

	private void setListProperty(Object object, String attributeName, Object value)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		if (value == null)
				return;

		List<Object> modifiedList = new ArrayList<Object>();

		for (Object listValue : (List<?>) value) {
			if (isStatefulDomain(listValue)) {
				modifiedList.add(getModifiedObject((StatefulDomain) listValue));
			} else {
				modifiedList.add(listValue);
			}
		}
		PropertyUtils.setProperty(object, attributeName, modifiedList);

	}

}
