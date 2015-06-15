package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;

@Data
public final class Position {

	private final String name;

	public Position(final String name) {
		this.name = name;
	}

}

