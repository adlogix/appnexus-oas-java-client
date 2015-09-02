package eu.adlogix.appnexus.oas.client.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;

public final class GetPageListResponseElementHandler implements ResponseElementHandler {

	@Setter
	private Map<String, Site> siteMapById;

	@Getter
	private final List<Page> pages = new ArrayList<Page>();

	public final void processElement(final ResponseElement element) {
		addPageToPageList(element);
	}

	private void addPageToPageList(final ResponseElement element) {

		final String siteId = element.getChild("SiteId");
		final String url = element.getChild("Url");

		if (StringUtils.isNotEmpty(siteId)) {
			final Site site = MapUtils.isNotEmpty(siteMapById) ? siteMapById.get(siteId) : new Site(siteId, null, null);
			pages.add(new Page(url, site));
		}
	}

}