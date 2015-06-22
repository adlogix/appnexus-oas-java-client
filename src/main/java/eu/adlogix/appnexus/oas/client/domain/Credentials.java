package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;


/**
 * Credentials that are used for accessing OAS API
 * 
 */
@Data
public final class Credentials {
	
	private String host;
	private String user;
	private String password;
	private String account;

	/**
	 * Initializes the credentials
	 * 
	 * @param host
	 *            OAS host url.This is a mandatory attribute.
	 * @param user
	 *            User Name for accessing the API.This is a mandatory attribute
	 * @param password
	 *            Password for accessing the API.This is a mandatory attribute
	 * @param account
	 *            OAS Account for accessing the API.This is a mandatory
	 *            attribute
	 */
	public Credentials(String host, String user, String password, String account) {

		if (StringUtils.isEmpty(host)) {
			throw new IllegalArgumentException("The OAS Hostname is empty");
		}
		this.host = host;

		if (StringUtils.isEmpty(user)) {
			throw new IllegalArgumentException("The OAS Username is empty");
		}
		this.user = user;

		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("The OAS Password is empty");
		}

		this.password = password;

		if (StringUtils.isEmpty(account)) {
			throw new IllegalArgumentException("The OAS Account is emtpy");
		}
		this.account = account;
	}

}
