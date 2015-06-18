package eu.adlogix.appnexus.oas.client.exceptions;

public class OasRequestEmbeddedException extends RuntimeException {
	private static final long serialVersionUID = -2189864208812591203L;

	public OasRequestEmbeddedException(String request) {
		super(getErrorMessage(request));
	}

	public OasRequestEmbeddedException(String request, Throwable cause) {
		super(getErrorMessage(request), cause);
	}

	private static String getErrorMessage(String request) {
		return "OAS Error on Request: " + request;
	}

}