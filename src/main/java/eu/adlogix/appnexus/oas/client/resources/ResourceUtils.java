package eu.adlogix.appnexus.oas.client.resources;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.io.IOUtils;

import eu.adlogix.appnexus.oas.client.exceptions.OasClientSideException;

public final class ResourceUtils {

	public static InputStream loadResourceInputStream(final String resourceFileName) {
		final InputStream result = ResourceUtils.class.getResourceAsStream(resourceFileName);
		if (result == null) {
			throw new OasClientSideException(resourceFileName + " could not be found");
		}
		return result;
	}

	public static String loadResourceString(final String resourceFileName) {
		try {
			return IOUtils.toString(loadResourceInputStream(resourceFileName));
		} catch (IOException exception) {
			throw new OasClientSideException("Error while reading file " + resourceFileName, exception);
		}
	}

	public static StringTemplate loadResourceTemplate(final String resourceFileName) {
		return new StringTemplate(loadResourceString(resourceFileName));
	}

	private ResourceUtils() {
		// Utility class: should never be instantiated
	}

}
