package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;

/**
 * Represents a Page (Web page) which has a URL
 */
@Data
public final class Page {

	private final String url;
	private Site site;



	public Page(final String url, Site site) {
		super();
		this.url = url;
		this.site = site;
	}

	public Page(final String url) {
		super();
		this.url = url;
	}


}
