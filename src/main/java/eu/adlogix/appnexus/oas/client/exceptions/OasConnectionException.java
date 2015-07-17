package eu.adlogix.appnexus.oas.client.exceptions;

@SuppressWarnings("serial")
public class OasConnectionException extends OasClientSideException {

	public OasConnectionException(final String errorMsg) {
		super(errorMsg);
	}

	public OasConnectionException(String string, Exception e) {
		super(string, e);
	}

}