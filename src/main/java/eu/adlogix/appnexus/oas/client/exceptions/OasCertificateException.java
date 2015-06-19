package eu.adlogix.appnexus.oas.client.exceptions;

@SuppressWarnings("serial")
public class OasCertificateException extends RuntimeException {

	public OasCertificateException(final String errorMsg) {
		super(errorMsg);
	}

	public OasCertificateException(final String errorMsg, Exception exception) {
		super(errorMsg, exception);
	}

}