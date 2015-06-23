package eu.adlogix.appnexus.oas.client.exceptions;

@SuppressWarnings("serial")
public class OasClientSideException extends RuntimeException {

	public OasClientSideException(final String errorMsg) {
		super(errorMsg);
	}

	public OasClientSideException(final String errorMsg, Exception e) {
		super(errorMsg, e);
	}

}