package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;

import eu.adlogix.appnexus.oas.client.GetPageListResponseElementHandler;
import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class NetworkService extends AbstractXaxisService {

	final XmlRequestGenerator getSiteListRequestGenerator = new XmlRequestGenerator("list-sites");

	final XmlRequestGenerator getPageListRequestGenerator = new XmlRequestGenerator("list-pages");

	protected NetworkService(Properties credentials) {
		super(credentials);
	}

	public NetworkService(Properties credentials, XaxisApiService apiService, CertificateManager certificateManager) {
		super(credentials, apiService, certificateManager);
	}

	/**
	 * Retrieve all sites
	 */
	public List<Site> getAllSites() {
		final List<Site> result = new ArrayList<Site>();

		ResponseElementHandler getSiteListResponseElementHandler = new ResponseElementHandler() {
			public final void processElement(final ResponseElement element) {
				final String id = element.getChild("Id");
				final String name = element.getChild("Name");
				final Site site = new Site(id, name);
				result.add(site);
			}
		};

		performPagedRequest(getSiteListRequestGenerator, new HashMap<String, Object>(), "List", "//List/Site", getSiteListResponseElementHandler);

		return Collections.unmodifiableList(result);
	}

	/**
	 * Retrieve list of pages with positions which are modified since the given
	 * last modified date
	 * 
	 * @param siteMapById
	 *            Map which contains all the OAS sites mapped against their IDs
	 * 
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @return
	 */
	public List<Page> getAllPagesWithPositionsModifiedSinceDate(final Map<String, Site> siteMapById,
			final DateTime lastModifiedDate) {

		return getAllPagesWithPositionsModifiedSinceDate(new Site.SiteIdMapBackedBuilder(siteMapById), lastModifiedDate);
	}

	/**
	 * Retrieve list of pages with positions which are modified since the given
	 * last modified date. No {@link Site} details are loaded since
	 * {@link Page#getSite()} only contains ID
	 * 
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @return
	 */
	public List<Page> getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(final DateTime lastModifiedDate) {

		return getAllPagesWithPositionsModifiedSinceDate(new Site.OnlyIdBuilder(), lastModifiedDate);
	}

	private List<Page> getAllPagesWithPositionsModifiedSinceDate(final Site.Builder siteBuilder,
			final DateTime lastModifiedDate) {

		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (lastModifiedDate != null) {
			parameters.put("lastModifiedDate", lastModifiedDate.toString("yyyy-MM-dd"));
		}

		// The siteBuilder will inject the Site when Pages are created based on
		// the siteBuilder implementation
		final GetPageListResponseElementHandler getPageListResponseElementHandler = new GetPageListResponseElementHandler(siteBuilder);

		performPagedRequest(getPageListRequestGenerator, parameters, "List", "//List/Page", getPageListResponseElementHandler);

		return getPageListResponseElementHandler.getPages();
	}
}
