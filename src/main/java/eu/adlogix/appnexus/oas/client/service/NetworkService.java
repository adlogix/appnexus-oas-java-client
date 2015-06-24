package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.GetPageListResponseElementHandler;
import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
import eu.adlogix.appnexus.oas.client.domain.Section;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.utils.OasPageUrlParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

public class NetworkService extends AbstractOasService {

	final XmlRequestGenerator getSiteListRequestGenerator = new XmlRequestGenerator("list-sites");
	final XmlRequestGenerator getPageListRequestGenerator = new XmlRequestGenerator("list-pages");
	final XmlRequestGenerator getSectionListRequestGenerator = new XmlRequestGenerator("list-sections");
	final XmlRequestGenerator readSectionRequestGenerator = new XmlRequestGenerator("read-section.xml");

	public NetworkService(OasApiService apiService) {
		super(apiService);
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
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @param allSites
	 *            {@link List} which contains all the OAS {@link Site}s mapped
	 *            against their IDs
	 * 
	 * @return
	 */
	public List<Page> getAllPagesWithPositionsModifiedSinceDate(final DateTime lastModifiedDate,
			final List<Site> allSites) {

		checkNotEmpty(allSites, "allSites");

		final Map<String, Site> siteMapById = Maps.uniqueIndex(allSites, new Function<Site, String>() {
			@Override
			public String apply(Site site) {
				return site.getId();
			}
		});

		return getAllPagesWithPositionsModifiedSinceDate(lastModifiedDate, siteMapById);
	}

	/**
	 * Retrieve list of pages with positions. No {@link Site} details are loaded
	 * since {@link Page#getSite()} only contains ID
	 * 
	 * @return
	 */
	public List<Page> getAllPagesWithPositionsWithoutSiteDetails() {
		return getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);
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

		return getAllPagesWithPositionsModifiedSinceDate(lastModifiedDate, new HashMap<String, Site>());
	}

	private List<Page> getAllPagesWithPositionsModifiedSinceDate(final DateTime lastModifiedDate,
			final Map<String, Site> siteMapById) {

		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (lastModifiedDate != null) {
			parameters.put("lastModifiedDate", lastModifiedDate.toString(OAS_DATE_FORMAT));
		}

		// If siteMap is empty, sites will not be loaded and will have site with
		// just ID
		final GetPageListResponseElementHandler getPageListResponseElementHandler = new GetPageListResponseElementHandler();
		if (MapUtils.isNotEmpty(siteMapById)) {
			getPageListResponseElementHandler.setSiteMapById(siteMapById);
		}

		performPagedRequest(getPageListRequestGenerator, parameters, "List", "//List/Page", getPageListResponseElementHandler);

		return getPageListResponseElementHandler.getPages();
	}

	/**
	 * Retrieve full list of sections
	 * 
	 * @return
	 */
	public List<Section> getAllSections() {
		return getSectionListModifiedSinceDate(null);
	}

	/**
	 * Retrieve list of sections that are modified since the given last modified
	 * date
	 * 
	 * @param lastModifiedDate
	 *            Used to retrieve all modifications since this given date. If
	 *            null, everything will be retrieved.
	 * @return
	 */
	public List<Section> getSectionListModifiedSinceDate(DateTime lastModifiedDate) {

		final List<Section> result = new ArrayList<Section>();

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (lastModifiedDate != null) {
			parameters.put("lastModifiedDate", lastModifiedDate.toString(OAS_DATE_FORMAT));
		}

		ResponseElementHandler getSectionListResponseElementHandler = new ResponseElementHandler() {
			public final void processElement(final ResponseElement element) {
				final String id = element.getChild("Id");
				final Section section = readSection(id);
				result.add(section);
			}
		};

		performPagedRequest(getSectionListRequestGenerator, parameters, "List", "//List/Section", getSectionListResponseElementHandler);

		return Collections.unmodifiableList(result);
	}

	/**
	 * Retrieve a single section by id
	 * 
	 * @param sectionId
	 *            OAS ID of the section that needs to be retrieved
	 * @return
	 */
	public Section readSection(final String sectionId) {

		checkNotNull(sectionId, "sectionId");

		@SuppressWarnings("serial")
		final Map<String, Object> requestParameters = new HashMap<String, Object>() {
			{
				put("sectionId", sectionId);
			}
		};

		final ResponseParser parser = performRequest(readSectionRequestGenerator, requestParameters, true);

		String secId = parser.getTrimmedElement("//Section/Id");
		Section oasSection = new Section(secId);

		List<String> pageUrls = parser.getTrimmedElementList("//Section/Pages/Url");

		final Map<String, Page> mapPositionsPerPage = new HashMap<String, Page>();

		for (String url : pageUrls) {

			String pageUrl = OasPageUrlParser.getPageUrl(url);
			String position = OasPageUrlParser.getPosition(url);

			if (!mapPositionsPerPage.containsKey(pageUrl)) {
				mapPositionsPerPage.put(pageUrl, new Page(pageUrl));
			}
			if (!StringUtils.isEmpty(position)) {
				Position oasPosition = new Position(position);
				mapPositionsPerPage.get(pageUrl).addPosition(oasPosition);
			}
		}

		oasSection.setPages(new ArrayList<Page>(mapPositionsPerPage.values()));

		return oasSection;
	}
}
