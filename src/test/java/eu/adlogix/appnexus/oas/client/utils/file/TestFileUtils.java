package eu.adlogix.appnexus.oas.client.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;

import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.FileUtils;

public class TestFileUtils {

	public static File getTestResource(final String fileName, final Class<?> testClazz) throws URISyntaxException,
			ResourceNotFoundException {
		String testedClassCanonicalName = testClazz.getCanonicalName();
		String testedClassCanonicalNameLow = testedClassCanonicalName.toLowerCase();

		String classPathSep = "/";
		String testedClassPath = testedClassCanonicalNameLow.replace(".", classPathSep);
		String completeClassPath = testedClassPath + classPathSep + fileName;

		return FileUtils.getResourceFilePerClassPath(completeClassPath);
	}

	public static String getTestResourceAsString(final String fileName, final Class<?> testClazz)
			throws URISyntaxException, FileNotFoundException, IOException, ResourceNotFoundException {
		File testResourceFile = getTestResource(fileName, testClazz);

		return IOUtils.toString(new FileInputStream(testResourceFile));
	}
}
