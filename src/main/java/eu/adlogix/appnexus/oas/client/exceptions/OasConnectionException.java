package eu.adlogix.appnexus.oas.client.exceptions;

@SuppressWarnings("serial")
public class OasConnectionException extends RuntimeException {

	public OasConnectionException(final String errorMsg) {
		super(errorMsg);
	}

}