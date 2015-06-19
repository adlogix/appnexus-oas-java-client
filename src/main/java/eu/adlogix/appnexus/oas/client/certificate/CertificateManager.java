package eu.adlogix.appnexus.oas.client.certificate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.exceptions.OasCertificateException;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;

/**
 * Manager class which handles all the certificate related functions such as
 * certificate initialization and renewal.
 * 
 **/
public class CertificateManager {

	private static final int PORT = 443;
	private static char[] passphrase = "changeit".toCharArray();
	private static final int NO_OF_DAYS_TO_EXPIRE = 3;

	private static final String CONF_DIRECTORY = "conf";
	private static final String OAS_KEYSTORE_FILENAME = "OASCACerts";

	private static final String HTTPS_PREFIX = "https://";
	private static final String CERTIFICATE_ALIAS_POSTFIX = "-1";

	private static final Logger logger = LogUtils.getLogger(CertificateManager.class);

	/**
	 * Installs a new certificate and prepares the keystore for a given host
	 * 
	 * @param host
	 *            - host name which starts with 'https://'
	 */
	public synchronized void prepareKeyStoreForHost(final String host) {

		try {
			if (!host.startsWith(HTTPS_PREFIX))
				throw new RuntimeException("We only support https host and not [" + host + "]");

			final File confDir = new File("conf");

			if (!confDir.exists() && !confDir.mkdir())
				throw new RuntimeException("Unable to create inexisting conf dir at " + confDir.getAbsolutePath());

			final File usedKeyStoreFile = new File(confDir, OAS_KEYSTORE_FILENAME);

			if (usedKeyStoreFile.exists()) {
				logger.warn("Going to delete pre-existing keyStore file at " + usedKeyStoreFile.getAbsolutePath());

				if (!usedKeyStoreFile.delete())
					throw new RuntimeException("Unable to delete pre-existing keyStore at "
							+ usedKeyStoreFile.getAbsolutePath());

				if (usedKeyStoreFile.exists())
					throw new RuntimeException("We could not delete the existing keyStore at "
							+ usedKeyStoreFile.getAbsolutePath());
			}

			final String shortenedHost = host.substring(HTTPS_PREFIX.length());

			installCertificate(usedKeyStoreFile.getAbsolutePath(), shortenedHost);

			if (!usedKeyStoreFile.exists())
				throw new RuntimeException("We could not copy the keyStore to " + usedKeyStoreFile.getAbsolutePath());

			System.setProperty("javax.net.ssl.trustStore", CONF_DIRECTORY + File.separator + OAS_KEYSTORE_FILENAME);

		} catch (Exception e) {
			throw new OasCertificateException("Error in preparing keystore and installing certificate for host" + host, e);
		}
	}

	/**
	 * Renews the existing certificate for the given host if it has expired or
	 * will expire in the next 3 days
	 * 
	 * @param host
	 *            - host name which starts with 'https://'
	 */
	public synchronized void renewCertificateForHost(final String host) {

		try {
			if (!host.startsWith(HTTPS_PREFIX))
				throw new RuntimeException("We only support https host and not [" + host + "]");

			final File confDir = new File("conf");
			final File usedKeyStoreFile = new File(confDir, OAS_KEYSTORE_FILENAME);
			final String shortenedHost = host.substring(HTTPS_PREFIX.length());
			boolean renewed = renewCertificate(usedKeyStoreFile.getAbsolutePath(), shortenedHost);
			if (renewed) {
				System.setProperty("javax.net.ssl.trustStore", CONF_DIRECTORY + File.separator + OAS_KEYSTORE_FILENAME);
			}

		} catch (Exception e) {
			throw new OasCertificateException("Error in renewing certificate for host" + host, e);

		}
	}

	/**
	 * Checks if a certificate for the given host is expired or will expire in 3
	 * days
	 * 
	 * @param filePath
	 *            - keystore file path
	 * @param host
	 *            - host name without 'https://' prefix
	 */

