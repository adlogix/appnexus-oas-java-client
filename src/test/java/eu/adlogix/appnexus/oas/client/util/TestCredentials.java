package eu.adlogix.appnexus.oas.client.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.Credentials;

public class TestCredentials {

	private static final String CREDENTIASL_KEY_HOST = "oas.host";
	private static final String CREDENTIASL_KEY_ACCOUNT = "oas.account";
	private static final String CREDENTIASL_KEY_USER = "oas.user";
	private static final String CREDENTIASL_KEY_PASSWORD = "oas.password";


	public static Credentials getCredentialsFromExternalFile() {

		Properties properties = loadTestCredentialsFromExternalFile();
		return new Credentials(getHost(properties), getUser(properties), getPassword(properties), getAccount(properties));
	}

	private static Properties loadCredentials(final InputStream credentialsInputStream) {
		try {
			final Properties credentials = new Properties();
			if (credentialsInputStream == null) {
				throw new IllegalArgumentException("Credentials input stream not found");
			}
			credentials.load(credentialsInputStream);
			return credentials;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static Properties loadTestCredentialsFromExternalFile() {
		String credentialsFilePath = System.getProperty("credentialsFile");
		if (!StringUtils.isEmpty(credentialsFilePath)) {
			try {
				return loadCredentials(new FileInputStream(credentialsFilePath));
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Credentials file cannot be found", e);
			}
		} else {
			throw new RuntimeException("Credentials file is not set");
		}
	}

	public static String getHost(final Properties credentials) {
		return getProperty(CREDENTIASL_KEY_HOST, credentials);
	}

	public static String getUser(final Properties credentials) {
		return getProperty(CREDENTIASL_KEY_USER, credentials);
	}

	public static String getPassword(final Properties credentials) {
		return getProperty(CREDENTIASL_KEY_PASSWORD, credentials);
	}

	public static String getAccount(final Properties credentials) {
		return getProperty(CREDENTIASL_KEY_ACCOUNT, credentials);
	}

	private static String getProperty(String key, final Properties credentials) {
		String value = credentials.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			throw new RuntimeException("In the credentials Properties file the value for key " + key + " is missing");
		}
		return value.trim();
	}

}
