package eu.adlogix.appnexus.oas.client.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.exceptions.OasClientSideException;

public class StatefulDomainManager {

	/**
	 * Checks if an {@link Object} is an instance of {@link StatefulDomain}
	 * 
	 * @param entity
	 * @return true if the o
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
				} else if (value instanceof Map<?, ?>) {
					setMapProperty(updatedObject, attributeName, value);
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

	private void setMapProperty(Object object, String attributeName, Object value) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		if (value == null)
			return;

		final Map<Object, Object> modifiedMap = Maps.newHashMap();

		for (Map.Entry<?, ?> mapEntry : ((Map<?, ?>) value).entrySet()) {
			final Object origValue = mapEntry.getValue();
			final Object modValue = isStatefulDomain(origValue) ? getModifiedObject((StatefulDomain) origValue)
					: origValue;
			modifiedMap.put(mapEntry.getKey(), modValue);
		}

		PropertyUtils.setProperty(object, attributeName, modifiedMap);
	}

}
