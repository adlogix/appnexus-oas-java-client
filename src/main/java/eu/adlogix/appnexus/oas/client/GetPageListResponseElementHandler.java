package eu.adlogix.appnexus.oas.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.utils.OasPageUrlParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;

public final class GetPageListResponseElementHandler implements ResponseElementHandler {

	@Setter
	private Map<String, Site> siteMapById;

	private final Map<String, Page> pagesPerPageUrl = new HashMap<String, Page>();

	public final void processElement(final ResponseElement element) {
		final String url = element.getChild("Url");

		if (!StringUtils.isEmpty(url)) {

			String pageUrl = OasPageUrlParser.getPageUrl(url);
			String position = OasPageUrlParser.getPosition(url);

			addPageToPageMap(element, pageUrl);
			addPositionToPageMap(position, pageUrl);
		}
	}

	private void addPositionToPageMap(String position, String pageUrl) {
		if (!StringUtils.isEmpty(position)) {
			final Position oasPosition = new Position(position);
			pagesPerPageUrl.get(pageUrl).addPosition(oasPosition);
		}
	}

	private void addPageToPageMap(final ResponseElement element, String pageUrl) {

		if (!pagesPerPageUrl.containsKey(pageUrl)) {

			final String siteId = element.getChild("SiteId");

			if (StringUtils.isNotEmpty(siteId)) {
				final Site site = MapUtils.isNotEmpty(siteMapById) ? siteMapById.get(siteId) : new Site(siteId, null);
				pagesPerPageUrl.put(pageUrl, new Page(pageUrl, site));
			}
		}
	}

	public List<Page> getPages() {
		return new ArrayList<Page>(pagesPerPageUrl.values());
	}
}