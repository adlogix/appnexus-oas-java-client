package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Data;

@Data
public class Targeting {

	public enum TargetingType {
		TOP_DOMAIN("TopLevelDomain"),
		BANDWIDTH("Bandwidth"),
		CONTINENT("Continent"),
		COUNTRY("Country"),
		STATE("State"),
		MSA("Msa"),
		DMA("Dma"),
		OS("Os"),
		BROWSER("Browser"),
		BROWSER_VERSIONS("BrowserV");

		private final String code;

		private TargetingType(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return code;
		}
	}

	private Boolean exculde;
	private TargetingType targetingType;
	private List<String> values;

	public Targeting(TargetingType type) {
		this.targetingType = type;
	}
}
