package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public final class Credentials {
	
	private String host;
	private String user;
	private String password;
	private String account;
}
