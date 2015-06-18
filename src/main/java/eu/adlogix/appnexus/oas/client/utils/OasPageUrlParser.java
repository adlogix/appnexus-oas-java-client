package eu.adlogix.appnexus.oas.client.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * OasPageUrlParser class handles OAS Page url parsing related functions.
 * 
 * @author Piumika Welagedara
 * 
 */
public class OasPageUrlParser {

	/**
	 * Retrieves page url from the given url string. eg: when the url is
	 * 'quotidianiespresso.it/qe/ilcentro/home@x96' this method will return
	 * quotidianiespresso.it/qe/ilcentro/home
	 * 
	 * @param url
	 * @return page url
	 */
	public static String getPageUrl(String url) {
		if (!StringUtils.isEmpty(url)) {
			return url.contains("@") ? url.substring(0, url.indexOf("@")) : url;
		}
		return url;
	}

	/**
	 * Retrieve position from the given url string. eg: when the url is
	 * 'quotidianiespresso.it/qe/ilcentro/home@x96' this method will return x96
	 * 
	 * @param url
	 * @return position
	 */

	public static String getPosition(String url) {
		if (!StringUtils.isEmpty(url) && url.contains("@")) {
			return url.substring(url.indexOf("@") + 1, url.length());

		}
		return null;
	}

}
