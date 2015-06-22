package eu.adlogix.appnexus.oas.client.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ParameterMapXmlEscaper {

	private final StringXmlEscaper stringXmlEscaper = new StringXmlEscaper();

	final Map<String, Object> escapeParameters(final Map<String, Object> parameters) {
		if (parameters == null) {
			return null;
		}
		final Map<String, Object> result = new HashMap<String, Object>();
		for (final String key : parameters.keySet()) {
			final Object parameter = parameters.get(key);
			if (parameter instanceof String) {
				result.put(key, this.stringXmlEscaper.escape((String) parameter));
			} else if (parameter instanceof Collection<?>) {
				final Collection<?> originalCollection = (Collection<?>) parameter;
				final List<Object> sanitizedCollection = new ArrayList<Object>();
				for (final Object object : originalCollection) {
					if (object instanceof String) {
						sanitizedCollection.add(this.stringXmlEscaper.escape((String) object));
					} else {
						sanitizedCollection.add(this.stringXmlEscaper.escape(object.toString()));
					}
				}
				result.put(key, Collections.unmodifiableList(sanitizedCollection));
			} else {
				Object value = (parameter == null) ? parameter : this.stringXmlEscaper.escape(parameter.toString());
				result.put(key, value);
			}
		}
		return Collections.unmodifiableMap(result);
	}
}
