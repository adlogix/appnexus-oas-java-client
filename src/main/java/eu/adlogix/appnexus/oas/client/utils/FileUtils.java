package eu.adlogix.appnexus.oas.client.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import eu.adlogix.appnexus.oas.client.exceptions.OasClientSideException;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;

public class FileUtils {

	private final static String RESOURCE_NOT_FOUND_MSG_BEGIN = "Unable to locate resource for path '";

	public static File getResourceFilePerClassPath(final String resourcePath) throws ResourceNotFoundException {
		try {
			final URL fileUrl = ClassLoader.getSystemResource(resourcePath);
			if (fileUrl == null)
				throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MSG_BEGIN + resourcePath
						+ "' (fileUrl is null)");

			URI fileUri = fileUrl.toURI();

			if (fileUri == null)
				throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MSG_BEGIN + resourcePath
						+ "' (fileUri is null)");

			final File res = new File(fileUri);
			return res;
		} catch (ResourceNotFoundException adlrnfe) {
			throw adlrnfe;
		} catch (Throwable t) {
			throw new OasClientSideException("Unexpected throwable while getResourceFilePerClassPath for '"
					+ resourcePath
					+ "' [" + t.getMessage() + "]", t);
		}
	}

	public static void copyResourceFileToLocalFile(final String resourcePath, final File destinationLocalFile,
			Class<?> callingClazz) throws FileNotFoundException, IOException {
		InputStream resourceFileStream = callingClazz.getClassLoader().getResourceAsStream(resourcePath);

		if (resourceFileStream == null)
			throw new OasClientSideException("Unable to locate resource for path " + resourcePath
					+ " (resourceFileStream is null)");

		IOUtils.copy(resourceFileStream, new FileOutputStream(destinationLocalFile));
	}
}