	private boolean certificateExpires(String filePath, String host) {

		String certificateAlias = host + CERTIFICATE_ALIAS_POSTFIX;

		try {

			File file = new File(filePath);

			if (!file.exists()) {
				throw new RuntimeException("There is no keystore at " + filePath);
			}

			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

			InputStream in = new FileInputStream(file);
			ks.load(in, passphrase);
			in.close();

			X509Certificate certificate = (X509Certificate) ks.getCertificate(certificateAlias);

			GregorianCalendar cal = new GregorianCalendar();
			cal.add(Calendar.DATE, NO_OF_DAYS_TO_EXPIRE);

			Date certExpirationDate = ((X509Certificate) certificate).getNotAfter();

			if (certExpirationDate.before(cal.getTime())) {
				logger.info("Certificate at " + filePath + " is expired or will expire in " + NO_OF_DAYS_TO_EXPIRE
						+ " days. Expires/Expired on : " + certExpirationDate);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new RuntimeException("Error occured while checking if the certificate expires", e);
		}
	}

	/**
	 * Renews the certificate at the given keystore path if it has expired or
	 * will expire in the next 3 days
	 * 
	 * @param filePath
	 *            - keystore file path
	 * @param host
	 *            - host name without 'https://' prefix
	 * @return 'true' if the certificate was renewed. 'false' if the certificate
	 *         was not renewed
	 */
	private boolean renewCertificate(String filePath, String host) {

		File file = new File(filePath);
		boolean certificateExists = file.exists();

		if (!certificateExists || certificateExpires(filePath, host)) {
			installCertificate(filePath, host);
			return true;
		}

		return false;
	}

	/**
	 * Installs a new certificate at the given path for the given host
	 * 
	 * @param filePath
	 *            - keystore file path
	 * @param host
	 *            - host name without 'https://' prefix
	 */
	private void installCertificate(String filePath, String host) {

		try {
			String certificateAlias = host + CERTIFICATE_ALIAS_POSTFIX;

			File file = new File(filePath);

			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

			if (file.exists()) {
				logger.debug("Loading KeyStore " + file.getAbsolutePath() + "...");
				InputStream in = new FileInputStream(file);
				ks.load(in, passphrase);
				in.close();
			} else {
				logger.debug("Creating KeyStore " + file.getAbsolutePath() + "...");
				ks.load(null, passphrase);
			}

			SSLContext context = SSLContext.getInstance("TLS");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
			SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
			context.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory factory = context.getSocketFactory();

			logger.debug("Opening connection to " + host + ":" + PORT + "...");
			SSLSocket socket = (SSLSocket) factory.createSocket(host, PORT);
			socket.setSoTimeout(10000);
			try {
				logger.debug("Starting SSL handshake...");
				socket.startHandshake();
				socket.close();
				logger.debug("No errors, certificate is already trusted");
			} catch (SSLException e) {
				logger.warn(e.getLocalizedMessage());
			}

			X509Certificate[] chain = tm.chain;
			if (chain == null) {
				logger.error("Could not obtain server certificate chain");
				return;
			}

			logger.debug("Server sent " + chain.length + " certificate(s):");

			int certificateToAdd = -1;
			int i = 0;
			while (i < chain.length && (certificateToAdd == -1)) {
				X509Certificate cert = chain[i];
				String dn = cert.getSubjectX500Principal().getName();
				LdapName ldapDN = new LdapName(dn);

				for (Rdn rdn : ldapDN.getRdns()) {
					if (rdn.getType().equals("CN") && hostMatchesCommonName(rdn.getValue().toString(), host)) {
						certificateToAdd = i;
						logger.debug("Found certificate to renew");
						break;
					}
				}
				i++;
			}

			if (certificateToAdd == -1) {
				logger.debug("KeyStore not changed");
				return;
			}

			X509Certificate cert = chain[certificateToAdd];
			ks.setCertificateEntry(certificateAlias, cert);

			OutputStream out = new FileOutputStream(file);
			ks.store(out, passphrase);
			out.close();

			logger.info("Added certificate to keystore " + file.getAbsolutePath());

		} catch (Exception e) {
			throw new RuntimeException("Error in installing certificate", e);
		}
	}

	private static class SavingTrustManager implements X509TrustManager {

		private final X509TrustManager tm;
		private X509Certificate[] chain;

		SavingTrustManager(X509TrustManager tm) {
			this.tm = tm;
		}

		public X509Certificate[] getAcceptedIssuers() {
			throw new UnsupportedOperationException();
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			throw new UnsupportedOperationException();
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			this.chain = chain;
			tm.checkServerTrusted(chain, authType);
		}
	}

	/**
	 * Checks if the Certificate common name matches the host name
	 * 
	 * @param commonName
	 *            - Certificate common name
	 * @param host
	 *            - host name
	 * @return 'true' if the host matches commonNam.e 'false'if the host does
	 *         not match commonName
	 */
	private boolean hostMatchesCommonName(String commonName, String host) {

		if (commonName.equals(host)) {
			return true;
		}

		if (isWildCardName(commonName)) {
			return matchesWildCardCommonName(commonName, host);
		}

		return false;
	}

	/**
	 * Checks if the given name is a wildcard name which starts with *
	 * 
	 * @param name
	 * @return 'true' if the name starts with '*'. 'false' if the name does not
	 *         start with '*'
	 */
	private static boolean isWildCardName(String name) {
		return name.startsWith("*");
	}

	/**
	 * Checks if the given wild card common name matches host
	 * 
	 * @param wildCardCommonName
	 *            eg: *.domain.com
	 * @param host
	 *            eg: test.domain.com
	 * @return 'true' if the wild card common name matched host. 'false'if the
	 *         wild card common name does not match host
	 */
	private static boolean matchesWildCardCommonName(String wildCardCommonName, String host) {

		if (wildCardCommonName != null && wildCardCommonName.length() > 1) {
			String commonName = wildCardCommonName.substring(1);
			return host.endsWith(commonName);
		}
		return false;
	}

}
