package eu.adlogix.appnexus.oas.client.utils;

import static eu.adlogix.appnexus.oas.client.resources.ResourceUtils.loadResourceInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.PushLevel;


public final class Credentials {
	
	private static final String CREDENTIASL_KEY_HOST = "oas.host";
	private static final String CREDENTIASL_KEY_ACCOUNT = "oas.account";
	private static final String CREDENTIASL_KEY_USER = "oas.user";
	private static final String CREDENTIASL_KEY_PASSWORD = "oas.password";
	private static final String CREDENTIASL_KEY_PUSH_LEVEL = "oas.push_level";
	

	public static Properties loadCredentials(final String credentialsName) {
		if (StringUtils.isEmpty(credentialsName)) {
			throw new IllegalArgumentException("Illegal credentials: " + credentialsName);
		}
		try {
			final Properties credentials = new Properties();
			final String resourceFileName = credentialsName.trim().endsWith(".credentials") ? credentialsName.trim()
					: credentialsName + ".credentials";
			final InputStream credentialsInputStream = loadResourceInputStream(resourceFileName);
			if (credentialsInputStream == null) {
				throw new IllegalArgumentException("Credentials not found: " + resourceFileName);
			}
			credentials.load(credentialsInputStream);
			return credentials;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private Credentials() {
		// Utility class: should never be instantiated
	}

	
	public static Properties loadCredentials(final InputStream credentialsInputStream) {
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

	public static Properties loadTestCredentials() {
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

	public static PushLevel getPushLevel(final Properties credentials) {
		if (!propertyExists(CREDENTIASL_KEY_PUSH_LEVEL, credentials)) {
			return PushLevel.PAGES;
		}
		String pushLevel = getProperty(CREDENTIASL_KEY_PUSH_LEVEL, credentials);
		return PushLevel.valueOf(pushLevel.toUpperCase());
	}
	
	private static String getProperty(String key, final Properties credentials) {
		String value = credentials.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			throw new RuntimeException("In the credentials Properties file the value for key "+key+" is missing");
		}
		return value.trim();
	}
	
	private static boolean propertyExists(String key, final Properties credentials) {
		return credentials.containsKey(key);
	}

}
