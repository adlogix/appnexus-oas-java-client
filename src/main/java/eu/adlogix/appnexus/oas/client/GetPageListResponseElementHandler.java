package eu.adlogix.appnexus.oas.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.domain.Site.Builder;
import eu.adlogix.appnexus.oas.client.utils.OasPageUrlParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;

public final class GetPageListResponseElementHandler implements ResponseElementHandler {

	private final Builder siteBuilder;
	private final Map<String, Page> mapPositionsPerPage = new HashMap<String, Page>();

	public GetPageListResponseElementHandler(Builder siteBuilder) {
		this.siteBuilder = siteBuilder;
	}

	public final void processElement(final ResponseElement element) {
		final String url = element.getChild("Url");

		if (!StringUtils.isEmpty(url)) {

			String pageUrl = OasPageUrlParser.getPageUrl(url);
			String position = OasPageUrlParser.getPosition(url);

			if (!mapPositionsPerPage.containsKey(pageUrl)) {
				final String siteId = element.getChild("SiteId");
				if (!StringUtils.isEmpty(siteId)) {
					final Site site = siteBuilder.build(siteId);
					mapPositionsPerPage.put(pageUrl, new Page(pageUrl, site));
				}
			}

			if (!StringUtils.isEmpty(position)) {
				final Position oasPosition = new Position(position);
				mapPositionsPerPage.get(pageUrl).addPosition(oasPosition);
			}
		}
	}

	public List<Page> getPages() {
		return new ArrayList<Page>(mapPositionsPerPage.values());
	}
}