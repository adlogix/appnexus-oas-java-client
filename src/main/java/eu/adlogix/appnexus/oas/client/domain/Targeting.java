package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class Targeting extends StatefulDomain {

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

	private Boolean exclude;
	private TargetingType targetingType;
	private List<String> values;

	protected Targeting() {
		super();
	}

	public Targeting(TargetingType type) {
		this.targetingType = type;
		setModifiedFlag("targetingType");
	}

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		setModifiedFlag("exclude");
	}

	public void setValues(List<String> values) {
		this.values = values;
		setModifiedFlag("values");
	}

	public void setTargetingType(TargetingType targetingType) {
		this.targetingType = targetingType;
	}

	/**
	 * Resets the modified flags.The {@link Targeting} will be considered as an
	 * unmodified {@link Targeting} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
	}

	/**
	 * Returns a new {@link Targeting} object with only the modified attribute
	 * values.
	 */
	public Targeting getTargetingWithModifiedAttributes() {
		return super.getObjectWithModifiedAttributes();
	}


}
