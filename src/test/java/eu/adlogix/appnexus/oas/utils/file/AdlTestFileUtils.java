package eu.adlogix.appnexus.oas.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;

import eu.adlogix.appnexus.oas.client.utils.AdlFileUtils;
import eu.adlogix.appnexus.oas.utils.file.AdlResourceNotFoundException;

public class AdlTestFileUtils {

	public static File getTestResource(final String fileName, final Class<?> testClazz) throws URISyntaxException,
			AdlResourceNotFoundException {
		String testedClassCanonicalName = testClazz.getCanonicalName();
		String testedClassCanonicalNameLow = testedClassCanonicalName.toLowerCase();

		String classPathSep = "/";
		String testedClassPath = testedClassCanonicalNameLow.replace(".", classPathSep);
		String completeClassPath = testedClassPath + classPathSep + fileName;

		return AdlFileUtils.getResourceFilePerClassPath(completeClassPath);
	}

	public static String getTestResourceAsString(final String fileName, final Class<?> testClazz)
			throws URISyntaxException, FileNotFoundException, IOException, AdlResourceNotFoundException {
		File testResourceFile = getTestResource(fileName, testClazz);

		return IOUtils.toString(new FileInputStream(testResourceFile));
	}
}
