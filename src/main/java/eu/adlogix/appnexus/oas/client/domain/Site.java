package eu.adlogix.appnexus.oas.client.domain;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public final class Site {

	public interface Builder {
		Site build(final String siteId);
	}

	@AllArgsConstructor
	public static class SiteIdMapBackedBuilder implements Builder {
		final Map<String, Site> siteMap;

		@Override
		public Site build(String siteId) {
			return siteMap.get(siteId);
		}
	}

	public static class OnlyIdBuilder implements Builder {
		@Override
		public Site build(String siteId) {
			return new Site(siteId, siteId);
		}
	}

	private final String id;
	private final String name;

	public Site(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

}
