package eu.adlogix.appnexus.oas.client.certificate;

import java.util.Properties;

public class TestCredentials {
	public static Properties getTestCredentials() {
		final Properties credentials = new Properties();
		credentials.put("oas.host", "https://test");
		credentials.put("oas.account", "x");
		credentials.put("oas.user", "x");
		credentials.put("oas.password", "x");
		return credentials;
	}
}
