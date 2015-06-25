package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class Position {

	private final String name;
	private String shortName;

	public Position(final String name) {
		this.name = name;
	}

}

