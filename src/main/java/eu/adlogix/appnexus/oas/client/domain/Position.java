package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a Position of a {@link Page} which can host an Ad
 *
 * @see Page#getPositions()
 */
@AllArgsConstructor
@Data
public final class Position {

	private final String name;
	private String shortName;

	public Position(final String name) {
		this.name = name;
	}

}

